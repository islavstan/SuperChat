package com.internship.supercoders.superchat.splashScreen;

import android.util.Log;

import com.internship.supercoders.superchat.models.user_authorization_response.LogAndPas;

public class SplashScreenPresenterImpl implements SplashScreenPresenter, SplashScreenInteractor.UserAuthorizationFinishedListener {
    private SplashScreenView splashScreenView;
    private SplashScreenInteractor splashScreenInteractor;
    private String token = null;
    private boolean authorize = false;

    SplashScreenPresenterImpl(SplashScreenView view) {
        this.splashScreenView = view;
        this.splashScreenInteractor = new SplashScreenInteractorImpl();
    }

    @Override
    public void unsubscribe() {
        splashScreenView = null;
        splashScreenInteractor = null;
    }

    @Override
    public void sleep(final long milliseconds) {
        authorize = splashScreenView.isAuth();
        Thread sleepThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (authorize) {
                    Log.i("Splash", "ToMainScreen");
                    splashScreenView.navigateToMainScreen(token);
                } else {
                    Log.i("Splash", "ToAuth");
                    splashScreenView.navigateToAuthorScreen();
                }

            }
        };
        sleepThread.start();
        if (authorize) {
            LogAndPas user;
            user = splashScreenView.getLogAndPas();
            Log.d("Splash", "Login: " + user.getEmail() + "Password: " + user.getPassword());
            splashScreenInteractor.userAuthorization(user.getEmail(), user.getPassword(), this);
        }


    }

    @Override
    public void onError() {
        authorize = false;
    }

    @Override
    public void onSuccess(String token) {
        this.token = token;
    }
}
