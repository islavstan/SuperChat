package com.internship.supercoders.superchat.chat;

import com.internship.supercoders.superchat.chat.adapter.ChatAdapter;



public interface ChatInteractor {
    void loadMessages(ChatAdapter adapter, String token, String chatId);

}
