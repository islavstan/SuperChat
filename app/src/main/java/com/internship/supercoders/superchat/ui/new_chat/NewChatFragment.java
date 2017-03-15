package com.internship.supercoders.superchat.ui.new_chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.ui.new_chat.adapter.SelectUserRvAdapter;
import com.internship.supercoders.superchat.ui.new_chat.interfaces.NewChatView;

/**
 * Created by Max on 11.03.2017.
 */

public class NewChatFragment extends MvpAppCompatFragment implements NewChatView {
    @InjectPresenter
    NewChatPresenterImpl mPresenter;

    RecyclerView recyclerView;
    SelectUserRvAdapter userListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_chat, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_user_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getUserList();
    }

    @Override
    public void initUserList(SelectUserRvAdapter adapter) {
        userListAdapter = adapter;
        recyclerView.setAdapter(userListAdapter);

    }

    @Override
    public void chatCreated() {

    }

    @Override
    public void updateUserList() {
        userListAdapter.notifyDataSetChanged();

    }
}
