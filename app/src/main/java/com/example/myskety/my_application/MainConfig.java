package com.example.myskety.my_application;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.myskety.my_application.data.MessageItem;

import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/3.
 */
public class MainConfig {
    public static String URL="http://121.42.30.74:8080/TLMSever/";
    public static String LOGIN_URL=URL+"Login";
    public static String REGIST_URL=URL+"Regist";
    public static String GET_INFORMATION_URL=URL+"GetUserDigitals";
    public static String GET_INFORMATIONBYNAME_URL=URL+"GetUserDitalsByName";
    public static String SET_INFROMATION_URL = URL+"SetUserDitals";
    public static String SEND_MESSAGE_URL = URL+"SendMessage";
    public static String GET_MESSAGE_URL = URL+"GetMessages";
    public static String GET_FRIEND_URL = URL+"GetFriends";
    public static String GET_FRIENDREQ_URL = URL+"GetFriendReq";
    public static String OP_FRIENDREQ_URL = URL+"OperateAddFriend";
    public static String REQADDFRIEND_URL = URL+"RequestAddFriend";
    public static String DELETEFRIEND_URL = URL+"DeleteFriend";
    public static String GET_PRODUCT_URL = URL+"GetProduct";
    public static String GET_ALLPRODUCT_URL = URL+"GetAllProduct";
    public static String GET_PRODUCTBYID_URL = URL+"GetProductById";
    public static String BUY_PRODUCT_URL = URL+"BuyProduct";
    public static String ADD_PRODUCT_URL = URL+"AddProduct";
    public static String IMAGE_URL = URL+"Image";
    public static String LOADIMAGE_URL= URL+"LoadImage?name=";
    public static String TOKEN=null;
    public static String gettoken(SharedPreferences sp){
        return sp.getString("token","none");

    }

    public static String picurl = LOADIMAGE_URL+"default.png";
    public static String username="";
}
