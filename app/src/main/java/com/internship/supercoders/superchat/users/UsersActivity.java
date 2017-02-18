package com.internship.supercoders.superchat.users;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.users.interfaces.UsersView;

public class UsersActivity extends MvpAppCompatActivity implements UsersView {
    @InjectPresenter
    UsersPresenterImpl mUsersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mUsersPresenter.getUsers();
    }
}
