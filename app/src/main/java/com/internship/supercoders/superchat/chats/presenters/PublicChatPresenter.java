package com.internship.supercoders.superchat.chats.presenters;


import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;

public interface PublicChatPresenter {
    void loadData(String token, ChatsRecyclerAdapter adapter);

    void openChat();
}
