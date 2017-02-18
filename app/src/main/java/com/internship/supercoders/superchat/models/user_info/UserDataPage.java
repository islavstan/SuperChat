package com.internship.supercoders.superchat.models.user_info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Max on 17.02.2017.
 */

public class UserDataPage {
    @SerializedName("curren_page")
    int currentPage;
    @SerializedName("per_page")
    int perPage;
    @SerializedName("total_entries")
    int totalEntries;
    @SerializedName("items")
    List<UserDataFullProfile> userList;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public List<UserDataFullProfile> getUserList() {
        return userList;
    }
}
