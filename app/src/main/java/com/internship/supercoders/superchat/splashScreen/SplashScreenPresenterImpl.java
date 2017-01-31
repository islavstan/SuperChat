package com.internship.supercoders.superchat.splashScreen;

import android.os.Handler;
import android.util.Log;

import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.utils.UserPreferences;

public class SplashScreenPresenterImpl implements SplashScreenPresenter, SplashScreenInteractor.UserAuthorizationFinishedListener {
    private SplashScreenView splashScreenView;
    private SplashScreenInteractor splashScreenInteractor;
    private volatile String token = null;
    private boolean isAuthorize = false;

    SplashScreenPresenterImpl(SplashScreenView view, DBMethods dbManager, UserPreferences userPreferences) {
        this.splashScreenView = view;
        this.splashScreenInteractor = new SplashScreenInteractorImpl(dbManager, userPreferences);
    }

    @Override
    public void unsubscribe() {
        splashScreenView = null;
        splashScreenInteractor = null;
    }

    @Override
    public void sleep(final long milliseconds) {
        Thread sleepThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Handler mainHandler = splashScreenView.createUiHandler();
                mainHandler.post(() -> splashScreenView.fadeIn());
                if (isAuthorize) {
                    Log.i("Splash", "ToMainScreen");
                    splashScreenView.navigateToMainScreen(token);
                } else {
                    Log.i("Splash", "ToAuth");
                    splashScreenView.navigateToAuthorScreen(token);
                }
                Log.i("Splash Presenter", "Call finish");
                splashScreenView.finish();
            }
        };
        sleepThread.start();
        isAuthorize = splashScreenInteractor.isAuth();
        if (isAuthorize) {
            VerificationData user;
            user = splashScreenInteractor.getUserInfo();
            Log.d("Splash", "Login: " + user.getEmail() + "Password: " + user.getPassword());
            splashScreenInteractor.userAuthorization(user.getEmail(), user.getPassword(), this);
        } else {
            splashScreenInteractor.authorization(this);
        }
    }

    @Override
    public void onError() {
        isAuthorize = false;
        Log.i("Splash", "Error");
    }

    @Override
    public void onSuccess(String token) {
        this.token = token;
        Log.i("Splash", "token: " + token);
    }
}
