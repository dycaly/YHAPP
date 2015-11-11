package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;

import org.json.JSONObject;

public class RegisterActivity extends Activity {
    private int flag=1;
    private ImageView reImageView;
    private EditText usernameET;
    private EditText passwordET;
    private EditText confirmET;
    private LinearLayout registLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reImageView = (ImageView) findViewById(R.id.regist_back);

        reImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("flag",-1);
                setResult(0,intent);
                finish();
            }
        });

        usernameET=(EditText)findViewById(R.id.regist_username);
        passwordET=(EditText)findViewById(R.id.regist_password);
        confirmET=(EditText)findViewById(R.id.regist_confirm);
        registLayout = (LinearLayout)findViewById(R.id.regist);
        registLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String confirm = confirmET.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "用户名不能为空" , Toast.LENGTH_SHORT).show();
                    return;
                }
               if(password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "密码不能为空" , Toast.LENGTH_SHORT).show();
                   return;
                }
               if(confirm.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "确认密码不能为空" , Toast.LENGTH_SHORT).show();
                   return;
                }
               if(!password.equals(confirm)){
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不同，请重新确认" , Toast.LENGTH_SHORT).show();
                   return;
                }
                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {

                        try{
                            JSONObject jsonObject = new JSONObject(result);
                            int status = jsonObject.getInt("status");
                            switch (status){
                                case 0:
                                    String token = jsonObject.getString("token");

                                    MainConfig.TOKEN = token;

                                    new NetConnection(new NetConnection.SuccessCallback() {
                                        @Override
                                        public void onSuccess(String result) {
                                            try{
                                                JSONObject jsonObject = new JSONObject(result);

                                                int status = jsonObject.getInt("status");
                                                switch (status){
                                                    case 0:
                                                        String picurl = jsonObject.getString("picUrl");
                                                        String nickname = jsonObject.getString("nickname");
                                                        SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sp.edit();
                                                        editor.clear();
                                                        editor.putString("token", MainConfig.TOKEN);
                                                        editor.putString("picurl",picurl);
                                                        editor.putString("username", username);
                                                        editor.putString("nickname", nickname);
                                                        editor.commit();
                                                        Intent intent = new Intent();
                                                        intent.putExtra("flag",1);
                                                        setResult(0,intent);
                                                        finish();
                                                        break;
                                                    default:
                                                        Toast.makeText(RegisterActivity.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                            catch (Exception e){
                                                Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }, new NetConnection.FailCallback() {
                                        @Override
                                        public void onFail(String result) {
                                            Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT).show();
                                        }
                                    },MainConfig.GET_INFORMATION_URL, HttpMethod.POST,"token",MainConfig.TOKEN);
                                    break;
                                default:
                                    Toast.makeText(RegisterActivity.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){

                        }


                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                        Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT).show();
                    }
                },MainConfig.REGIST_URL, HttpMethod.POST,"username",username,"password",password);

            }
        });


    }
}
