package com.example.myskety.my_application.fragment;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.View.ImageCycleView;
import com.example.myskety.my_application.activity.Activity_Product;
import com.example.myskety.my_application.adapter.ProductAdapter;
import com.example.myskety.my_application.data.ImageItem;
import com.example.myskety.my_application.data.ProductItem;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Home extends Fragment implements AdapterView.OnItemClickListener {

	View view;


	private ImageCycleView mICView;
	private ArrayList<ImageItem> mItemList;

	private ListView lv;

	private ProductAdapter mAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_home, null);

		lv = (ListView)view.findViewById(R.id.home_lv);
		mAdapter = new ProductAdapter(getActivity());
		lv.setAdapter(mAdapter);

		initImageCycle();


		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				initView();

				handler.postDelayed(this, 10000);
			}
		};
		handler.postDelayed(runnable, 0);
		lv.setOnItemClickListener(this);
		return view;
	}

	private void initView() {
		new NetConnection(new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {

				try {
					JSONObject json = new JSONObject(result);
					int status = json.getInt("status");

					switch (status) {
						case 0:
							JSONArray items = json.getJSONArray("productInfos");


							mAdapter.clear();
							for (int i = 0; i < items.length(); i++) {
								JSONObject item = items.getJSONObject(i);
								int productid = item.getInt("productid");
								String producturl = item.getString("producturl");
								String productname = item.getString("productname");
								String productintro = item.getString("productintro");
								int hightestprice = item.getInt("hightestprice");
								int lowestprice = item.getInt("lowestprice");
								int cuttime = item.getInt("cuttime");
								int cutprice = item.getInt("cutprice");
								int sstatus = item.getInt("status");
								String sellername = item.getString("sellername");
								String sellernickname = item.getString("sellernickname");
								String selldate = item.getString("selldate");
								String buyername = item.getString("buyername");
								int lastprice = item.getInt("lastprice");
								String classify = item.getString("classify");

								ProductItem pi = new ProductItem(productid, producturl, productname, productintro,
										hightestprice, lowestprice, cuttime,cutprice, sstatus, sellername, sellernickname,
										selldate, buyername, lastprice, classify);
								mAdapter.addItem(0,pi);
							}

							break;
						default:
							Toast.makeText(getActivity(), json.getString("reason"), Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
				}

			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail(String result) {
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			}
		}, MainConfig.GET_ALLPRODUCT_URL, HttpMethod.POST);
	}

	private void initImageCycle() {

		mItemList = new ArrayList<ImageItem>();
		mItemList.add(new ImageItem("0", MainConfig.LOADIMAGE_URL+"p1.png"));
		mItemList.add(new ImageItem("1", MainConfig.LOADIMAGE_URL+"p2.png"));
		mItemList.add(new ImageItem("2", MainConfig.LOADIMAGE_URL+"p3.png"));
		mItemList.add(new ImageItem("3", MainConfig.LOADIMAGE_URL+"p4.png"));

		mICView = (ImageCycleView) view.findViewById(R.id.id_home_image_cycle);
		mICView.setImageResources(mItemList, new ImageCycleView.ImageCycleViewListener() {

			public void onImageClick(ImageItem info, int postion, View imageView) {
				Toast.makeText(getActivity(), "content->"+info.getName(), Toast.LENGTH_SHORT).show();

			}

			public void displayImage(String imageURL, ImageView imageView) {
				ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！

			}
		});
	}
	@Override
	public void onResume() {
		super.onResume();
		mICView.startImageCycle();
	};

	@Override
	public void onPause() {
		super.onPause();
		mICView.pushImageCycle();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mICView.pushImageCycle();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		ProductItem item = mAdapter.getItem(position);
		Gson gson = new Gson();
		String info = gson.toJson(item);
		Intent intent = new Intent(getActivity(), Activity_Product.class);
		intent.putExtra("info",info);
		intent.putExtra("productid",item.getProductid());
		startActivity(intent);
	}
}
