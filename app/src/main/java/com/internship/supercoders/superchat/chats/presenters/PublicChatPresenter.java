package com.internship.supercoders.superchat.chats.presenters;


import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.db.DBMethods;

public interface PublicChatPresenter {
    void loadData(DBMethods dbMethods, ChatsRecyclerAdapter adapter);

    void loadDataWithOutInternet(DBMethods dbMethods, ChatsRecyclerAdapter adapter);

    void openChat();
}
