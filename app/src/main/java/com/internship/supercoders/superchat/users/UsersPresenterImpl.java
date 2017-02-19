package com.internship.supercoders.superchat.users;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;
import com.internship.supercoders.superchat.users.interfaces.UsersInteractor;
import com.internship.supercoders.superchat.users.interfaces.UsersPresenter;
import com.internship.supercoders.superchat.users.interfaces.UsersView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Max on 17.02.2017.
 */
@InjectViewState
public class UsersPresenterImpl extends MvpPresenter<UsersView> implements UsersPresenter {
    private UsersInteractor mUsersInteractor = new UsersInteractorImpl();

    private List<UserDataPage.UserDataList> userListInfo;

    @Override
    public void getUsers() {
        mUsersInteractor.getFirstUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userDataPage -> {
                            userListInfo = userDataPage.getUserList();
                            getViewState().initUserList(new UserRvAdapter(userListInfo));

                        },
                        error -> Log.d("UserPresenter", "Error: " + error.getMessage()));
    }
}
