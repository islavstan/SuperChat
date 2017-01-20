package com.internship.supercoders.superchat.splashScreen;

import rx.Observable;

public class SplashScreenPresenterImpl implements SplashScreenPresenter {
    private SplashScreenView splashScreenView;
    private SplashScreenInteractor splashScreenInteractor;

    SplashScreenPresenterImpl(SplashScreenView view) {
        splashScreenView = view;
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
                splashScreenView.navigateToMainScreen();
            }
        };
        sleepThread.start();

    }
}
