package com.example.movietimeapp.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class MyPreference {
    private static MyPreference MyPreference;
    private SharedPreferences sharedPreferences;

    public static MyPreference getInstance(Context context) {
        if (MyPreference == null) {
            MyPreference = new MyPreference(context);
        }
        return MyPreference;
    }

    public MyPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }


    public void saveData(String key, Register register) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(register);
        editor.putString(key, json);
        editor.commit();
    }

    public Register getData(String key) {

        if (sharedPreferences != null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, "");
            Register register = gson.fromJson(json, Register.class);
            if (register == null)
                register = new Register("", "", "");
            return register;
        }
        return new Register("", "", "");

    }


}
