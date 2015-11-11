package com.example.myskety.my_application;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Myskety on 2015/11/4.
 */
public  class  MyApplication  extends Application {
    private int f=0;
    private String token="";
    private SharedPreferences sp;
    public int getMybool() {
        return f;
    }
    public void setMybool(int a) {
        this.f =a;
    }
    public SharedPreferences getToken(){
        return sp;
    }
    public void setToken(SharedPreferences s){
        this.sp=s;
    }
    public void init(){
        this.sp=getSharedPreferences("user",MODE_PRIVATE);
    }
}