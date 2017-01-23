package com.internship.supercoders.superchat.splashScreen;

import android.content.Context;

import rx.Observable;

public class SplashScreenPresenterImpl implements SplashScreenPresenter, SplashScreenInteractor.UserAuthorizationFinishedListener {
    Context context;
    private SplashScreenView splashScreenView;
    private SplashScreenInteractor splashScreenInteractor;
    private String token = null;

    SplashScreenPresenterImpl(SplashScreenView view) {
        this.splashScreenView = view;
        splashScreenInteractor = new SplashScreenInteractorImpl();
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
                splashScreenView.navigateToMainScreen(token);
            }
        };
        sleepThread.start();
        splashScreenInteractor.userAuthorization("max@g.com", "testtest", this);


    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(String token) {
        this.token = token;
    }
}
