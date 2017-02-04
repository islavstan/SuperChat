package com.internship.supercoders.superchat.splash_screen;

import android.util.Log;

import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.utils.UserPreferences;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashScreenPresenterImpl implements SplashScreenPresenter, SplashScreenInteractor.UserAuthorizationFinishedListener {
    private SplashScreenView splashScreenView;
    private SplashScreenInteractor splashScreenInteractor;
    private volatile String token = null;
    private boolean isAuthorize = false;
    private Subscription subscriber = null;

    SplashScreenPresenterImpl(SplashScreenView view, DBMethods dbManager, UserPreferences userPreferences) {
        this.splashScreenView = view;
        this.splashScreenInteractor = new SplashScreenInteractorImpl(dbManager, userPreferences);
    }

    @Override
    public void unsubscribe() {
        splashScreenView = null;
        splashScreenInteractor = null;
        if (subscriber != null)
            subscriber.unsubscribe();

    }

    @Override
    public void sleep(final long milliseconds) {
        Thread sleepThread = new Thread() {
            public void run() {
                try {
                    Log.i(AppConsts.SPLASH_TAG, "Start");
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isAuthorize) {
                    Log.i(AppConsts.SPLASH_TAG, "ToMainScreen");
                    splashScreenView.navigateToMainScreen();
                } else {
                    Log.i(AppConsts.SPLASH_TAG, "ToAuth");
                    splashScreenView.navigateToAuthorScreen();
                }
                Log.i(AppConsts.SPLASH_TAG + " Presenter", "Call finish");
                splashScreenView.finish();
            }
        };
        sleepThread.start();
        isAuthorize = splashScreenInteractor.isAuth();
        if (isAuthorize) {
            VerificationData user;
            user = splashScreenInteractor.getUserInfo();
            Log.d("Splash", "Login: " + user.getEmail() + "Password: " + user.getPassword());
            //splashScreenInteractor.userAuthorization(user.getEmail(), user.getPassword(), this);
            subscriber = splashScreenInteractor.rxUserAuthorization(user.getEmail(), user.getPassword())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(session -> {
                        splashScreenInteractor.saveToken(session.getData().getToken());
                        Log.i(AppConsts.SPLASH_TAG, "token=" + session.getData().getToken());
                    })
                    .doOnError(throwable -> {
                        isAuthorize = false;
                        Log.i("Splash", "Error");
                    }).subscribe();


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
        splashScreenInteractor.saveToken(token);

        Log.i(AppConsts.SPLASH_TAG, "token: " + token);
    }
}
