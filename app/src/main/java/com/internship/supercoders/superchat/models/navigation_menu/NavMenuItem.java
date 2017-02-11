package com.internship.supercoders.superchat.models.navigation_menu;

/**
 * Created by Max on 11.02.2017.
 */

public class NavMenuItem {
    private String itemText;
    private int iconRes;

    public NavMenuItem(int icon, String text) {
        this.iconRes = icon;
        this.itemText = text;
    }

    public String getText() {
        return itemText;
    }

    public int getIconRes() {
        return iconRes;
    }
}
