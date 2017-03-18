package com.internship.supercoders.superchat.chats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chats.UserActionsListener;
import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.chats.chat_model.ChatModel;
import com.internship.supercoders.superchat.chats.presenters.PublicChatPresenter;
import com.internship.supercoders.superchat.chats.presenters.PublicChatPresenterImpl;
import com.internship.supercoders.superchat.chats.views.FragmentChatView;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.dialog.DialogData;

import java.util.ArrayList;
import java.util.List;


public class PublicChatsFragment extends Fragment implements FragmentChatView {
    List<DialogData> chatsList = new ArrayList<>();
    RecyclerView recyclerView;
    ChatsRecyclerAdapter adapter;
    PublicChatPresenter presenter;
    private DBMethods db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_public_chat, container, false);
        presenter = new PublicChatPresenterImpl(this);
        db = new DBMethods(getActivity());
        loadUI(v);
        loadData();
        return v;
    }

    @Override
    public void loadUI(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        adapter = new ChatsRecyclerAdapter(chatsList, listener, db);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    UserActionsListener listener = () -> {
        presenter.openChat();

    };

    @Override
    public void loadData() {
        presenter.loadData(db, adapter);

    }

    @Override
    public void goToChat() {

    }


}
