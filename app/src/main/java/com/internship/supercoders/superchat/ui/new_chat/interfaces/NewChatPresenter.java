package com.internship.supercoders.superchat.ui.new_chat.interfaces;

import com.internship.supercoders.superchat.ui.new_chat.adapter.SelectUserRvAdapter;

/**
 * Created by Max on 13.03.2017.
 */

public interface NewChatPresenter {
    void getUserList();

    void createNewChat(boolean isPrivate, String name, SelectUserRvAdapter userListAdapter);

    void loadPhoto();


}
