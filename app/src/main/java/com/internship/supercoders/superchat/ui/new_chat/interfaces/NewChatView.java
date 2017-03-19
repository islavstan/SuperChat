package com.internship.supercoders.superchat.ui.new_chat.interfaces;

import com.arellomobile.mvp.MvpView;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;

import java.util.List;

/**
 * Created by Max on 13.03.2017.
 */

public interface NewChatView extends MvpView {
    void initUserList();

    void setUserList(List<UserDataPage.UserDataList> userInfo);

    void createChat();

    void goToCreatedChat(String chatId);

    void updateUserList();

    void showError(String errorMessage);
}
