package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;

public class StartActivity extends Activity {
    private  int flag=0;
    private String str;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
        str=sp.getString("token", "none");
        MainConfig.picurl = sp.getString("picurl", MainConfig.LOADIMAGE_URL+"default.png");
        MainConfig.TOKEN=str;
        MainConfig.username = sp.getString("username","");
        flag=sp.getInt("value", 2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MainConfig.TOKEN.equals("none")){
                    Intent tent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(tent);
                    finish();
                }
                else {
                    Intent tent = new Intent(StartActivity.this, HostActivity.class);
                    startActivity(tent);
                    finish();
                }

            }
        }, 2000);
    }

}
