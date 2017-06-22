package com.example.administrator.giftclient.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.util.Log;

import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.model.Event;
import com.example.administrator.giftclient.model.Message;
import com.example.administrator.giftclient.support.ConnectServer;
import com.example.administrator.giftclient.support.NotificationEvent;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 3/6/2017.
 */

public class GetMessageBroadCast extends BroadcastReceiver {
    HashMap<String, String> hashMap;
    @Override
    public void onReceive(Context context, Intent intent) {
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("data");
//        Log.v("HH", hashMap.toString());
        if (hashMap.get("key").equals("notificaion")) {
            Event event = new Event();
            event.setMessage(hashMap.get("message"));
            event.setImage(hashMap.get("image"));
            event.setMp3(hashMap.get("music"));
            event.setVideo(hashMap.get("video"));
            NotificationEvent notificationEvent = new NotificationEvent(context, event);
            notificationEvent.show();
            Ringtone ringtone=RingtoneManager.getRingtone(context,RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION));
            ringtone.play();
        } else if (hashMap.get("key").equals("image")) {
            Intent i=new Intent("take_image");
            context.sendBroadcast(i);
        } else {
            response(context);
        }
    }

    public void response(Context context) {
        ConnectServer connectServer = new ConnectServer(context, new ConnectServer.ConnectComplete() {
            @Override
            public void response(String response) {

            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("to", hashMap.get("from_server"));
        map.put("key", hashMap.get("key"));

        if (hashMap.get("key").equals("location")) {
            try {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                map.put("lat", String.valueOf(location.getLatitude()));
                map.put("long", String.valueOf(location.getLongitude()));
            } catch (Exception e) {
            }
        }
        connectServer.setPara(map);
        connectServer.setUrl(Config.RESPONSE_SEVER);
        connectServer.connect();
    }
}
