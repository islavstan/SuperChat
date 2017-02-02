package com.internship.supercoders.superchat.splash_screen;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenView {
    void navigateToMainScreen();

    void navigateToAuthorScreen();

    void finish(); //Presenter call onDestroy method in activity

}
