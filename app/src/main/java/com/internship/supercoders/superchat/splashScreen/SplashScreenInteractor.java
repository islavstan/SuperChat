package com.internship.supercoders.superchat.splashScreen;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenInteractor {
    interface UserAuthorizationFinishedListener {

        void onError();

        void onSuccess(String token);

    }

    void userAuthorization(String email, String password, UserAuthorizationFinishedListener listener);

    void authorization(UserAuthorizationFinishedListener authorizationListener);
}
