package com.example.myskety.my_application.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myskety.my_application.R;
import com.example.myskety.my_application.data.ProductItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/8.
 */
public class ProductAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<ProductItem> datas;

    public ProductAdapter(Context context){
        this.context = context;
        this.datas = new ArrayList<ProductItem>();
    }

    public int getCount(){
        return datas.size();
    }

    public ProductItem getItem(int position) {
        return datas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public Context getContext() {
        return context;
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }

    public void addItem(ProductItem item){
        datas.add(item);
        notifyDataSetChanged();
    }
    public void addItem(int index,ProductItem item){
        datas.add(index,item);
        notifyDataSetChanged();
    }
    public void removeItem(int index){
        datas.remove(index);
        notifyDataSetChanged();
    }
    public View getView(int position,View convertView,ViewGroup parent){

        if(convertView ==null){
            convertView =LayoutInflater.from(getContext()).inflate(R.layout.product_list_cell,null);
        }
        ProductItem item = datas.get(position);
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.product_pic);

        String picUrl = item.getProducturl();
        ImageLoader.getInstance().loadImage(picUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.product);
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
        TextView nameTV = (TextView)convertView.findViewById(R.id.product_name);
        TextView contentTV = (TextView)convertView.findViewById(R.id.product_content);
        TextView priceTV=(TextView)convertView.findViewById(R.id.product_price);
        nameTV.setText(item.getProductname());
        contentTV.setText(item.getProductintro());
        priceTV.setText("￥"+item.getLastprice()+".00");
        return convertView;

    }

}
