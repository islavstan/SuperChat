package com.internship.supercoders.superchat.splash_screen;


import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;

import rx.Observable;

/**
 * Created by Max on 17.01.2017.
 */

public interface SplashScreenInteractor {
    VerificationData getUserInfo();

    void saveToken(String token);

    String getToken();

    interface UserAuthorizationFinishedListener {

        void onError();

        void onSuccess(String token);

    }

    Observable<Session> userAuthorization(String email, String password);

    Observable<Session> createSession();

    Observable<UpdateUser> signIn(String token);

    boolean isAuth();
}
