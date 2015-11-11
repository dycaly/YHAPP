package com.example.myskety.my_application.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.activity.Activity_Product;
import com.example.myskety.my_application.adapter.ProductAdapter;
import com.example.myskety.my_application.data.ProductItem;
import com.example.myskety.my_application.net.HttpMethod;
import com.example.myskety.my_application.net.NetConnection;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Myskety on 2015/11/9.
 */
public class Fragment_Sort_Book extends Fragment implements AdapterView.OnItemClickListener {
    private View rootView;
    private String classname = "book";

    private ProductAdapter mAdapter;
    private ListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sort_book, null);


        mAdapter = new ProductAdapter(getActivity());
        mListView = (ListView) rootView.findViewById(R.id.book_lv);
        mListView.setAdapter(mAdapter);


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                initView();

                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable, 0);



        mListView.setOnItemClickListener(this);
        return rootView;
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
                                        hightestprice, lowestprice, cuttime, cutprice, sstatus, sellername, sellernickname,
                                        selldate, buyername, lastprice, classify);


                                int index;
                                for (index=0;index<mAdapter.getCount();index++){
                                    ProductItem it = mAdapter.getItem(index);

                                    if (it.getProductid() == productid){
                                        break;
                                    }
                                }
                                if (index == mAdapter.getCount()){
                                    mAdapter.addItem(pi);
                                }
                            }

                            break;
                        default:
                            Toast.makeText(getActivity(), json.getString("reason"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }, MainConfig.GET_PRODUCT_URL, HttpMethod.POST, "classname", classname);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ProductItem item = mAdapter.getItem(position);
        Gson gson = new Gson();
        String info = gson.toJson(item);
        Intent intent = new Intent(getActivity(), Activity_Product.class);
        intent.putExtra("info", info);
        intent.putExtra("productid", item.getProductid());
        startActivity(intent);

    }
}
