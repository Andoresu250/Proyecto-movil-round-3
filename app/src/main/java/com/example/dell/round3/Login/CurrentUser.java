package com.example.dell.round3.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class CurrentUser {
    private final String SHARED_PREFS_FILE = "HMPrefs";
    private final String KEY_USER = "Users";
    private final String KEY_TYPE = "Nothing";

    private Context mContext;


    public CurrentUser(Context context){
        mContext = context;
    }

    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public String getUser(){
        return getSettings().getString(KEY_USER, null);
    }

    public String getType(){
        return getSettings().getString(KEY_TYPE, null);
    }

    public void setUser(String user){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_USER, user);
        editor.commit();
    }

    public void setType(String type){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_TYPE, type);
        editor.commit();
    }
}
