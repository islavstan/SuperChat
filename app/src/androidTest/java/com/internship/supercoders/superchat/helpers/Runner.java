package com.internship.supercoders.superchat.helpers;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.Toast;

import com.internship.supercoders.superchat.helpers.WaitFor;
import com.internship.supercoders.superchat.splash_screen.SplashScreenActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

/**
 * Created by Just For AS on 08.02.2017.
 */

public class Runner {
    Context mContext;
    WaitFor waitFor;

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(SplashScreenActivity.class);

    @Before
    public void precondition() {
        mContext = InstrumentationRegistry.getContext();
        waitFor = new WaitFor(mContext);
    }

    @After
    public void testExecuted() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivityRule.getActivity(), "Test Executed", Toast.LENGTH_LONG).show();
            }
        });
    }

}
