package com.internship.supercoders.superchat.users;

import com.internship.supercoders.superchat.App;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.users.interfaces.UsersInteractor;

import rx.Observable;

/**
 * Created by Max on 17.02.2017.
 */

public class UsersInteractorImpl implements UsersInteractor {
    private DBMethods dbManager;

    UsersInteractorImpl() {
        this.dbManager = App.getDataBaseManager();

    }

    @Override
    public Observable<UserDataPage> getFirstUsers() {
        final Points.RxRetriveAllUsers apiService = ApiClient.getRxRetrofit().create(Points.RxRetriveAllUsers.class);
        return apiService.getUserInfoPage(dbManager.getToken(), "2", "10");
    }
}
