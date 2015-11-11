package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import cz.msebera.android.httpclient.Header;
public class InformationActivity extends Activity {

    private LinearLayout imgLayout;
    private LinearLayout nicknameLayout;
    private LinearLayout nameLayout;
    private LinearLayout ageLayout;
    private LinearLayout sexLayout;
    private LinearLayout schoolLayout;
    private LinearLayout collegeLayout;
    private LinearLayout phoneLayout;
    private LinearLayout emailLayout;

    private TextView nicknameTextView;
    private TextView nameTextView;
    private TextView ageTextView;
    private TextView sexTextView;
    private TextView schoolTextView;
    private TextView collegeTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView regTextView;
    private int  Call_request=1;
    private int flag=-1;


    private ImageView headImageView;

    private static int CAMERA_REQUSET_CODE = 11;
    private static int GALLARY_REQUSET_CODE = 12;
    private static int CROP_REQUSET_CODE = 13;


    private String picUrl = "";
    private String nickname ="";
    private String name ="";
    private int age=0;
    private int sex=0;
    private String school="";
    private String college="";
    private String email="";
    private String phone="";
    private String regdate="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_information);

        findViewById(R.id.infromation_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headImageView = (ImageView)findViewById(R.id.header);
        imgLayout = (LinearLayout)findViewById(R.id.id_img);
        imgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = "选择获取图片的方式";
                String[] items = new String[]{"拍照","相册"};

                new AlertDialog.Builder(InformationActivity.this)
                        .setTitle(title)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        Intent intentc = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intentc,CAMERA_REQUSET_CODE);
                                        break;
                                    case 1:

                                        Intent intentg = new Intent(Intent.ACTION_GET_CONTENT);
                                        intentg.setType("image/*");
                                        startActivityForResult(intentg,GALLARY_REQUSET_CODE);

                                        break;
                                }
                            }
                        })
                        .show();



            }
        });


        //昵称
        nicknameLayout = (LinearLayout)findViewById(R.id.user_nickname);
        nicknameTextView = (TextView)findViewById(R.id.user_nickname_tv);
        nicknameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("value",nickname);
                startActivityForResult(intent, Call_request);

            }
        });

        //昵称
        nameLayout = (LinearLayout)findViewById(R.id.user_name);
        nameTextView = (TextView)findViewById(R.id.user_name_tv);
        nameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",2);
                intent.putExtra("value",name);
                startActivityForResult(intent, Call_request);

            }
        });

        //昵称
        ageLayout = (LinearLayout)findViewById(R.id.user_age);
        ageTextView = (TextView)findViewById(R.id.user_age_tv);
        ageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",3);
                intent.putExtra("value",age+"");
                startActivityForResult(intent, Call_request);

            }
        });

        //昵称
        sexLayout = (LinearLayout)findViewById(R.id.user_sex);
        sexTextView = (TextView)findViewById(R.id.user_sex_tv);
        sexLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",4);
                intent.putExtra("value",sex+"");
                startActivityForResult(intent, Call_request);
            }
        });

        //昵称
        schoolLayout = (LinearLayout)findViewById(R.id.user_school);
        schoolTextView = (TextView)findViewById(R.id.user_school_tv);
        schoolLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",5);
                intent.putExtra("value",school);
                startActivityForResult(intent, Call_request);
            }
        });

        //昵称
        collegeLayout = (LinearLayout)findViewById(R.id.user_college);
        collegeTextView = (TextView)findViewById(R.id.user_college_tv);
        collegeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",6);
                intent.putExtra("value",college);
                startActivityForResult(intent, Call_request);
            }
        });

        //昵称
        phoneLayout = (LinearLayout)findViewById(R.id.user_phone);
        phoneTextView = (TextView)findViewById(R.id.user_phone_tv);
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",7);
                intent.putExtra("value",phone);
                startActivityForResult(intent, Call_request);
            }
        });

        //昵称
        emailLayout = (LinearLayout)findViewById(R.id.user_email);
        emailTextView = (TextView)findViewById(R.id.user_email_tv);
        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationActivity.this,dealwithActivity.class);
                intent.putExtra("flag",8);
                intent.putExtra("value",email);
                startActivityForResult(intent, Call_request);
            }
        });
        regTextView = (TextView)findViewById(R.id.user_regdate_tv);
        initUser();
    }



    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_REQUSET_CODE);
    }

    private Uri saveBitmap(Bitmap bm){

        File imgDir = new File(Environment.getExternalStorageDirectory()+"/com.ktBoys.xiaotao");
        if (!imgDir.exists()){
            imgDir.mkdir();
        }
        File imgFile = new File(imgDir.getAbsolutePath() +"head.png");
        try {
            FileOutputStream fos = new FileOutputStream(imgFile);
            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();

            return Uri.fromFile(imgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(InformationActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(InformationActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    private void sendBitmap(Bitmap bm){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte bytes[] = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("image",img);
        params.add("name",MainConfig.username+".png");
        client.post(MainConfig.IMAGE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {


                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(InformationActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();

                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                        Toast.makeText(InformationActivity.this,result,Toast.LENGTH_SHORT).show();
                    }
                },MainConfig.SET_INFROMATION_URL,HttpMethod.POST,
                        "token",MainConfig.TOKEN,
                        "picurl",MainConfig.LOADIMAGE_URL+MainConfig.username+".png",
                        "nickname",nickname,
                        "name",name,
                        "age",age+"",
                        "sex",""+sex,
                        "school",school,
                        "college",college,
                        "phone",phone,
                        "email",email
                );



            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(InformationActivity.this,"头像上传失败",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private Uri convertUri(Uri uri){
        InputStream is= null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bm = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void onActivityResult(int requestcode ,int resultcode,Intent data){

        if (requestcode == CAMERA_REQUSET_CODE){
            if (data == null){
                return;
            }
            else{
                Bundle extra = data.getExtras();
                if (extra!=null){
                    Bitmap bm = extra.getParcelable("data");

                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        }
        if (requestcode == GALLARY_REQUSET_CODE){
            if (data == null){
                return;
            }
            else{
                Uri uri = data.getData();
                Uri auri = convertUri(uri);
                startImageZoom(auri);
            }
        }
        if (requestcode == CROP_REQUSET_CODE){

            if (data == null){
                return;

            }
            else{

                Bundle extras = data.getExtras();
                if (extras!=null){

                    Bitmap bm = extras.getParcelable("data");

                    headImageView.setImageBitmap(bm);
                    sendBitmap(bm);
                }
            }
        }


        flag = data.getIntExtra("flag",0);
        if (resultcode == 1){
            String str = data.getStringExtra("content");
            if (flag == 1) {
                nicknameTextView.setText(str);
                nickname = str;

            }
            if (flag == 2) {
                nameTextView.setText(str);
                name = str;
            }
            if (flag == 3) {
                ageTextView.setText(str);
                age = Integer.parseInt(str);
            }
            if (flag == 4) {

                sexTextView.setText(str);
                sex = str.equals("男")?0:1;
            }
            if (flag == 5) {
                schoolTextView.setText(str);
                school = str;
            }
            if (flag == 6) {
                collegeTextView.setText(str);
                college = str;
            }
            if (flag == 7) {
                phoneTextView.setText(str);
                phone = str;
            }
            if (flag == 8) {
                emailTextView.setText(str);
                email = str;
            }

            new NetConnection(new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        int status = jsonObject.getInt("status");
                        if (status == 0){
                            Toast.makeText(InformationActivity.this,"用户信息修改成功",Toast.LENGTH_SHORT).show();
                            if (flag !=1){
                                return;
                            }
                            SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("nickname", nickname);
                            editor.commit();
                        }
                        else{
                            Toast.makeText(InformationActivity.this,"服务器异常",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {

                    }

                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail(String result) {
                    Toast.makeText(InformationActivity.this,result,Toast.LENGTH_SHORT).show();
                }
            },MainConfig.SET_INFROMATION_URL,HttpMethod.POST,
                    "token",MainConfig.TOKEN,
                    "picurl",MainConfig.LOADIMAGE_URL+MainConfig.TOKEN+".png",
                    "nickname",nickname,
                    "name",name,
                    "age",age+"",
                    "sex",""+sex,
                    "school",school,
                    "college",college,
                    "phone",phone,
                    "email",email
            );
        }


    }

    public void initUser(){



        if (MainConfig.TOKEN != null && !MainConfig.TOKEN.equals("none")){

            new NetConnection(new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try{
                        JSONObject jsonObject = new JSONObject(result);

                        int status = jsonObject.getInt("status");
                        switch (status){
                            case 0:

                                picUrl = jsonObject.getString("picUrl");

                                ImageLoader.getInstance().clearMemoryCache();
                                ImageLoader.getInstance().clearDiscCache();
                                ImageLoader.getInstance().loadImage(picUrl, new ImageLoadingListener() {
                                    @Override
                                    public void onLoadingStarted(String s, View view) {

                                    }

                                    @Override
                                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                                        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.default_head);
                                            headImageView.setImageBitmap(bm);
                                    }

                                    @Override
                                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                            headImageView.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onLoadingCancelled(String s, View view) {

                                    }
                                });

                                nickname = jsonObject.getString("nickname");
                                nicknameTextView.setText(nickname);
                                name = jsonObject.getString("name");
                                nameTextView.setText(name);
                                age = jsonObject.getInt("age");
                                ageTextView.setText(""+age);
                                sex = jsonObject.getInt("sex");
                                sexTextView.setText(sex==0?"男":"女");
                                school = jsonObject.getString("school");
                                schoolTextView.setText(school);
                                college = jsonObject.getString("college");
                                collegeTextView.setText(college);
                                phone = jsonObject.getString("phone");
                                phoneTextView.setText(phone);
                                email = jsonObject.getString("email");
                                emailTextView.setText(email);
                                regdate = jsonObject.getString("regdate");
                                regTextView.setText(regdate);
                                break;
                            default:
                                Toast.makeText(InformationActivity.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                        Toast.makeText(InformationActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail(String result) {
                    Toast.makeText(InformationActivity.this,result,Toast.LENGTH_SHORT).show();
                }
            },MainConfig.GET_INFORMATION_URL, HttpMethod.POST,"token",MainConfig.TOKEN);
        }
        else{
            Toast.makeText(InformationActivity.this,"用户信息有误",Toast.LENGTH_SHORT).show();
        }
    }
}
