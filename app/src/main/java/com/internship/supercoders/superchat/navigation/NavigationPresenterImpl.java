package com.internship.supercoders.superchat.navigation;

import com.arellomobile.mvp.MvpPresenter;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationInteractor;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationInteractorImpl;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationPresenter;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NavigationPresenterImpl extends MvpPresenter<NavigationView> implements NavigationPresenter, NavigationInteractor.NavigationFinishedListener {

    NavigationInteractor navigationInteractor;
    private NavigationView view;

    public NavigationPresenterImpl(NavigationView view) {
        navigationInteractor = new NavigationInteractorImpl();
        this.view = view;
    }

    public void getUserInfo() {
    }

    @Override
    public void logOut(DBMethods dbMethods) {
     navigationInteractor.signOut(dbMethods,this);



    }

    @Override
    public void loadUsers(DBMethods dbMethods) {
        navigationInteractor.loadUsers(dbMethods);
    }


    @Override
    public void finishSignOut() {
        view.logOut();
    }
}
