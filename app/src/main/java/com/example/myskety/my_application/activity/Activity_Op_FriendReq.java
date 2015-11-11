package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.data.FriendReqItem;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.example.myskety.my_application.tools.UserFriendReqStore;

import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Op_FriendReq extends Activity {

    private String username;
    private TextView nicknameTextView;
    private TextView nameTextView;
    private TextView ageTextView;
    private TextView sexTextView;
    private TextView schoolTextView;
    private TextView collegeTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView regTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_friendreq);
        final Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        findViewById(R.id.reqfriend_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(-1, intent);
                finish();
            }
        });
        nicknameTextView = (TextView)findViewById(R.id.reqfriend_nickname_tv);
        nameTextView = (TextView)findViewById(R.id.reqfriend_name_tv);
        ageTextView = (TextView)findViewById(R.id.reqfriend_age_tv);
        sexTextView = (TextView)findViewById(R.id.reqfriend_sex_tv);
        schoolTextView = (TextView)findViewById(R.id.reqfriend_school_tv);
        collegeTextView = (TextView)findViewById(R.id.reqfriend_college_tv);
        phoneTextView = (TextView)findViewById(R.id.reqfriend_phone_tv);
        emailTextView = (TextView)findViewById(R.id.reqfriend_email_tv);
        regTextView = (TextView)findViewById(R.id.reqfriend_regdate_tv);

        findViewById(R.id.reqfriend_agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                            try{
                                JSONObject jsonObject =new JSONObject(result);
                                int status = jsonObject.getInt("status");
                                if (status == 0){
                                    Toast.makeText(Activity_Op_FriendReq.this,"已添加",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Activity_Op_FriendReq.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(Activity_Op_FriendReq.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }

                        SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
                        UserFriendReqStore ufrs = new UserFriendReqStore(Activity_Op_FriendReq.this,sp.getString("username","none"));
                        ArrayList<FriendReqItem> items = ufrs.getFriendReq();
                        for (FriendReqItem fr:items){
                            if (fr.getSusername().equals(username)){
                                items.remove(fr);
                            }
                        }
                        ufrs.setFriendReq(items);
                        Intent intent = new Intent();
                        intent.putExtra("username",username);
                        setResult(1, intent);
                        finish();
                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                            Toast.makeText(Activity_Op_FriendReq.this,result,Toast.LENGTH_SHORT).show();
                    }
                },MainConfig.OP_FRIENDREQ_URL,HttpMethod.POST,"token",MainConfig.TOKEN,"username",username,"method","0");
            }
        });
        findViewById(R.id.reqfriend_refuse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try{
                            JSONObject jsonObject =new JSONObject(result);
                            int status = jsonObject.getInt("status");
                            if (status == 0){
                                Toast.makeText(Activity_Op_FriendReq.this,"已拒绝",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Activity_Op_FriendReq.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(Activity_Op_FriendReq.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                        SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
                        UserFriendReqStore ufrs = new UserFriendReqStore(Activity_Op_FriendReq.this,sp.getString("username","none"));
                        ArrayList<FriendReqItem> items = ufrs.getFriendReq();
                        for (FriendReqItem fr:items){
                            if (fr.getSusername().equals(username)){
                                items.remove(fr);
                            }
                        }
                        ufrs.setFriendReq(items);

                        Intent intent = new Intent();
                        intent.putExtra("username",username);
                        setResult(1,intent);
                        finish();
                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                        Toast.makeText(Activity_Op_FriendReq.this,result,Toast.LENGTH_SHORT).show();
                    }
                },MainConfig.OP_FRIENDREQ_URL,HttpMethod.POST,"token",MainConfig.TOKEN,"username",username,"method","1");
            }
        });


        new NetConnection(new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    int status = jsonObject.getInt("status");
                    switch (status){
                        case 0:
                            String nickname = jsonObject.getString("nickname");
                            nicknameTextView.setText(nickname);
                            String name = jsonObject.getString("name");
                            nameTextView.setText(name);
                            int age = jsonObject.getInt("age");
                            ageTextView.setText(""+age);
                            int sex = jsonObject.getInt("sex");
                            sexTextView.setText(sex==0?"男":"女");
                            String school = jsonObject.getString("school");
                            schoolTextView.setText(school);
                            String college = jsonObject.getString("college");
                            collegeTextView.setText(college);
                            String phone = jsonObject.getString("phone");
                            phoneTextView.setText(phone);
                            String email = jsonObject.getString("email");
                            emailTextView.setText(email);
                            String regdate = jsonObject.getString("regdate");
                            regTextView.setText(regdate);
                            break;
                        default:
                            Toast.makeText(Activity_Op_FriendReq.this, jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(Activity_Op_FriendReq.this, e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail(String result) {
                Toast.makeText(Activity_Op_FriendReq.this, result,Toast.LENGTH_SHORT).show();
            }
        }, MainConfig.GET_INFORMATIONBYNAME_URL, HttpMethod.POST,"username",username);
    }
}
