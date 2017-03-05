package com.internship.supercoders.superchat.chat;

import com.internship.supercoders.superchat.chat.adapter.ChatAdapter;



public interface ChatPresenter {
    void sendMessage();
    void loadMessage(ChatAdapter adapter, String token, String chatId);
}