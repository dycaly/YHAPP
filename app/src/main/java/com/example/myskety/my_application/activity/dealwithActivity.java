package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myskety.my_application.R;

public class dealwithActivity extends Activity {
    private int flag=-1;
    private EditText deal_ET;
    private TextView deal_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealwith);
        findViewById(R.id.deal_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("flag",-1);
                setResult(0,intent);
                finish();
            }
        });

        deal_TV=(TextView)findViewById(R.id.change_item);
        Intent intent=this.getIntent();
        flag =intent.getIntExtra("flag",0);
        String value = intent.getStringExtra("value");

        if(flag==1){
            deal_TV.setText("昵称");
        }
        if(flag==2){
            deal_TV.setText("姓名");

        }
        if(flag==3){
            deal_TV.setText("年龄");
        }
        if(flag==4){
            deal_TV.setText("性别");
        }
        if(flag==5){
            deal_TV.setText("学校");
        }
        if(flag==6){
            deal_TV.setText("学院");
        }
        if(flag==7){
            deal_TV.setText("手机");
        }
        if(flag==8){
            deal_TV.setText("邮箱");
        }
        deal_ET = (EditText) findViewById(R.id.content);
        deal_ET.setText(value);
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = deal_ET.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("flag", flag);
                intent.putExtra("content", str);
                setResult(1,intent);
                finish();

            }
        });
    }
}
