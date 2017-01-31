package com.internship.supercoders.superchat.splashScreen;

import android.support.annotation.Nullable;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenView {
    // TODO: 1/30/17 [Code Review] why do you need token to navigate to other screen? lol
    void navigateToMainScreen(@Nullable String token);

    // TODO: 1/30/17 [Code Review] the same as above
    void navigateToAuthorScreen(@Nullable String token);

    void finish(); //Presenter call onDestroy method in activity

}
