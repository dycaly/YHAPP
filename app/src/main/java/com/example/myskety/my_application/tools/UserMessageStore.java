package com.example.myskety.my_application.tools;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.myskety.my_application.data.MessageItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Myskety on 2015/11/8.
 */
public class UserMessageStore {
    private Context context;
    private String username;
    private String dirPath;
    private String filePath;
    private File userfile;

    public UserMessageStore(Context context, String username,String myusername){
        this.context =context;
        this.username = username;
        File sd=Environment.getExternalStorageDirectory();
        String path=sd.getAbsolutePath()+"/Message/"+myusername;
        File file=new File(path);
        if(!file.exists())
            file.mkdirs();
        dirPath = file.getAbsolutePath();
        userfile=new File(dirPath+"/"+username+".xt");
        if(!userfile.exists()){
            try{
                userfile.createNewFile();
            }
            catch (Exception e){
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void addMessage(MessageItem item){
        Gson gson = new Gson();
        String result = gson.toJson(item)+"\n";
        try {
            OutputStream output = new FileOutputStream(userfile,true);
            output.write(result.getBytes());
            output.flush();
            output.close();
        }
        catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void setSawMessage(ArrayList<MessageItem> messages){
        try {
            OutputStream output = new FileOutputStream(userfile);
            output.write("".getBytes());
            output.flush();
            output.close();
            for(MessageItem item:messages){
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
    public ArrayList<MessageItem> getMessages(){
        ArrayList<MessageItem> messages = new ArrayList<MessageItem>();
        try {
            FileReader fr=new FileReader(userfile);
            BufferedReader br=new BufferedReader(fr);
            String temp = null;
            while((temp=br.readLine())!=null){
                if (temp.isEmpty()){
                    continue;
                }
                JSONObject json=new JSONObject(temp);
                boolean issaw = json.getBoolean("isSaw");
                boolean other = json.getBoolean("other");
                String susername = json.getString("susername");
                String snickname = json.getString("snickname");
                String rusername = json.getString("rusername");
                String rnickname = json.getString("rnickname");
                String message = json.getString("message");
                String date = json.getString("date");
                messages.add(new MessageItem(issaw,other,susername,snickname,rusername,rnickname,message,date));
            }
            fr.close();
        }
        catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return messages;
    }
}
