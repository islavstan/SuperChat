package com.internship.supercoders.superchat.users.interfaces;

import com.arellomobile.mvp.MvpView;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;

/**
 * Created by Max on 17.02.2017.
 */

public interface UsersView extends MvpView {
    void initUserList(UserRvAdapter userListAdapter);

    void updateUserList();
}
