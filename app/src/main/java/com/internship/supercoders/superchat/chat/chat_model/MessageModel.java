package com.internship.supercoders.superchat.chat.chat_model;

import com.google.gson.annotations.SerializedName;



public class MessageModel {
    @SerializedName("sender_id")
    int senderId;
    @SerializedName("message")
    String message;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("_id")
    String messageId;

    String date;

    boolean deleteMessage = false;

    public boolean isDeleteMessage() {
        return deleteMessage;
    }

    public void setDeleteMessage(boolean deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDate() {
        return date;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public MessageModel(String date) {
        this.date = date;
    }

    public MessageModel(int senderId, String message, String messageId) {
        this.senderId = senderId;
        this.message = message;
        this.messageId = messageId;
    }
}