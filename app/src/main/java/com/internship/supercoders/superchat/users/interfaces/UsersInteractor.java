package com.internship.supercoders.superchat.users.interfaces;

import com.internship.supercoders.superchat.models.user_info.UserDataPage;

import rx.Observable;

/**
 * Created by Max on 17.02.2017.
 */

public interface UsersInteractor {
    Observable<UserDataPage> getFirstUsers();
}
