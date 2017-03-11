package com.internship.supercoders.superchat.ui.new_chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.supercoders.superchat.R;

/**
 * Created by Max on 11.03.2017.
 */

public class NewChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_chat, container, false);
        return v;
    }
}
