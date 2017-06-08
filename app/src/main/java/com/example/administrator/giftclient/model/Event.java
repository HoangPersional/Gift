package com.example.administrator.surprisegiftserver.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Administrator on 23/5/2017.
 */

public class Event   implements Parcelable  {
    @SerializedName("id")
    private int id;
    @SerializedName("sName")
    private String name;
    @SerializedName("iIdUserName")
    private int idUser;
    @SerializedName("iIdClient")
    private int idClient;
    @SerializedName("iDate")
    private int date;
    @SerializedName("iMonth")
    private int month;
    @SerializedName("iYear")
    private int year;
    @SerializedName("iHour")
    private int hour;
    @SerializedName("iMinute")
    private int minute;
    @SerializedName("bRepeat")
    private boolean isRepeat;
    @SerializedName("iType")
    private int iType;
    @SerializedName("iStatus")
    private int iStatus;
    @SerializedName("dCreate")
    private String dCreate;
    @SerializedName("sDescription")
    private String description;

    private boolean isClient;

    @SerializedName("iNotificationMe")
    private boolean isNotification;
    @SerializedName("sText")
    private String message;
    @SerializedName("sImage")
    private String image;
    @SerializedName("sMp3")
    private String mp3;
    @SerializedName("sYouTube")
    private String video;
    @SerializedName("iNotificationBeforeMinute")
    private int iNotificationBeforeMinute;

    protected Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        idUser = in.readInt();
        idClient = in.readInt();
        date = in.readInt();
        month = in.readInt();
        year = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        isRepeat = in.readByte() != 0;
        iType = in.readInt();
        iStatus = in.readInt();
        dCreate = in.readString();
        description = in.readString();
        isClient = in.readByte() != 0;
        isNotification = in.readByte() != 0;
        message = in.readString();
        image = in.readString();
        mp3 = in.readString();
        video = in.readString();
        iNotificationBeforeMinute=in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(idUser);
        dest.writeInt(idClient);
        dest.writeInt(date);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeByte((byte) (isRepeat ? 1 : 0));
        dest.writeInt(iType);
        dest.writeInt(iStatus);
        dest.writeString(dCreate);
        dest.writeString(description);
        dest.writeByte((byte) (isClient ? 1 : 0));
        dest.writeByte((byte) (isNotification ? 1 : 0));
        dest.writeString(message);
        dest.writeString(image);
        dest.writeString(mp3);
        dest.writeString(video);
        dest.writeInt(iNotificationBeforeMinute);
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public int getiType() {
        return iType;
    }

    public void setiType(int iType) {
        this.iType = iType;
    }

    public int getiStatus() {
        return iStatus;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public String getdCreate() {
        return dCreate;
    }

    public void setdCreate(String dCreate) {
        this.dCreate = dCreate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getiNotificationBeforeMinute() {
        return iNotificationBeforeMinute;
    }

    public void setiNotificationBeforeMinute(int iNotificationBeforeMinute) {
        this.iNotificationBeforeMinute = iNotificationBeforeMinute;
    }

    public String toString()
    {
        return name+" "+isClient+" "+iType+" "+dCreate+" "+iNotificationBeforeMinute;
    }
}
