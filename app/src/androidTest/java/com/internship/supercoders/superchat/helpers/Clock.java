package com.internship.supercoders.superchat.helpers;

import android.annotation.TargetApi;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by Just For AS on 08.02.2017.
 */

@TargetApi(3)
public class Clock {



    public static void sleep(long millis){
        onView(isRoot()).perform(sleep_(millis));
//        SystemClock.sleep(millis);
    }

    public static ViewAction sleep_(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for at least " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;

                while (System.currentTimeMillis() < endTime);
            }
        };
    }
}