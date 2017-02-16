package com.internship.supercoders.superchat.chats.views;

import android.view.View;

/**
 * Created by islav on 16.02.2017.
 */

public interface FragmentChatView {
    void loadUI(View v);

    void loadData();

    void goToChat();
}
