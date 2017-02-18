package com.internship.supercoders.superchat.users;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.supercoders.superchat.users.interfaces.UsersInteractor;
import com.internship.supercoders.superchat.users.interfaces.UsersPresenter;
import com.internship.supercoders.superchat.users.interfaces.UsersView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Max on 17.02.2017.
 */
@InjectViewState
public class UsersPresenterImpl extends MvpPresenter<UsersView> implements UsersPresenter {
    UsersInteractor mUsersInteractor = new UsersInteractorImpl();

    @Override
    public void getUsers() {
        mUsersInteractor.getFirstUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userDataPage -> Log.d("UserPresenter", "Current page: " + Integer.toString(userDataPage.getCurrentPage())),
                        error -> Log.d("UserPresenter", "Error: " + error.getMessage()));
    }
}
