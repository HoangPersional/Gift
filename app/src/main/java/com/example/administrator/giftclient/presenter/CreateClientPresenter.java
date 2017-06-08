package com.example.administrator.giftclient.presenter;

import com.example.administrator.giftclient.model.PushClient;
import com.example.administrator.giftclient.support.ConnectServer;
import com.example.administrator.giftclient.view.CreateClientInterface;

/**
 * Created by Administrator on 30/5/2017.
 */

public class CreateClientPresenter implements ConnectServer.ConnectComplete {
    private CreateClientInterface view;
    private PushClient pushClient;
    public CreateClientPresenter(CreateClientInterface view,PushClient client)
    {
        this.view=view;
        this.pushClient=client;
        pushClient.setConnectComplete(this);
    }
    public void connect()
    {
        pushClient.connect();
    }
    @Override
    public void response(String response) {
        view.response(response);
    }
}
