package com.internship.supercoders.superchat.splashScreen;

import android.content.Context;
import android.support.annotation.Nullable;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenView {
    void navigateToMainScreen(@Nullable String token);

    void navigateToAuthorScreen(@Nullable String token);

    boolean isAuth();

    LogAndPas getLogAndPas();

    Context getContext();

    void finish();
}
