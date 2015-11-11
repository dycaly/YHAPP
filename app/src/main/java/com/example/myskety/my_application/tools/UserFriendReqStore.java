package com.example.myskety.my_application.tools;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.myskety.my_application.data.FriendReqItem;
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
public class UserFriendReqStore {
    private Context context;

    private String dirPath;
    private String filePath;
    private File userfile;

    public UserFriendReqStore(Context context, String myusername){
        this.context =context;
        File sd=Environment.getExternalStorageDirectory();
        String path=sd.getAbsolutePath()+"/Message/"+myusername;
        File file=new File(path);
        if(!file.exists())
            file.mkdirs();
        dirPath = file.getAbsolutePath();
        userfile=new File(dirPath+"/"+myusername+".xt");
        if(!userfile.exists()){
            try{
                userfile.createNewFile();
            }
            catch (Exception e){
                Toast.makeText(context,e.toString()+"1",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void addFriendReq(FriendReqItem item){
        Gson gson = new Gson();
        String result = gson.toJson(item)+"\n";
        try {
            OutputStream output = new FileOutputStream(userfile,true);
            output.write(result.getBytes());
            output.flush();
            output.close();
        }
        catch (Exception e){
            Toast.makeText(context,e.toString()+"2",Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<FriendReqItem> getFriendReq(){
        ArrayList<FriendReqItem> req = new ArrayList<FriendReqItem>();
        try {
            FileReader fr=new FileReader(userfile);
            BufferedReader br=new BufferedReader(fr);
            String temp = null;
            while((temp=br.readLine())!=null){
                if (temp.isEmpty()){
                    continue;
                }
                JSONObject json=new JSONObject(temp);
                boolean issaw = json.getBoolean("issaw");
                String susername = json.getString("susername");
                String snickname = json.getString("snickname");
                String rusername = json.getString("rusername");
                String date = json.getString("date");
                req.add(new FriendReqItem(issaw,susername,snickname,rusername,date));
            }
            fr.close();
        }
        catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return req;
    }
    public void setFriendReq(ArrayList<FriendReqItem> reqs){

        try {
            OutputStream output = new FileOutputStream(userfile);
            output.write("".getBytes());
            output.flush();
            output.close();
            for(FriendReqItem item:reqs){
                Gson gson = new Gson();
                String result = gson.toJson(item)+"\n";
                OutputStream ioutput = new FileOutputStream(userfile,true);
                ioutput.write(result.getBytes());
                ioutput.flush();
                ioutput.close();
            }
        }
        catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
