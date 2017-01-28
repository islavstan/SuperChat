package com.internship.supercoders.superchat.splashScreen;

import android.content.Context;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenView {
    void navigateToMainScreen(String token);

    void navigateToAuthorScreen();

    boolean isAuth();

    LogAndPas getLogAndPas();

    Context getContext();
}
