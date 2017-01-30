package com.internship.supercoders.superchat.splashScreen;

import android.content.Context;
import android.support.annotation.Nullable;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenView {
    // TODO: 1/30/17 [Code Review] why do you need token to navigate to other screen? lol
    void navigateToMainScreen(@Nullable String token);

    // TODO: 1/30/17 [Code Review] the same as above
    void navigateToAuthorScreen(@Nullable String token);

    // TODO: 1/30/17 [Code Review] business logic, move to interactor layer
    boolean isAuth();

    // TODO: 1/30/17 [Code Review] business logic, move to interactor layer
    LogAndPas getLogAndPas();

    // TODO: 1/30/17 [Code Review] prevent using this
    Context getContext();

    // TODO: 1/30/17 [Code Review] leave some comment what this method is used for
    void finish();

    void fadeIn();
}
