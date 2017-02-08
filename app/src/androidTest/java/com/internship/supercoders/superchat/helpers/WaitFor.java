package com.internship.supercoders.superchat.helpers;

import android.content.Context;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;

import junit.framework.AssertionFailedError;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Just For AS on 08.02.2017.
 */

public class WaitFor {
    private static int  SMALL_TIMEOUT = 1000;
    private static int  SHORT_CYCLE = 200;
    private static int  MEDIUM_CYCLE = 250;
    private static int  LONG_CYCLE = 500;

    Context context;

    public WaitFor(Context context){
        this.context = context;
    }

    public boolean waitForView(int view, int time) throws InterruptedException {
        int timeToBreak = time;
        int timeOfCycle = SHORT_CYCLE;
        boolean result;

        while(true) {
            try {
                onView(withId(view)).check(matches(isDisplayed()));
                result = true;
                break;
            } catch (NoMatchingViewException | AssertionFailedError |AmbiguousViewMatcherException err) {
                Clock.sleep(timeOfCycle);
                timeToBreak -= timeOfCycle;
                if(timeToBreak <= 0){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public boolean waitForDescriptionIn(String description, int time) throws InterruptedException {
        int timeToBreak = time;
        int timeOfCycle = LONG_CYCLE;
        boolean result;

        while(true) {
            try {
                onView(withContentDescription(description)).check(matches(isDisplayed()));
                result = true;
                break;
            } catch (NoMatchingViewException|AmbiguousViewMatcherException err) {
                Clock.sleep(timeOfCycle);
                timeToBreak -= timeOfCycle;
                if(timeToBreak <= 0){
                    result =false;
                    break;
                }
            }
        }
        return result;
    }

    public boolean waitForString(int string, int time) throws InterruptedException {
        int timeToBreak = time;
        int timeOfCycle = MEDIUM_CYCLE;
        boolean result;

        while(true) {
            try {
                onView(withText(containsString(context.getString(string)))).check(matches(isDisplayed()));
                result = true;
                break;
            } catch (NoMatchingViewException err) {
                Clock.sleep(timeOfCycle);
                timeToBreak -= timeOfCycle;
                if(timeToBreak <= 0){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public boolean waitForText(String string, int time) throws InterruptedException {
        int timeToBreak = time;
        int timeOfCycle = MEDIUM_CYCLE;
        boolean result = true;

        while(true) {
            try {
                onView(withText(containsString(string))).check(matches(isDisplayed()));
                result = true;
                break;
            } catch (Exception err) {
                Clock.sleep(timeOfCycle);
                timeToBreak -= timeOfCycle;
                if(timeToBreak <= 0){
                    result = false;
                    break;
                }
            } catch (Throwable e){
                break;
            }
        }
        return result;
    }

    public boolean waitForTextNotIsDisplayed(String string, int time) throws InterruptedException {

        int timeToBreak = time;
        int timeOfCycle = MEDIUM_CYCLE;
        boolean result;

        while(true) {
            try {
                onView(withText(string)).check(matches(not(isDisplayed())));
                result = true;
                break;
            } catch (Exception err) {
                Clock.sleep(timeOfCycle);
                timeToBreak -= timeOfCycle;
                if(timeToBreak <= 0){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public void waitForOneOfSeveralViews(int view, int view2, int time) throws InterruptedException {
        int timeToBreak = time;
        int timeOfCycle = MEDIUM_CYCLE;
        while(true) {
            try {
                onView(withId(view)).check(matches(isDisplayed()));
                break;
            } catch (NoMatchingViewException err) {
                try {
                    onView(withId(view2)).check(matches(isDisplayed()));
                    break;
                } catch (NoMatchingViewException err2){
                    Clock.sleep(timeOfCycle);
                    timeToBreak -= timeOfCycle;
                    if(timeToBreak <= 0) break;
                }
            }
        }
    }

    public void waitForTextPresent(int id) throws Exception {
        waitForString(id, SMALL_TIMEOUT);
    }
}
