package com.internship.supercoders.superchat.chat;



public interface ChatView {
    void messageRecieved();

    void messageSend();

    void showEmptyMessageInputError();

    void loadMessages();

}
