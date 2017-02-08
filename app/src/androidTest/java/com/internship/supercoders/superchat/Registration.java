package com.internship.supercoders.superchat;

import com.internship.supercoders.superchat.helpers.Clock;
import com.internship.supercoders.superchat.helpers.Runner;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Just For AS on 08.02.2017.
 */

public class Registration extends Runner {

    @Test
    public void filedRegistration() throws InterruptedException {
        waitFor.waitForView(R.id.input_layout_email, 4000);

        onView(withId(R.id.input_email)).perform(closeSoftKeyboard());
        onView(withId(R.id.signup_btn)).perform(click());

        onView(withId(R.id.input_email)).perform(click(), replaceText("roman.karnaukh@gmail.com"));
        onView(withId(R.id.input_password)).perform(click(), replaceText("Qwerty678"), closeSoftKeyboard());
        onView(withId(R.id.input_confirm_password)).perform(click(), replaceText("Qwerty678"), closeSoftKeyboard());
        onView(withId(R.id.input_fullname)).perform(scrollTo(), click(), replaceText("Full Name"), closeSoftKeyboard());
        onView(withId(R.id.input_phone)).perform(scrollTo(), click(), replaceText("+380952120200"), closeSoftKeyboard());
        onView(withId(R.id.input_website)).perform(scrollTo(), click(), replaceText("automation-qa.herokuapp.com"), closeSoftKeyboard());

        onView(withId(R.id.signup_btn)).perform(scrollTo(), click());

        Clock.sleep(6000);
    }
}
