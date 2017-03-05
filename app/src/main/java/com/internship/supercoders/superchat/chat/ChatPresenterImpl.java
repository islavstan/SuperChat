package com.internship.supercoders.superchat.chat;


import com.internship.supercoders.superchat.chat.adapter.ChatAdapter;

public class ChatPresenterImpl implements ChatPresenter {
    private ChatView view;
    private ChatInteractor chatInteractor;

    public ChatPresenterImpl(ChatView view) {
        this.view = view;
        this.chatInteractor = new ChatInteractorImpl();
    }


    @Override
    public void sendMessage() {

    }

    @Override
    public void loadMessage(ChatAdapter adapter, String token, String chatId) {
        chatInteractor.loadMessages(adapter, token, chatId);

    }
}