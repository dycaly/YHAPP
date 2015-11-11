package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Activity_AddProduct extends Activity {

    private static int CAMERA_REQUSET_CODE = 11;
    private static int CROP_REQUSET_CODE = 13;

    private String pdname="_";
    private ImageView imageView;
    private LinearLayout imgLayout;
    private EditText nameEditText;
    private EditText introEditText;
    private EditText hightEditText;
    private EditText lowEditText;
    private EditText cuttimeEditText;
    private EditText cutpriceEditText;
    private Spinner spinner;
    private RelativeLayout reLayout;
    private int classid = 0;
    private ArrayList<String> items = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        findViewById(R.id.ap_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView = (ImageView)findViewById(R.id.ap_header);
        imgLayout = (LinearLayout)findViewById(R.id.ap_img);
        imgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentc = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentc,CAMERA_REQUSET_CODE);
            }
        });

        nameEditText = (EditText) findViewById(R.id.ap_name);
        introEditText = (EditText) findViewById(R.id.ap_intro);
        hightEditText = (EditText) findViewById(R.id.ap_hight);
        lowEditText = (EditText) findViewById(R.id.ap_low);
        cuttimeEditText = (EditText) findViewById(R.id.ap_cuttime);
        cutpriceEditText = (EditText) findViewById(R.id.ap_cutprice);
        spinner = (Spinner)findViewById(R.id.ap_sp);

        items.add("life");
        items.add("book");
        items.add("study");
        items.add("elect");
        items.add("other");

        reLayout = (RelativeLayout)findViewById(R.id.ap_re);
        reLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                if (name.isEmpty())
                {
                    Toast.makeText(Activity_AddProduct.this,"商品名称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String intro = introEditText.getText().toString();
                if (intro.isEmpty())
                {
                    Toast.makeText(Activity_AddProduct.this,"商品简介不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String hight = hightEditText.getText().toString();
                if (hight.isEmpty())
                {
                    Toast.makeText(Activity_AddProduct.this,"最高价格不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String low = lowEditText.getText().toString();
                if (low.isEmpty())
                {
                    Toast.makeText(Activity_AddProduct.this,"最低价格不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String cuttime = cuttimeEditText.getText().toString();
                if (cuttime.isEmpty())
                {
                    Toast.makeText(Activity_AddProduct.this,"降价时间不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String cutprice = cutpriceEditText.getText().toString();
                if (cutprice.isEmpty())
                {
                    Toast.makeText(Activity_AddProduct.this,"降价价格不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String classify = items.get(classid);

                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {

                        Toast.makeText(Activity_AddProduct.this,"发布成功",Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {

                        Toast.makeText(Activity_AddProduct.this,result,Toast.LENGTH_SHORT).show();
                    }
                }, MainConfig.ADD_PRODUCT_URL, HttpMethod.POST,
                        "token",MainConfig.TOKEN,
                        "producturl",MainConfig.LOADIMAGE_URL+pdname+".png",
                        "productname",name,
                        "productintro",intro,
                        "classname",classify,
                        "hightestprice",hight,
                        "lowestprice",low,
                        "cuttime",cuttime,
                        "cutprice",cutprice);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                classid = position;
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }
    public void onActivityResult(int requestcode ,int resultcode,Intent data) {

        if (requestcode == CAMERA_REQUSET_CODE) {
            if (data == null) {
                return;
            } else {
                Bundle extra = data.getExtras();
                if (extra != null) {
                    Bitmap bm = extra.getParcelable("data");

                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        }
        if (requestcode == CROP_REQUSET_CODE) {

            if (data == null) {
                return;

            } else {

                Bundle extras = data.getExtras();
                if (extras != null) {

                    Bitmap bm = extras.getParcelable("data");

                    imageView.setImageBitmap(bm);
                    sendBitmap(bm);
                }
            }
        }
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
            Toast.makeText(Activity_AddProduct.this,e.toString(),Toast.LENGTH_SHORT).show();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Activity_AddProduct.this,e.toString(),Toast.LENGTH_SHORT).show();
            return null;
        }
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
    private void sendBitmap(Bitmap bm){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte bytes[] = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("image", img);
        pdname = System.currentTimeMillis()+"";
        params.add("name", pdname + ".png");
        client.post(MainConfig.IMAGE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {

                Toast.makeText(Activity_AddProduct.this, "图片上传成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(Activity_AddProduct.this, "图片上传失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
