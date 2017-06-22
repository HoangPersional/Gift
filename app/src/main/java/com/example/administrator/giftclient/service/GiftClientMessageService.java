package com.example.administrator.giftclient.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.giftclient.model.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 30/5/2017.
 */

public class GiftClientMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        Gson gson = new Gson();
//        Message message = gson.fromJson(remoteMessage.getData().toString(), Message.class);
//        Log.v("HH",remoteMessage.getData().toString());
        Map<String,String> map=remoteMessage.getData();
        HashMap<String,String> hashMap=new HashMap<>(map);
        Intent intent = new Intent();
        intent.setAction("push_message");
        intent.putExtra("data",hashMap);
        sendBroadcast(intent);
    }

}
