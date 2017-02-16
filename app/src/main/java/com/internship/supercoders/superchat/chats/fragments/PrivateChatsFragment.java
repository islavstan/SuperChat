package com.internship.supercoders.superchat.chats.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chats.views.FragmentChatView;


public class PrivateChatsFragment extends Fragment implements FragmentChatView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_private_chat, container, false);
        return v;
    }


    @Override
    public void loadUI(View v) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void goToChat() {

    }
}
