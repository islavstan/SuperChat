package com.internship.supercoders.superchat.models.navigation_menu;

import com.internship.supercoders.superchat.navigation_view.NavigationItemId;

/**
 * Created by Max on 11.02.2017.
 */

public class NavMenuItem {
    private String itemText;
    private int iconRes;
    private NavigationItemId itemId;

    public NavMenuItem(int icon, String text, NavigationItemId id) {
        this.iconRes = icon;
        this.itemText = text;
        this.itemId = id;
    }

    public String getText() {
        return itemText;
    }

    public int getIconRes() {
        return iconRes;
    }

    public NavigationItemId getItemId() {
        return itemId;
    }
}
