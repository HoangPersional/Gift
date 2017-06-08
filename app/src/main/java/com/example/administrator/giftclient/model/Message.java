package com.example.administrator.giftclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 3/6/2017.
 */

public class Message implements Serializable{
    @SerializedName("to")
    public String to;
    @SerializedName("data")
    ArrayList<Data> data;

    public Message() {
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String toString() {
        return   " " + to + " " + data.get(0).toString();
    }
}
