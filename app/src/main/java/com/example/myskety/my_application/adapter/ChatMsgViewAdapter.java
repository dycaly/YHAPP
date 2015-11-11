package com.example.myskety.my_application.adapter;

/**
 * Created by Myskety on 2015/11/4.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myskety.my_application.R;
import com.example.myskety.my_application.data.MessageItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 * 消息ListView的Adapter
 *
 *
 */
public class ChatMsgViewAdapter extends BaseAdapter {

    private Context context;
    private List<MessageItem> coll;// 消息对象数组

    private ImageView imageView;
    public ChatMsgViewAdapter(Context context) {
        this.context=context;
        this.coll = new ArrayList<MessageItem>();

    }

    public Context getContext(){
        return context;
    }
    public int getCount() {
        return coll.size();
    }

    public MessageItem getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void addItem(MessageItem mo){
        coll.add(mo);
        notifyDataSetChanged();
    }
    public void addItem(int index,MessageItem mo){
        coll.add(index,mo);
        notifyDataSetChanged();
    }



    public View getView(int position, View convertView, ViewGroup parent) {

       MessageItem entity = coll.get(position);
        if (entity.getOther()) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.chatting_item_msg_text_left, null);
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.chatting_item_msg_text_right, null);
        }
        ViewHolder viewHolder = new ViewHolder();

        imageView = (ImageView)convertView.findViewById(R.id.iv_userhead);
        MessageItem item = getItem(position);
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

        viewHolder.tvSendTime = (TextView) convertView
                .findViewById(R.id.tv_sendtime);
        viewHolder.tvUserName = (TextView) convertView
                .findViewById(R.id.tv_username);
        viewHolder.tvContent = (TextView) convertView
                .findViewById(R.id.tv_chatcontent);
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getSNickname());
        viewHolder.tvContent.setText(entity.getMessage());
        return convertView;
    }

    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
    }

}
