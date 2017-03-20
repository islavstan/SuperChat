package com.internship.supercoders.superchat.chats.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chat.ChatActivity;
import com.internship.supercoders.superchat.chat.service.SmackService;
import com.internship.supercoders.superchat.chats.UserActionsListener;
import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.chats.presenters.PublicChatPresenter;
import com.internship.supercoders.superchat.chats.presenters.PublicChatPresenterImpl;
import com.internship.supercoders.superchat.chats.views.FragmentChatView;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;


public class PrivateChatsFragment extends Fragment implements FragmentChatView {
    List<DialogData> chatsList = new ArrayList<>();
    RecyclerView recyclerView;
    ChatsRecyclerAdapter adapter;
    PublicChatPresenter presenter;
    private DBMethods db;
    RelativeLayout conProblemBlock;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_private_chat, container, false);
        presenter = new PublicChatPresenterImpl(this);
        db = new DBMethods(getActivity());
        loadUI(v);
        loadData();
        return v;
    }


    @Override
    public void loadUI(View v) {
        conProblemBlock = (RelativeLayout) v.findViewById(R.id.conProblemBlock);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        adapter = new ChatsRecyclerAdapter(chatsList, listener, db);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    UserActionsListener listener = id -> {
        if (!InternetConnection.hasConnection(getActivity())){
            showConnectError();
        }
        else {

            Intent intent = new Intent(getContext(), SmackService.class);
            intent.putExtra("id", id);
            getContext().startService(intent);
            Intent intent1 = new Intent(getContext(), ChatActivity.class);
            intent1.putExtra("chatId", id);
            getContext().startActivity(intent1);
        }
    };

    private void showConnectError() {
        conProblemBlock.setVisibility(View.VISIBLE);
        conProblemBlock.postDelayed(() ->
                conProblemBlock.setVisibility(View.GONE), 2000);

    }

    @Override
    public void loadData() {
        if (!InternetConnection.hasConnection(getActivity()))
            presenter.loadPrivateDataWithOutInternet(db, adapter);
        else
            presenter.loadPrivateData(db, adapter);
    }

    @Override
    public void goToChat() {

    }
}
