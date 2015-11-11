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
import com.example.myskety.my_application.data.FriendItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/8.
 */
public class FriendAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FriendItem> datas;
    private ImageView imageView;

    public FriendAdapter(Context context){
        this.context = context;
        datas= new ArrayList<FriendItem>();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    public void addItem(FriendItem item){
        datas.add(item);
        notifyDataSetChanged();
    }

    @Override
    public FriendItem getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext(){
        return context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_list_cell,null);
        }
        FriendItem item = datas.get(position);
        imageView = (ImageView)convertView.findViewById(R.id.friend_pic);
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().loadImage(item.getPicurl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_head);
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
        TextView nicknameTV = (TextView)convertView.findViewById(R.id.friend_nickname);
        nicknameTV.setText(item.getNickname());
        return convertView;
    }
}
