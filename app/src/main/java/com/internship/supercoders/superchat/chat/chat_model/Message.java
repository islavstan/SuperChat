package com.internship.supercoders.superchat.chat.chat_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Message {
    @SerializedName("items")
    List<MessageModel> messageModelList;

    public List<MessageModel> getMessageModelList() {
        return messageModelList;
    }
}