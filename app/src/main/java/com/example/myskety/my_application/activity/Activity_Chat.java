package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.adapter.ChatMsgViewAdapter;
import com.example.myskety.my_application.data.MessageItem;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.example.myskety.my_application.tools.UserMessageStore;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_Chat extends Activity implements View.OnClickListener {
    private ImageView Chat_Send;// 发送btn
    private ImageView Chat_Back;
    private ListView Chat_Listview;
    private EditText Chat_EditTextContent;
    private ChatMsgViewAdapter mAdapter;
    private TextView Chat_title;

    private int fg = 0;
    private String myUsername;
    private String myNickname;

    private String hisUsername;
    private String hisNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychat);
        initview();
        initData();
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("org.dyc.message");
        registerReceiver(new MyBroadcastReceiver(), myIntentFilter);


    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mes = intent.getStringExtra("message");

            try{
                SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
                JSONObject item = new JSONObject(mes);

                if (!item.getString("sender").equals(hisUsername)){
                    return;
                }
                MessageItem mgItem = new MessageItem(true,true,
                        item.getString("sender"),
                        item.getString("nickname"),
                        sp.getString("username","none"),
                        sp.getString("nickname","none"),
                        item.getString("content"),
                        item.getString("time"));
                mAdapter.addItem(mgItem);
                Chat_Listview.setSelection(mAdapter.getCount() - 1);

                UserMessageStore ums = new UserMessageStore(Activity_Chat.this,hisUsername,myUsername);
                ArrayList<MessageItem> messageItems = ums.getMessages();
                for(MessageItem mi:messageItems){
                    if (mi.getDate().equals(item.getString("time"))){
                        mi.setSaw(true);
                    }
                }
                ums.setSawMessage(messageItems);

                Intent sIntent = new Intent("org.dyc.see");
                sIntent.putExtra("username", item.getString("sender"));
                sIntent.putExtra("date",item.getString("time"));
                context.sendBroadcast(sIntent);
            }
            catch (Exception e){
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void initData() {
        SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        myNickname = sp.getString("nickname", "none");
        myUsername = sp.getString("username", "none");


        Intent intent = this.getIntent();
        hisUsername = intent.getStringExtra("username");
        hisNickname = intent.getStringExtra("nickname");
        fg = intent.getIntExtra("flag",0);
        if (fg==1){
            String productname = intent.getStringExtra("productname");
            int price = intent.getIntExtra("productprice",0);
            Chat_EditTextContent.setText("您好，我以"+price+"的价格拍下了您的商品："+productname);
            send();
        }


        Intent sIntent = new Intent("org.dyc.allsee");
        sIntent.putExtra("username",hisUsername);
        sendBroadcast(sIntent);

        Chat_title.setText(hisNickname);
        mAdapter = new ChatMsgViewAdapter(Activity_Chat.this);
        Chat_Listview.setAdapter(mAdapter);

        UserMessageStore ums = new UserMessageStore(Activity_Chat.this,hisUsername,myUsername);
        ArrayList<MessageItem> messages = ums.getMessages();
        for(MessageItem item:messages){
            MessageItem myItem =new MessageItem(false,item.getOther(),item.getSUsername(),item.getSNickname(),item.getRUsername(),item.getRNickname(),item.getMessage(),item.getDate());
            item.setSaw(true);
            mAdapter.addItem(myItem);
        }
        ums.setSawMessage(messages);
        Chat_Listview.setSelection(mAdapter.getCount() - 1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:// 发送按钮点击事件
                send();
                break;
            case R.id.chat_back:// 返回按钮点击事件
                finish();// 结束
                break;
        }
    }

    public void initview() {
        Chat_Listview = (ListView) findViewById(R.id.chat_listview);
        Chat_Send = (ImageView) findViewById(R.id.send);
        Chat_Send.setOnClickListener(this);
        Chat_Back = (ImageView) findViewById(R.id.chat_back);
        Chat_Back.setOnClickListener(this);
        Chat_EditTextContent = (EditText) findViewById(R.id.chat_message);
        Chat_title = (TextView) findViewById(R.id.chat_usr);

    }

    private void send() {
        final String content = Chat_EditTextContent.getText().toString();
        if (content.length() > 0) {
            new NetConnection(new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int status = jsonObject.getInt("status");
                        switch (status){
                            case 0:
                                MessageItem entity = new MessageItem(true,false,myUsername, myNickname,hisUsername,hisNickname, content, getDate());
                                mAdapter.addItem(entity);
                                UserMessageStore ums = new UserMessageStore(Activity_Chat.this,hisUsername,myUsername);
                                ums.addMessage(entity);
                                Chat_Listview.setSelection(mAdapter.getCount() - 1);
                                Chat_EditTextContent.setText("");// 清空编辑框数据

                                Intent sIntent = new Intent("org.dyc.snd");
                                sIntent.putExtra("username", hisUsername);
                                sIntent.putExtra("message",entity.getMessage());
                                sIntent.putExtra("date",entity.getDate());
                                sendBroadcast(sIntent);

                                break;
                            default:
                                Toast.makeText(Activity_Chat.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(Activity_Chat.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail(String result) {
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        Toast.makeText(Activity_Chat.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(Activity_Chat.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }


                }
            }, MainConfig.SEND_MESSAGE_URL, HttpMethod.POST, "token", MainConfig.TOKEN, "username", hisUsername, "content", content);

        }
    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
