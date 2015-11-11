package com.example.myskety.my_application.tools;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.myskety.my_application.data.MessageItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/8.
 */
public class UserMessageDir {
    private Context context;
    private String myusername;
    private File dirFile;
    public UserMessageDir(Context context,String myusername){
        this.context =context;
        this.myusername = myusername;
        File sd=Environment.getExternalStorageDirectory();
        String path=sd.getAbsolutePath()+"/Message/"+myusername;
        dirFile=new File(path);
        if(!dirFile.exists())
            dirFile.mkdirs();
    }
    public ArrayList<String> getFriendsName(){
        ArrayList<String> friendsname = new ArrayList<String>();
        File files[] = dirFile.listFiles();
        if(files != null){
            for (File f : files){
                if(!f.isDirectory()&& !f.getName().equals(myusername+".xt")){
                   String filename = f.getName();
                   friendsname.add(filename.substring(0,filename.indexOf(".")));
                }
            }
        }
        return friendsname;
    }
}
