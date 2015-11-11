package com.example.myskety.my_application.data;

/**
 * Created by Myskety on 2015/11/8.
 */
public class FriendReqItem {
    private boolean issaw;
    private String susername;
    private String snickname;
    private String rusername;
    private String date;

    public FriendReqItem(boolean issaw,String susername,String snickname,String rusername,String date){
        this.issaw =issaw;
        this.snickname =snickname;
        this.susername =susername;
        this.rusername =rusername;
        this.date =date;
    }
    public boolean getIssaw(){
        return issaw;
    }
    public String getSusername(){
        return susername;
    }
    public String getSnickname(){
        return snickname;
    }
    public String getRusername(){
        return rusername;
    }
    public String getDate(){
        return date;
    }
}
