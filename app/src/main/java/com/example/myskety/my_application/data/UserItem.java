package com.example.myskety.my_application.data;

import com.example.myskety.my_application.MainConfig;

/**
 * Created by Myskety on 2015/11/8.
 */
public class UserItem {
    private boolean issaw;
    private String url;
    private String username;
    private String nickname;
    private String message;
    private String date;

    public UserItem(boolean issaw, String username,String nickname, String message, String date){
        this.issaw =issaw;
        this.url = MainConfig.LOADIMAGE_URL+username+".png";
        this.username = username;
        this.nickname = nickname;
        this.message = message;
        this.date = date;
    }
    public  boolean getIssaw(){
        return issaw;
    }
    public String getUrl(){
        return url;
    }

    public void setIssaw(boolean is){
        this.issaw =is;
    }
    public String getUsername(){
        return username;
    }
    public String getNickname(){
        return nickname;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String msg){
        this.message=msg;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date =date;
    }
}
