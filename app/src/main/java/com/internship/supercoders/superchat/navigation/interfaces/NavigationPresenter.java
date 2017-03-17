package com.internship.supercoders.superchat.navigation.interfaces;


import com.internship.supercoders.superchat.db.DBMethods;

public interface NavigationPresenter {
     void getUserInfo();

     void logOut(DBMethods dbMethods);

     void loadUsers(DBMethods dbMethods);
}
