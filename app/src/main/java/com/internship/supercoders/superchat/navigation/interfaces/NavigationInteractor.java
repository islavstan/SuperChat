package com.internship.supercoders.superchat.navigation.interfaces;


import com.internship.supercoders.superchat.db.DBMethods;

import rx.Observable;

public interface NavigationInteractor {
    interface NavigationFinishedListener {

        void finishSignOut();

    }

    void getUserData();

    void signOut(DBMethods dbMethods, NavigationFinishedListener listener);
    void loadUsers(DBMethods dbMethods);
}
