package com.internship.supercoders.superchat.chats.interactors;

import android.support.annotation.NonNull;

import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;



public interface PublicChatInteractor {
    void loadData(ChatsRecyclerAdapter adapter, String token);
}

