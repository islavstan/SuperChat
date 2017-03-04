package com.internship.supercoders.superchat.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.internship.supercoders.superchat.R;


public class ChatActivity extends AppCompatActivity implements ChatView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

    }

    @Override
    public void messageRecieved() {

    }

    @Override
    public void messageSend() {

    }

    @Override
    public void showEmptyMessageInputError() {

    }

    @Override
    public void loadMessages() {

    }
}
