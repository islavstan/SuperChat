package com.internship.supercoders.superchat.users;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;
import com.internship.supercoders.superchat.users.interfaces.UsersView;

import static com.facebook.FacebookSdk.getApplicationContext;


public class UsersFragment extends MvpAppCompatFragment implements UsersView {
    @InjectPresenter
    UsersPresenterImpl mUsersPresenter;


    RecyclerView recyclerView;
    UserRvAdapter userListAdapter;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);
        mUsersPresenter.getUsers();
        recyclerView = (RecyclerView)v. findViewById(R.id.rv_user_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }




    @Override
    public void initUserList(UserRvAdapter adapter) {
        userListAdapter = adapter;
        recyclerView.setAdapter(userListAdapter);
    }

    @Override
    public void updateUserList() {
        userListAdapter.notifyDataSetChanged();
        //TODO: userListAdapter.notifyItemChanged(position);
    }
}
