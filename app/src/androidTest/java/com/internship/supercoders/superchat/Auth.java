package com.internship.supercoders.superchat;

import android.support.test.runner.AndroidJUnit4;

import com.internship.supercoders.superchat.helpers.Runner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class Auth extends Runner {

    @Test
    public void signInSuccess() throws InterruptedException {
        waitFor.waitForView(R.id.input_layout_email, 4000);

        onView(withId(R.id.input_email)).perform(click(), replaceText("roman.karnaukh@gmail.com"));
        onView(withId(R.id.input_password)).perform(click(), replaceText("qwerty"), closeSoftKeyboard());

//        onView(withId(R.id.btn_sign_in)).perform(click());
    }


}
