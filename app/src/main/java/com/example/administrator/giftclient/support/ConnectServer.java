package com.example.administrator.giftclient.support;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 12/5/2016.
 */

public class ConnectServer {
    private  RequestQueue mRequestQueue;
    private  String mUrl;
    private  HashMap<String, String> mMap;
    public static int GET= Request.Method.GET;
    public static int POST= Request.Method.POST;
    private ConnectComplete mConnectComplete;
    public ConnectServer(Context context, ConnectComplete connectComplete) {
        mRequestQueue= Volley.newRequestQueue(context);
        mConnectComplete=connectComplete;

    }
    public void setUrl(String uml)
    {
        mUrl=uml;
    }
    public void setPara(HashMap<String , String> map)
    {
        mMap=map;
    }
    public  void connect()
    {
        StringRequest stringRequest=new StringRequest(POST, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mConnectComplete.response(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mConnectComplete.response(error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mMap;
            }
        };
        mRequestQueue.add(stringRequest);
    }
    public interface ConnectComplete
    {
        public void response(String response);
    }
}
