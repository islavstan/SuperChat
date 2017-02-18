package com.internship.supercoders.superchat;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.internship.supercoders.superchat.db.DBMethods;

import io.fabric.sdk.android.Fabric;

/**
 * Created by RON on 22.01.2017.
 */
public class App extends Application {
    private static DBMethods dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        dbManager = new DBMethods(this);

    }

    public static DBMethods getDataBaseManager() {
        return dbManager;
    }
}
