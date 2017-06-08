package com.example.administrator.giftclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Administrator on 3/6/2017.
 */

public class Data {
    @SerializedName("key")
    public int key;
    @SerializedName("list")
    public String list;
    @SerializedName("from_server")
    public String server;

    public Data() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getLink() {
        return list;
    }

    public void setLink(String link) {
        this.list = link;
    }

    public String toString() {
        return key + " " + list;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
