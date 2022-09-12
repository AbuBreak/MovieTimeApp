package com.malikproject.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Register implements Parcelable {
    private String Username;
    private String Email;
    private String Password;

    public Register(String username, String email, String password) {
        Username = username;
        Email = email;
        Password = password;

    }
    public Register() {
    }

    protected Register(Parcel in) {
        Username = in.readString();
        Email = in.readString();
        Password = in.readString();
    }
    public static final Creator<Register> CREATOR = new Creator<Register>() {
        @Override
        public Register createFromParcel(Parcel in) {
            return new Register(in);
        }

        @Override
        public Register[] newArray(int size) {
            return new Register[size];
        }
    };
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(Username);
        parcel.writeString(Email);
        parcel.writeString(Password);

    }
}
