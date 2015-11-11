package com.example.myskety.my_application.data;

import com.example.myskety.my_application.MainConfig;

/**
 * Created by Myskety on 2015/11/8.
 */
public class FriendItem {

    private String picurl;
    private String username;
    private String nickname;


    public FriendItem(String username,String nickname){
        this.picurl = MainConfig.LOADIMAGE_URL+username+".png";
        this.username=username;
        this.nickname =nickname;

    }

    public String getPicurl(){
        return picurl;
    }
    public String getUsername(){
        return username;
    }
    public String getNickname(){
        return nickname;
    }

}
