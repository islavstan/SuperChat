package com.internship.supercoders.superchat.models.user_info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Max on 17.02.2017.
 */

public class UserDataPage {
    @SerializedName("curren_page")
    private int currentPage;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("total_entries")
    private int totalEntries;
    @SerializedName("items")
    private List<UserDataList> userList;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public List<UserDataList> getUserList() {
        return userList;
    }

    public class UserDataList {
        @SerializedName("user")
        private UserDataFullProfile item;
        private boolean isSelected;

        public UserDataFullProfile getItem() {
            return this.item;
        }

        public boolean isSelected() {
            return this.isSelected;
        }

        public void setSelected(boolean value) {
            this.isSelected = value;
        }
    }
}

