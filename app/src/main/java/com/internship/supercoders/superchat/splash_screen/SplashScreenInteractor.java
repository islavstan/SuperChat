package com.internship.supercoders.superchat.splash_screen;


import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;

import rx.Observable;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenInteractor {
    VerificationData getUserInfo();

    void saveToken(String token);

    interface UserAuthorizationFinishedListener {

        void onError();

        void onSuccess(String token);

    }

    Observable<Session> userAuthorization(String email, String password);

    Observable<Session> createSession();

    boolean isAuth();
}
