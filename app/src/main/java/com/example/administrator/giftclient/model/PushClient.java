package com.example.administrator.giftclient.model;

import android.content.Context;
import android.util.Log;

import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.support.ConnectServer;

import java.util.HashMap;

/**
 * Created by Administrator on 30/5/2017.
 */

public class PushClient implements ConnectServer.ConnectComplete {
    private Client client;
    private ConnectServer.ConnectComplete connectComplete;
    private ConnectServer connectServer;
    private Context mContext;

    public PushClient(Context context, Client client) {
        this.client = client;
        mContext = context;
        connectServer = new ConnectServer(mContext, this);
        connectServer.setUrl(Config.ADD_CLIENT);
    }

    @Override
    public void response(String response) {
        connectComplete.response(response);
    }

    public void connect() {
        HashMap<String, String> map = new HashMap<>();
        map.put("iIdUserName", String.valueOf(client.getIdUser()));
        map.put("sName", client.getName());
        map.put("sToken", client.getToken());
        map.put("sDescription", client.getDescription());
        connectServer.setPara(map);
        connectServer.connect();
    }

    public void setConnectComplete(ConnectServer.ConnectComplete connectComplete) {
        this.connectComplete = connectComplete;
    }
}
