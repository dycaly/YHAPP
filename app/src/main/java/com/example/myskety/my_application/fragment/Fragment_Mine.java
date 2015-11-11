package com.example.myskety.my_application.fragment;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.View.CircleImageView;
import com.example.myskety.my_application.activity.InformationActivity;
import com.example.myskety.my_application.activity.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class Fragment_Mine extends Fragment {

	private CircleImageView myView;
	private TextView mTextView;
	private LinearLayout releaseLayout;
	private LinearLayout parchaseLayout;
	private LinearLayout settingLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mine, null);

		//头像
		myView = (CircleImageView)rootView.findViewById(R.id.header);
		myView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (MainConfig.TOKEN != null && !MainConfig.TOKEN.equals("none")){
					Intent intent = new Intent(getActivity(), InformationActivity.class);
					startActivityForResult(intent, 0);
				}
				else{

					Intent intent = new Intent(getActivity(), MainActivity.class);
					startActivityForResult(intent, 0);
				}


			}
		});
		mTextView = (TextView)rootView.findViewById(R.id.nickname);

		//发布
		releaseLayout = (LinearLayout)rootView.findViewById(R.id.release);
		releaseLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		//购买
		parchaseLayout = (LinearLayout)rootView.findViewById(R.id.parchase);
		parchaseLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		//设置
		settingLayout = (LinearLayout)rootView.findViewById(R.id.setting);
		settingLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences sp=getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
				SharedPreferences.Editor  ed = sp.edit();
				ed.clear();
				ed.commit();
				MainConfig.TOKEN = null;

				initUser();
				Intent intent = new Intent(getActivity(),MainActivity.class);
				startActivity(intent);
			}
		});
		initUser();

		return rootView;
	}

	public void initUser(){

		if (MainConfig.TOKEN != null && !MainConfig.TOKEN.equals("none")){

			SharedPreferences sp=getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
			String nickname = sp.getString("nickname","none");
			mTextView.setText(nickname);
			ImageLoader.getInstance().clearMemoryCache();
			ImageLoader.getInstance().clearDiscCache();
			ImageLoader.getInstance().loadImage(MainConfig.picurl, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String s, View view) {

				}

				@Override
				public void onLoadingFailed(String s, View view, FailReason failReason) {
					Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_head);
					myView.setImageBitmap(bm);
				}

				@Override
				public void onLoadingComplete(String s, View view, Bitmap bitmap) {
					myView.setImageBitmap(bitmap);
				}

				@Override
				public void onLoadingCancelled(String s, View view) {

				}
			});
		}
		else{
			mTextView.setText("未登录");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		initUser();
	}
}
