package com.example.myskety.my_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myskety.my_application.R;
import com.example.myskety.my_application.data.FriendReqItem;

import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/9.
 */
public class FriendReqAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FriendReqItem> datas;

    public FriendReqAdapter(Context context){
        this.context =context;
        datas = new ArrayList<FriendReqItem>();
    }
    public Context getContext(){
        return context;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public FriendReqItem getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(FriendReqItem item){
        datas.add(0,item);
        notifyDataSetChanged();
    }
    public void removeItem(int index){
        datas.remove(index);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView==null){
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.req_list_cell,null);
       }
        TextView titleTV = (TextView) convertView.findViewById(R.id.req_title);
        TextView dateTV =(TextView)convertView.findViewById(R.id.req_date);
        FriendReqItem item = getItem(position);
        titleTV.setText(item.getSnickname());
        dateTV.setText(item.getDate());
        return convertView;
    }
}
