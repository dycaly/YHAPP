package com.example.myskety.my_application.activity;




import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.View.IconView;
import com.example.myskety.my_application.data.FriendReqItem;
import com.example.myskety.my_application.data.MessageItem;
import com.example.myskety.my_application.fragment.FragmentController;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.example.myskety.my_application.tools.UserFriendReqStore;
import com.example.myskety.my_application.tools.UserMessageStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HostActivity extends FragmentActivity implements OnClickListener {

	private FragmentController fragmentController;
	private IconView homeIconView;
	private IconView sortIconView;
	private IconView nearIconView;
	private IconView mineIconView;

	private Context context =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host);
		initView();
		getMessage();
	}
	public void getMessage(){
		context =HostActivity.this;
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				new NetConnection(new NetConnection.SuccessCallback() {
					@Override
					public void onSuccess(String result) {
							try{
								JSONObject json = new JSONObject(result);
								int status = json.getInt("status");
								if (status !=0){
									return;
								}
								JSONArray messages = json.getJSONArray("messages");
								for(int index =0; index <messages.length(); index++){
									JSONObject item = messages.getJSONObject(index);
									SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
									UserMessageStore ums = new UserMessageStore(HostActivity.this,item.getString("sender"),sp.getString("username","none"));

									MessageItem mgItem = new MessageItem(false,true,
											item.getString("sender"),
											item.getString("nickname"),
											sp.getString("username","none"),
											sp.getString("nickname","none"),
											item.getString("content"),
											item.getString("time"));
									ums.addMessage(mgItem);

									Intent intent = new Intent("org.dyc.message");
									intent.putExtra("message", item.toString());
									context.sendBroadcast(intent);

								}
							}
							catch (Exception e){

							}
					}
				}, new NetConnection.FailCallback() {
					@Override
					public void onFail(String result) {

					}
				},MainConfig.GET_MESSAGE_URL, HttpMethod.POST,"token",MainConfig.TOKEN);


				new NetConnection(new NetConnection.SuccessCallback() {
					@Override
					public void onSuccess(String result) {
						try{
							JSONObject json = new JSONObject(result);
							JSONArray items= json.getJSONArray("reqs");
							for (int i =0; i<items.length(); i++){
								JSONObject item = items.getJSONObject(i);
								boolean issaw = item.getBoolean("issaw");
								String susername = item.getString("susername");
								String snickname = item.getString("snickname");
								String rusername = item.getString("rusername");
								String date = getDate();
								FriendReqItem fri=new FriendReqItem(issaw,susername,snickname,rusername,date);
								SharedPreferences sp=getSharedPreferences("User", Context.MODE_PRIVATE);
								UserFriendReqStore ufrs = new UserFriendReqStore(context,sp.getString("username","none"));
								ufrs.addFriendReq(fri);
								Intent intent = new Intent("org.dyc.req");
								intent.putExtra("message", item.toString());
								intent.putExtra("date",date);
								context.sendBroadcast(intent);
							}
						}
						catch (Exception e){
							Toast.makeText(HostActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
						}
					}
				}, new NetConnection.FailCallback() {
					@Override
					public void onFail(String result) {

					}
				},MainConfig.GET_FRIENDREQ_URL,HttpMethod.POST,"token",MainConfig.TOKEN);

				handler.postDelayed(this, 1000);
			}
		};
		//handler.postDelayed(runnable, 1000);
	}
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	private void initView() {
		fragmentController = FragmentController.getInstance(this, R.id.id_activity_main_framelayout);
		fragmentController.showFragment(0);

		homeIconView = (IconView) findViewById(R.id.id_icon_view_home);
		homeIconView.setOnClickListener(this);
		sortIconView = (IconView) findViewById(R.id.id_icon_view_wait);
		sortIconView.setOnClickListener(this);
		nearIconView = (IconView) findViewById(R.id.id_icon_view_info);
		nearIconView.setOnClickListener(this);
		mineIconView = (IconView) findViewById(R.id.id_icon_view_mine);
		mineIconView.setOnClickListener(this);
		clearIconView();
		homeIconView.setIconAlpha(1.0f);
	}

	private void clearIconView() {
		homeIconView.setIconAlpha(0);
		sortIconView.setIconAlpha(0);
		nearIconView.setIconAlpha(0);
		mineIconView.setIconAlpha(0);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.id_icon_view_home:
			fragmentController.showFragment(0);
			clearIconView();
			homeIconView.setIconAlpha(1.0f);
			break;
		case R.id.id_icon_view_wait:
			fragmentController.showFragment(1);
			clearIconView();
			sortIconView.setIconAlpha(1.0f);
			break;
		case R.id.id_icon_view_info:
			fragmentController.showFragment(2);
			clearIconView();
			nearIconView.setIconAlpha(1.0f);
			break;
		case R.id.id_icon_view_mine:
			fragmentController.showFragment(3);
			clearIconView();
			mineIconView.setIconAlpha(1.0f);
			break;

		}
	}

}
