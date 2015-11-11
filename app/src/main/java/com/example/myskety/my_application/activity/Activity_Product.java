package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.data.ProductItem;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

import java.sql.Timestamp;

public class Activity_Product extends Activity {

    private String sellername;
    private String sellernickname;
    private int productid;
    private ImageView imageView;
    private TextView nameTextView;
    private TextView introTextView;
    private TextView hightTextView;
    private TextView nowTextView;
    private TextView timeTextView;
    private TextView cutTextView;
    private TextView stateTextView;
    private RelativeLayout buyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        findViewById(R.id.ac_product_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView = (ImageView) findViewById(R.id.ac_product_img);



        nameTextView = (TextView) findViewById(R.id.ac_product_name);
        introTextView = (TextView) findViewById(R.id.ac_product_intro);
        hightTextView = (TextView) findViewById(R.id.ac_product_hight);
        nowTextView = (TextView) findViewById(R.id.ac_product_now);
        timeTextView = (TextView) findViewById(R.id.ac_product_time);
        cutTextView = (TextView)findViewById(R.id.ac_product_cut);
        stateTextView = (TextView)findViewById(R.id.ac_product_state);
        buyLayout = (RelativeLayout) findViewById(R.id.ac_product_buy);
        buyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new NetConnection(new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {

                        try{

                            JSONObject json = new JSONObject(result);
                            switch (json.getInt("status")){
                                case 0:
                                    Toast.makeText(Activity_Product.this,"成功拍下",Toast.LENGTH_SHORT).show();
                                    new NetConnection(new NetConnection.SuccessCallback() {
                                        @Override
                                        public void onSuccess(String result) {
                                            try{
                                                JSONObject json=new JSONObject(result);
                                                int status = json.getInt("status");

                                                    new NetConnection(new NetConnection.SuccessCallback() {
                                                        @Override
                                                        public void onSuccess(String result) {
                                                            try {
                                                                JSONObject item = new JSONObject(result);
                                                                int productid = item.getInt("productid");
                                                                String producturl = item.getString("producturl");

                                                                String productname = item.getString("productname");
                                                                String productintro = item.getString("productintro");
                                                                int hightestprice = item.getInt("hightestprice");
                                                                int lowestprice = item.getInt("lowestprice");
                                                                int cuttime = item.getInt("cuttime");
                                                                int cutprice = item.getInt("cutprice");
                                                                int sstatus = item.getInt("status");
                                                                sellername = item.getString("sellername");
                                                                sellernickname = item.getString("sellernickname");
                                                                String selldate = item.getString("selldate");
                                                                String buyername = item.getString("buyername");
                                                                int lastprice = item.getInt("lastprice");
                                                                String classify = item.getString("classify");
                                                                Intent intent = new Intent(Activity_Product.this,Activity_Chat.class);
                                                                intent.putExtra("username",sellername);
                                                                intent.putExtra("nickname",sellernickname);
                                                                intent.putExtra("flag",1);
                                                                intent.putExtra("productname",productname);
                                                                intent.putExtra("productprice",lastprice);
                                                                startActivity(intent);


                                                            } catch (Exception e)

                                                            {
                                                                Toast.makeText(Activity_Product.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                            }


                                                        }
                                                    }

                                                            , new NetConnection.FailCallback()

                                                    {
                                                        @Override
                                                        public void onFail(String result) {
                                                            Toast.makeText(Activity_Product.this, result, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                            , MainConfig.GET_PRODUCTBYID_URL, HttpMethod.POST, "productid", productid + "");





                                            }
                                            catch (Exception e){
                                                Toast.makeText(Activity_Product.this, e.toString(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new NetConnection.FailCallback() {
                                        @Override
                                        public void onFail(String result) {
                                            Toast.makeText(Activity_Product.this, result,Toast.LENGTH_SHORT).show();
                                        }
                                    }, MainConfig.URL+"AddFriend", HttpMethod.POST,"token",MainConfig.TOKEN,"username",sellername);
                                    break;
                                default:
                                    Toast.makeText(Activity_Product.this,json.getString("reason"),Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){
                            Toast.makeText(Activity_Product.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail(String result) {
                        Toast.makeText(Activity_Product.this,result,Toast.LENGTH_SHORT).show();
                    }
                },MainConfig.BUY_PRODUCT_URL,HttpMethod.POST,"token",MainConfig.TOKEN,"productid",productid+"");

            }
        });


        Intent intent = this.getIntent();
        productid = intent.getIntExtra("productid", 0);

        initView();


    }

    private void initView() {
        new NetConnection(new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject item = new JSONObject(result);
                    int productid = item.getInt("productid");
                    String producturl = item.getString("producturl");

                    ImageLoader.getInstance().loadImage(producturl, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.product);
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


                    String productname = item.getString("productname");
                    String productintro = item.getString("productintro");
                    int hightestprice = item.getInt("hightestprice");
                    int lowestprice = item.getInt("lowestprice");
                    int cuttime = item.getInt("cuttime");
                    int cutprice = item.getInt("cutprice");
                    int sstatus = item.getInt("status");
                    sellername = item.getString("sellername");
                    sellernickname = item.getString("sellernickname");
                    String selldate = item.getString("selldate");
                    String buyername = item.getString("buyername");
                    int lastprice = item.getInt("lastprice");
                    String classify = item.getString("classify");
                    ProductItem pi = new ProductItem(productid, producturl, productname, productintro,
                            hightestprice, lowestprice, cuttime,cutprice, sstatus, sellername, sellernickname,
                            selldate, buyername, lastprice, classify);

                    nameTextView.setText(productname);
                    introTextView.setText(productintro);
                    hightTextView.setText("￥" + hightestprice + ".00");
                    nowTextView.setText("￥" + lastprice + ".00");

                    Timestamp ts = Timestamp.valueOf(selldate);
                    Timestamp tn = new Timestamp(System.currentTimeMillis());

                    long lefttime =  cuttime;
                    long day = lefttime / (60 * 60 * 24);

                    lefttime = lefttime % (60 * 60 * 24);
                    long hour = lefttime / (60 * 60);

                    lefttime = lefttime % (60 * 60);
                    long min = lefttime / 60;

                    lefttime = lefttime % 60;
                    long sec = lefttime;
                    timeTextView.setText(day + "天" + hour + "小时" + min + "分钟" + sec + "秒");
                    cutTextView.setText("￥"+cutprice+".00");
                    if (sstatus == 0){
                        stateTextView.setText("在售");
                    }
                    else if(sstatus == 2){
                        stateTextView.setText("已售出");
                        buyLayout.setClickable(false);
                        buyLayout.setEnabled(false);

                    }
                    else{
                        stateTextView.setText("不可取");
                        buyLayout.setClickable(false);
                        buyLayout.setEnabled(false);
                    }

                } catch (Exception e)

                {
                    Toast.makeText(Activity_Product.this, e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }

                , new NetConnection.FailCallback()

        {
            @Override
            public void onFail(String result) {
                Toast.makeText(Activity_Product.this, result, Toast.LENGTH_SHORT).show();
            }
        }

                , MainConfig.GET_PRODUCTBYID_URL, HttpMethod.POST, "productid", productid + "");
    }
}
