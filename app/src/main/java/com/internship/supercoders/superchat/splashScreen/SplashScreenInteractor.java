package com.internship.supercoders.superchat.splashScreen;


import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenInteractor {
    VerificationData getUserInfo();

    interface UserAuthorizationFinishedListener {

        void onError();

        void onSuccess(String token);

    }

    void userAuthorization(String email, String password, UserAuthorizationFinishedListener listener);

    void authorization(UserAuthorizationFinishedListener authorizationListener);

    boolean isAuth();
}
