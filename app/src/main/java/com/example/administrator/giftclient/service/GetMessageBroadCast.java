package com.example.administrator.giftclient.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.model.Message;
import com.example.administrator.giftclient.support.ConnectServer;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 3/6/2017.
 */

public class GetMessageBroadCast extends BroadcastReceiver {
    Message message;
    HashMap<String,String> hashMap;
    @Override
    public void onReceive(Context context, Intent intent) {
        hashMap= (HashMap<String, String>) intent.getSerializableExtra("data");
        if(hashMap.get("key").equals("is_online"))
        {
            response(context);
            Log.v("HHC","isonline");
        }
    }

    public void response(Context context)
    {
        ConnectServer connectServer=new ConnectServer(context, new ConnectServer.ConnectComplete() {
            @Override
            public void response(String response) {

            }
        });
        HashMap<String,String> map=new HashMap<>();
        map.put("to",hashMap.get("from_server"));
        map.put("is_online","true");
        connectServer.setPara(map);
        connectServer.setUrl(Config.RESPONSE_SEVER);
        connectServer.connect();
    }
}
