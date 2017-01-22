package com.internship.supercoders.superchat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.internship.supercoders.superchat.data.AppConsts;

/**
 * Created by RON on 22.01.2017.
 */
public class UserPreferences {
    Context context;
    SharedPreferences mySharedPreferences;

    public  UserPreferences(Context context){
        this.context = context;
        mySharedPreferences = context.getSharedPreferences(AppConsts.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void kepUserSignedIn(Boolean aBoolean){
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(AppConsts.SIGNED_IN_PREFERENCES, aBoolean );
        editor.apply();
    }

    // for SplashScreen
    public boolean isUserSignedIn(){
        return mySharedPreferences.getBoolean(AppConsts.SIGNED_IN_PREFERENCES, false);
    }
}
