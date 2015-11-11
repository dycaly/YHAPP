package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

public class Activity_SeeFriend extends Activity {

    private ImageView imageView;
    private String picurl;
    private String username;
    private String nickname;
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
        setContentView(R.layout.activity_seefriend);

        Intent intent = this.getIntent();
        picurl = intent.getStringExtra("picurl");
        username = intent.getStringExtra("username");
        nickname=intent.getStringExtra("nickname");


        imageView = (ImageView)findViewById(R.id.seefriend_header);

        findViewById(R.id.seefriend_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.seefriend_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_SeeFriend.this, Activity_Chat.class);
                intent.putExtra("picurl", picurl);
                intent.putExtra("username", username);
                intent.putExtra("nickname", nickname);
                startActivityForResult(intent, 0);
                finish();
            }
        });
        findViewById(R.id.seefriend_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                            try{
                                JSONObject json=new JSONObject(result);
                                int status = json.getInt("status");
                                if (status == 0){
                                    Toast.makeText(Activity_SeeFriend.this, "删除好友成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Activity_SeeFriend.this, json.getString("reason"),Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(Activity_SeeFriend.this, e.toString(),Toast.LENGTH_SHORT).show();
                            }
                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                        Toast.makeText(Activity_SeeFriend.this, result,Toast.LENGTH_SHORT).show();
                    }
                },MainConfig.DELETEFRIEND_URL,HttpMethod.POST,"token",MainConfig.TOKEN,"username",username);
            }
        });

        nicknameTextView = (TextView)findViewById(R.id.seefriend_nickname_tv);
        nameTextView = (TextView)findViewById(R.id.seefriend_name_tv);
        ageTextView = (TextView)findViewById(R.id.seefriend_age_tv);
        sexTextView = (TextView)findViewById(R.id.seefriend_sex_tv);
        schoolTextView = (TextView)findViewById(R.id.seefriend_school_tv);
        collegeTextView = (TextView)findViewById(R.id.seefriend_college_tv);
        phoneTextView = (TextView)findViewById(R.id.seefriend_phone_tv);
        emailTextView = (TextView)findViewById(R.id.seefriend_email_tv);
        regTextView = (TextView)findViewById(R.id.seefriend_regdate_tv);
        new NetConnection(new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    int status = jsonObject.getInt("status");
                    switch (status){
                        case 0:
                            String picUrl = jsonObject.getString("picUrl");

                            ImageLoader.getInstance().clearMemoryCache();
                            ImageLoader.getInstance().clearDiscCache();
                            ImageLoader.getInstance().loadImage(picUrl, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {
                                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_head);
                                    imageView.setImageBitmap(bm);
                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    imageView.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
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
                            Toast.makeText(Activity_SeeFriend.this, jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(Activity_SeeFriend.this, e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail(String result) {
                Toast.makeText(Activity_SeeFriend.this, result,Toast.LENGTH_SHORT).show();
            }
        }, MainConfig.GET_INFORMATIONBYNAME_URL, HttpMethod.POST,"username",username);


    }
}
