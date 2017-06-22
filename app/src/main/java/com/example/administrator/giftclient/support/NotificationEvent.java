package com.example.administrator.giftclient.support;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.administrator.giftclient.R;
import com.example.administrator.giftclient.model.Event;
import com.example.administrator.giftclient.view.Gift;
import com.example.administrator.giftclient.view.LogInActivity;

/**
 * Created by Administrator on 12/6/2017.
 */

public class NotificationEvent {
    private RemoteViews remoteView;
    private Context mContext;
    private NotificationManager notificationManager;
    private Notification notification;
    private NotificationCompat.Builder builder;
    private Event event;
    private TaskStackBuilder taskStackBuilder;
    public static int id = 0x009988;

    public NotificationEvent(Context context, Event e) {
        mContext = context;
        event = e;
        init();
    }

    public void show() {
        notificationManager.notify(id, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void init() {
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle(mContext.getResources().getString(R.string.new_message));;
        builder.setSmallIcon(R.mipmap.gift)
                .setContentInfo(event.getMessage());
        Intent intent=new Intent(mContext,Gift.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable("event",event);
        intent.putExtra("data",bundle);
        taskStackBuilder=TaskStackBuilder.create(mContext);
        taskStackBuilder.addParentStack(Gift.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notification = builder.build();

    }
}
