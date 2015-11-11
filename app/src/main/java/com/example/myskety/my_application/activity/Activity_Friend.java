package com.example.myskety.my_application.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.adapter.FriendAdapter;
import com.example.myskety.my_application.data.FriendItem;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Activity_Friend extends Activity {

    private ListView mListView;
    private FriendAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        mListView = (ListView)findViewById(R.id.friend_lv);
        mAdapter = new FriendAdapter(Activity_Friend.this);
        mListView.setAdapter(mAdapter);

        findViewById(R.id.friend_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.friend_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Activity_Friend.this,Activity_AddFriend.class);
                startActivity(intent);
            }
        });

        getFriends();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_Friend.this,Activity_SeeFriend.class);
                intent.putExtra("picurl",mAdapter.getItem(position).getPicurl());
                intent.putExtra("username",mAdapter.getItem(position).getUsername());
                intent.putExtra("nickname",mAdapter.getItem(position).getNickname());
                startActivityForResult(intent,0);
            }
        });
    }

    public void getFriends(){

        new NetConnection(new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try{
                    mAdapter.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    int status = jsonObject.getInt("status");
                    switch (status){
                        case 0:
                            JSONArray friends = jsonObject.getJSONArray("friends");
                            for(int index = 0; index < friends.length(); index++){
                                JSONObject item = friends.getJSONObject(index);
                                String username = item.getString("username");
                                String nickname = item.getString("nickname");
                                mAdapter.addItem(new FriendItem(username,nickname));
                            }

                            break;
                        default:
                            Toast.makeText(Activity_Friend.this,jsonObject.getString("reason"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(Activity_Friend.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail(String result) {
                Toast.makeText(Activity_Friend.this,result,Toast.LENGTH_SHORT).show();
            }
        }, MainConfig.GET_FRIEND_URL, HttpMethod.POST,"token",MainConfig.TOKEN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFriends();
    }
}
