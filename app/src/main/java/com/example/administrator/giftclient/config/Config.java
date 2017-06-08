package com.example.administrator.giftclient.config;

/**
 * Created by Administrator on 17/5/2017.
 */

public class Config {
    public static String HOST="http://192.168.1.198/";
    public static String CLIENT="NotificationBirthday/addClient.php";
    public static String IN="NotificationBirthday/Login.php";
    public static String UP="NotificationBirthday/SignUp.php";
    public static String EVENT="NotificationBirthday/Event.php";
    public static String GET_EVENT="NotificationBirthday/Events.php";
    public static String RESPONSE="NotificationBirthday/response.php";
    public static String LOG_IN =HOST+IN;
    public static String SIGN_UP=HOST+UP;
    public static String PUSH_EVENT=HOST+EVENT;
    public static String GET_ALL_EVENT=HOST+GET_EVENT;
    public static String ADD_CLIENT=HOST+CLIENT;
    public static String RESPONSE_SEVER=HOST+RESPONSE;

    public static String PREF="pref_client";
}
