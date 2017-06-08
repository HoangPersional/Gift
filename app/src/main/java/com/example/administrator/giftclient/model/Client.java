package com.example.administrator.giftclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 30/5/2017.
 */

public class Client implements Serializable,Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("iIdUserName")
    int idUser;
    @SerializedName("sName")
    String name;
    @SerializedName("sDescription")
    String description;
    @SerializedName("dCreate")
    String dCreate;
    @SerializedName("sToken")
    String token;
    public Client(){}

    protected Client(Parcel in) {
        id = in.readInt();
        idUser = in.readInt();
        name = in.readString();
        description = in.readString();
        dCreate = in.readString();
        token = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idUser);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(dCreate);
        dest.writeString(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
