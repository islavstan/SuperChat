package com.internship.supercoders.superchat.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Max on 29.01.2017.
 */

public class AnimateElement {
    public static void run(Context context, View animateView, int resourceId) {
        Animation a = AnimationUtils.loadAnimation(context, resourceId);
        a.reset();
        animateView.clearAnimation();
        animateView.startAnimation(a);
    }
}
