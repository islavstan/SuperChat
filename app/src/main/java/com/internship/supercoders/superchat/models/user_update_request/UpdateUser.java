package com.internship.supercoders.superchat.models.user_update_request;

import com.google.gson.annotations.SerializedName;



public class UpdateUser {
    @SerializedName("user")
    UpdateUserData updateUserData;


    public UpdateUser(UpdateUserData updateUserData) {
        this.updateUserData = updateUserData;
    }

    public UpdateUserData getUpdateUserData() {
        return updateUserData;
    }
}
