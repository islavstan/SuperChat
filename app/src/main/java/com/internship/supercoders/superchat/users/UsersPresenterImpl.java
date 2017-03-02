package com.internship.supercoders.superchat.users;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.supercoders.superchat.App;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;
import com.internship.supercoders.superchat.users.interfaces.UsersInteractor;
import com.internship.supercoders.superchat.users.interfaces.UsersPresenter;
import com.internship.supercoders.superchat.users.interfaces.UsersView;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Max on 17.02.2017.
 */
@InjectViewState
public class UsersPresenterImpl extends MvpPresenter<UsersView> implements UsersPresenter {
    private UsersInteractor mUsersInteractor = new UsersInteractorImpl(App.getDataBaseManager(), App.getFileManager());

    private List<UserDataPage.UserDataList> userListInfo;

    @Override
    public void getUsers() {
        mUsersInteractor.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userDataPage -> {
                            userListInfo = userDataPage.getUserList();
                            getViewState().initUserList(new UserRvAdapter(userListInfo));
                            updateUserAvatarts();
                        },
                        error -> Log.d("UserPresenter", "Error: " + error.toString()));

    }

    private void updateUserAvatarts() {
        Observable.from(userListInfo)
                .subscribeOn(Schedulers.io())
                .flatMap(userData -> Observable.just(userData.getItem()))
                .filter(user -> user.getBlobId() != null)
                .subscribe(user -> {
                    mUsersInteractor.getFile(user.getBlobId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(file -> {
                                try {
                                    user.setAvatarObj(file.bytes());
                                    mUsersInteractor.writeAvatarToDisk(file, user.getBlobId());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.d("UserPresenter", "IOException: " + e.toString());
                                }
                                getViewState().updateUserList();
                            }, error -> Log.d("UserPresenter", "updateUserAvatarError: " + error.getMessage()));
                });
    }
}
