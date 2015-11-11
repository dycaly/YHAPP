package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.application.SysApplication;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.example.myskety.my_application.net.NetLogin;

import org.json.JSONObject;


public class MainActivity extends Activity {

    private EditText usernameTV;
    private EditText passwordTV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);

        ImageView back = (ImageView) findViewById(R.id.login_back);
        back.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameTV=(EditText)findViewById(R.id.login_username);
                passwordTV=(EditText)findViewById(R.id.login_password);
                final String username = usernameTV.getText().toString();
                String password = passwordTV.getText().toString();
                if (username.isEmpty()){
                    Toast.makeText(MainActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()){
                    Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                new NetLogin(new NetLogin.SuccessCallback() {
                    public void onSuccess(String token) {

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
                                            MainConfig.picurl = picurl;
                                            editor.putString("token", MainConfig.TOKEN);
                                            editor.putString("picurl", picurl);
                                            editor.putString("username", username);
                                            editor.putString("nickname", nickname);
                                            editor.commit();
                                            Intent intent = new Intent(MainActivity.this,HostActivity.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        default:
                                            Toast.makeText(MainActivity.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                                    }

                                }
                                catch (Exception e){
                                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new NetConnection.FailCallback() {
                            @Override
                            public void onFail(String result) {
                                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                            }
                        },MainConfig.GET_INFORMATION_URL, HttpMethod.POST,"token",MainConfig.TOKEN);

                    }
                }
                        , new NetLogin.FailCallback() {
                    @Override
                    public void onFail(String reson) {
                        Toast.makeText(MainActivity.this,"登陆失败："+reson,Toast.LENGTH_SHORT).show();
                    }
                }, username, password);
            }
        });
        findViewById(R.id.click_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int flag = data.getIntExtra("flag",-1);
        if (flag<0) {
            return;
        }
        finish();
    }
}
