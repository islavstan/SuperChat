package com.internship.supercoders.superchat.users;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_info.UserDataFullProfile;
import com.internship.supercoders.superchat.users.adapter.UserRvAdapter;
import com.internship.supercoders.superchat.users.interfaces.UsersView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.facebook.FacebookSdk.getApplicationContext;


public class UsersFragment extends MvpAppCompatFragment implements UsersView {
    @InjectPresenter
    UsersPresenterImpl mUsersPresenter;

    RecyclerView recyclerView;
    UserRvAdapter userListAdapter;
    DBMethods db;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);
        mUsersPresenter.getUsers();
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_user_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        db = new DBMethods(getActivity());
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


    public void findUser(String newText) {
        List<UserDataFullProfile> list = new ArrayList<>();
        db.getSearchUsers(newText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchUsers -> {
                    list.clear();
                    list.addAll(searchUsers);
                    userListAdapter.setNewData(list);
                }, error -> Log.d("stas", "getSearchUsers error = " + error.getMessage()));

    }
}
