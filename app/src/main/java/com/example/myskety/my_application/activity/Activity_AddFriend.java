package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;

import org.json.JSONObject;

public class Activity_AddFriend extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);

        findViewById(R.id.addfriend_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.addfriend_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)findViewById(R.id.addfriend_et)).getText().toString();
                if (username.isEmpty())
                    return;
                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try{
                            JSONObject json=new JSONObject(result);
                            int status = json.getInt("status");
                            if (status == 0){
                                Toast.makeText(Activity_AddFriend.this, "已发送好友请求",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Activity_AddFriend.this, json.getString("reason"),Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(Activity_AddFriend.this, e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                        Toast.makeText(Activity_AddFriend.this, result,Toast.LENGTH_SHORT).show();
                    }
                }, MainConfig.REQADDFRIEND_URL, HttpMethod.POST,"token",MainConfig.TOKEN,"username",username);
            }
        });
    }
}
