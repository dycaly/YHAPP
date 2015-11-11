package com.example.myskety.my_application.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.MainConfig;
import com.example.myskety.my_application.R;
import com.example.myskety.my_application.data.MessageItem;
import com.example.myskety.my_application.data.UserItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/8.
 */
public class MessageAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<UserItem> datas;
    private ImageView imageView;

    public MessageAdapter(Context context){
        this.context = context;
        this.datas = new ArrayList<UserItem>();
    }

    public int getCount(){
        return datas.size();
    }

    public UserItem getItem(int position) {
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

    public void addItem(UserItem item){
        datas.add(item);
        notifyDataSetChanged();
    }
    public void addItem(int index,UserItem item){
        datas.add(index,item);
        notifyDataSetChanged();
    }
    public void removeItem(int index){
        datas.remove(index);
        notifyDataSetChanged();
    }
    public View getView(int position,View convertView,ViewGroup parent){

        if(convertView ==null){
            convertView =LayoutInflater.from(getContext()).inflate(R.layout.chat_list_cell,null);
        }
        UserItem item = datas.get(position);
        imageView = (ImageView)convertView.findViewById(R.id.image);
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().loadImage(item.getUrl(), new ImageLoadingListener() {
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
        TextView sawTextView = (TextView) convertView.findViewById(R.id.saw);
        sawTextView.setVisibility(item.getIssaw()?View.GONE:View.VISIBLE);
        TextView titleTV = (TextView)convertView.findViewById(R.id.title);
        TextView messageTV = (TextView)convertView.findViewById(R.id.message);
        TextView dateTV=(TextView)convertView.findViewById(R.id.date);
        titleTV.setText(item.getNickname());
        messageTV.setText(item.getMessage());
        dateTV.setText(item.getDate());
        return convertView;

    }

}
