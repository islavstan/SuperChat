package com.internship.supercoders.superchat.ui.new_chat;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.supercoders.superchat.App;
import com.internship.supercoders.superchat.data.ChatType;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.ui.new_chat.adapter.SelectUserRvAdapter;
import com.internship.supercoders.superchat.ui.new_chat.interfaces.NewChatPresenter;
import com.internship.supercoders.superchat.ui.new_chat.interfaces.NewChatView;
import com.internship.supercoders.superchat.users.UsersInteractorImpl;
import com.internship.supercoders.superchat.users.interfaces.UsersInteractor;

import java.io.IOException;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Max on 13.03.2017.
 */
@InjectViewState
public class NewChatPresenterImpl extends MvpPresenter<NewChatView> implements NewChatPresenter {

    private List<UserDataPage.UserDataList> userListInfo;

    private UsersInteractor mUsersInteractor = new UsersInteractorImpl(App.getDataBaseManager(), App.getFileManager());

    @Override
    public void getUserList() {

        mUsersInteractor.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userDataPage -> {
                            userListInfo = userDataPage.getUserList();
                            SelectUserRvAdapter mAdapter = new SelectUserRvAdapter(userListInfo);
                            getViewState().initUserList(mAdapter);
                            updateUserAvatarts();
                        },
                        error -> {
                            error.printStackTrace();
                            Log.d("NewChatPresenter", "Error: " + error.toString());

                        });

    }

    private void updateUserAvatarts() {
        rx.Observable.from(userListInfo)
                .subscribeOn(Schedulers.io())
                .flatMap(userData -> rx.Observable.just(userData.getItem()))
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

    @Override
    public void createNewChat(ChatType privacy, String name, SelectUserRvAdapter userListAdapter) {

    }

    @Override
    public void loadPhoto() {

    }
}
