package com.internship.supercoders.superchat.ui.new_chat.interfaces;

import com.arellomobile.mvp.MvpView;
import com.internship.supercoders.superchat.ui.new_chat.adapter.SelectUserRvAdapter;

/**
 * Created by Max on 13.03.2017.
 */

public interface NewChatView extends MvpView {
    void initUserList(SelectUserRvAdapter selectUserRvAdapter);

    void chatCreated();

    void updateUserList();
}
