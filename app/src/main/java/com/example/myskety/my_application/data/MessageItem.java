package com.example.myskety.my_application.data;

import com.example.myskety.my_application.MainConfig;

/**
 * Created by Myskety on 2015/11/8.
 */
public class MessageItem {
    private boolean isSaw;
    private boolean other;
    private String picurl;
    private String susername;
    private String snickname;
    private String rusername;
    private String rnickname;
    private String message;
    private String date;

    public MessageItem(boolean isSaw,boolean other, String susername,String snickname,String rusername,String rnickname, String message, String date){

        this.isSaw = isSaw;
        this.other =other;

        this.susername = susername;
        this.picurl = MainConfig.LOADIMAGE_URL+susername+".png";
        this.snickname = snickname;
        this.rusername = rusername;
        this.rnickname = rnickname;
        this.message = message;
        this.date = date;
    }
    public String getPicurl(){
        return picurl;
    }
    public  boolean getIsSaw(){
        return isSaw;
    }
    public  void setSaw(boolean isSaw){
        this.isSaw =isSaw;
    }
    public boolean getOther(){
        return other;
    }

    public String getSUsername(){
        return susername;
    }
    public String getSNickname(){
        return snickname;
    }
    public String getRUsername(){
        return rusername;
    }
    public String getRNickname(){
        return rnickname;
    }
    public String getMessage(){
        return message;
    }
    public String getDate(){
        return date;
    }
}