package com.example.myskety.my_application.fragment;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myskety.my_application.R;
import com.example.myskety.my_application.activity.Activity_Chat;
import com.example.myskety.my_application.activity.Activity_Friend;
import com.example.myskety.my_application.activity.Activity_Op_FriendReq;
import com.example.myskety.my_application.adapter.FriendReqAdapter;
import com.example.myskety.my_application.adapter.MessageAdapter;
import com.example.myskety.my_application.data.FriendReqItem;
import com.example.myskety.my_application.data.MessageItem;
import com.example.myskety.my_application.data.UserItem;
import com.example.myskety.my_application.tools.UserFriendReqStore;
import com.example.myskety.my_application.tools.UserMessageDir;
import com.example.myskety.my_application.tools.UserMessageStore;

import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Message extends Fragment {

	private ListView mListView;
	private ListView reqListView;
	private MessageAdapter mAdapter;
	private FriendReqAdapter reqAdapter;


	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_message, null);



		mListView = (ListView)rootView.findViewById(R.id.message_listview);
		reqListView = (ListView)rootView.findViewById(R.id.req_listview);
		mAdapter = new MessageAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		reqAdapter = new FriendReqAdapter(getActivity());
		reqListView.setAdapter(reqAdapter);

		ImageView addImageView = (ImageView)rootView.findViewById(R.id.message_tb).findViewById(R.id.tb_add);
		addImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Activity_Friend.class);
				startActivityForResult(intent, 0);
			}
		});

		initView();

		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("org.dyc.message");
		getActivity().registerReceiver(new MyBroadcastReceiver(), myIntentFilter);


		IntentFilter mySeeIntentFilter = new IntentFilter();
		mySeeIntentFilter.addAction("org.dyc.see");
		getActivity().registerReceiver(new MySeeBroadcastReceiver(), mySeeIntentFilter);

		IntentFilter myAllSeeIntentFilter = new IntentFilter();
		myAllSeeIntentFilter.addAction("org.dyc.allsee");
		getActivity().registerReceiver(new MyAllSeeBroadcastReceiver(), myAllSeeIntentFilter);

		IntentFilter myReqIntentFilter = new IntentFilter();
		myReqIntentFilter.addAction("org.dyc.req");
		getActivity().registerReceiver(new MyReqBroadcastReceiver(), myReqIntentFilter);


		IntentFilter mySndIntentFilter = new IntentFilter();
		mySndIntentFilter.addAction("org.dyc.snd");
		getActivity().registerReceiver(new MySndBroadcastReceiver(), mySndIntentFilter);
		return rootView;
	}

	public class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String mes = intent.getStringExtra("message");

			try{
				SharedPreferences sp=getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
				JSONObject item = new JSONObject(mes);
				String sender = item.getString("sender");
				String nickname = item.getString("nickname");
				String message =item.getString("content");
				String date =item.getString("time");
				int i =0;
				for(i=0; i< mAdapter.getCount(); i++){
					UserItem ui=mAdapter.getItem(i);
					if (ui.getUsername().equals(sender)){
						ui.setMessage(message);
						ui.setDate(date);
						ui.setIssaw(false);
						mAdapter.removeItem(i);
						mAdapter.addItem(0,ui);
						break;
					}
				}
				if (i == mAdapter.getCount()){
					UserItem ui= new UserItem(false,sender,nickname,message,date);
					mAdapter.addItem(0,ui);
				}



			}
			catch (Exception e){
				Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
			}
		}
	}

	public class MySeeBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String username = intent.getStringExtra("username");
			String date = intent.getStringExtra("date");
			for(int i =0; i < mAdapter.getCount(); i++){
				UserItem ui = mAdapter.getItem(i);
				if (ui.getUsername().equals(username)&&ui.getDate().equals(date)){
					ui.setIssaw(true);
					mAdapter.removeItem(i);
					mAdapter.addItem(i,ui);
					break;
				}

			}

		}
	}
	public class MyAllSeeBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String username = intent.getStringExtra("username");
			for(int i =0; i < mAdapter.getCount(); i++){
				UserItem ui = mAdapter.getItem(i);
				if (ui.getUsername().equals(username)){
					ui.setIssaw(true);
					mAdapter.removeItem(i);
					mAdapter.addItem(i,ui);
					break;
				}

			}

		}
	}

	public class MySndBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String username = intent.getStringExtra("username");
			String message = intent.getStringExtra("message");
			String date = intent.getStringExtra("date");
			for(int i =0; i < mAdapter.getCount(); i++){
				UserItem ui = mAdapter.getItem(i);
				if (ui.getUsername().equals(username)){
					ui.setIssaw(true);
					ui.setMessage(message);
					ui.setDate(date);
					mAdapter.removeItem(i);
					mAdapter.addItem(i,ui);
					break;
				}

			}

		}
	}
	public class MyReqBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String mes = intent.getStringExtra("message");

			try{
				JSONObject item = new JSONObject(mes);

				for(int i =0; i< reqAdapter.getCount();i++){
					FriendReqItem fr = reqAdapter.getItem(i);
					if (fr.getSusername().equals(item.getString("susername"))){
						return;
					}
				}

				boolean issaw = item.getBoolean("issaw");
				String susername = item.getString("susername");
				String snickname = item.getString("snickname");
				String rusername = item.getString("rusername");
				String date = intent.getStringExtra("date");

				FriendReqItem fri=new FriendReqItem(issaw,susername,snickname,rusername,date);
				reqAdapter.addItem(fri);

			}
			catch (Exception e){
				Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
			}
		}
	}
	private void initView() {
		mAdapter.clear();
		SharedPreferences sp=getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
		UserMessageDir umd = new UserMessageDir(getActivity(),sp.getString("username","none"));

		ArrayList<String> friends = umd.getFriendsName();

		for(String str:friends){
			UserMessageStore ums = new UserMessageStore(getActivity(),str,sp.getString("username","none"));
			ArrayList<MessageItem> messages = ums.getMessages();
			if (messages.size() >0){
				MessageItem item = messages.get(messages.size()-1);
				mAdapter.addItem(new UserItem(item.getIsSaw(),str,item.getOther() ?item.getSNickname():item.getRNickname(),item.getMessage(),item.getDate()));
			}

		}
		UserFriendReqStore ufrs = new UserFriendReqStore(getActivity(),sp.getString("username","none"));

		ArrayList<FriendReqItem> fris = ufrs.getFriendReq();

		for (FriendReqItem item:fris){
			reqAdapter.addItem(item);
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				UserItem item = mAdapter.getItem(position);
				Intent intent = new Intent(getActivity(), Activity_Chat.class);
				intent.putExtra("picurl", item.getUrl());
				intent.putExtra("username", item.getUsername());
				intent.putExtra("nickname", item.getNickname());
				startActivityForResult(intent, 0);
			}
		});
		reqListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FriendReqItem fri=reqAdapter.getItem(position);
				Intent intent = new Intent(getActivity(), Activity_Op_FriendReq.class);
				intent.putExtra("username", fri.getSusername());
				startActivityForResult(intent, 0);
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode > 0){
			String username = data.getStringExtra("username");
			int i;
			for(i = 0; i < reqAdapter.getCount(); i++){
				if (reqAdapter.getItem(i).getSusername().equals(username)){
					reqAdapter.removeItem(i);
					break;
				}
			}
		}
	}
}
