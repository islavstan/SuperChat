package com.internship.supercoders.superchat.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by RON on 21.01.2017.
 */
public class ViewUtils {
    Context context;
    Activity activity;

    public ViewUtils(Activity activity){
        this.activity = activity;
    }

    public void hideKeyboard() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showShackLong(int message) {
        Snackbar.make(activity.getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }

    public void showShackShort(int message) {
        Snackbar.make(activity.getWindow().getDecorView(), message, Snackbar.LENGTH_LONG).show();
    }
}
