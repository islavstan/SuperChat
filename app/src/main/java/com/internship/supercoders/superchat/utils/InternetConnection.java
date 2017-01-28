package com.internship.supercoders.superchat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Max on 28.01.2017.
 */

public class InternetConnection {
    public static boolean hasConnection(final Context context) {
        ConnectivityManager connectManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

}
