package com.example.administrator.giftclient.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ha Hoang on 6/22/2017.
 */

public class BootRecieve extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context,CameraService.class);
        context.startService(i);
    }
}
