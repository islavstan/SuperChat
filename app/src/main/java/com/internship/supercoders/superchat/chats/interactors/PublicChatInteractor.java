package com.internship.supercoders.superchat.chats.interactors;

import android.support.annotation.NonNull;

import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.db.DBMethods;


public interface PublicChatInteractor {
    void loadData(ChatsRecyclerAdapter adapter, DBMethods dbMethods);

    void loadDataWithOutInternet(ChatsRecyclerAdapter adapter, DBMethods dbMethods);
}

