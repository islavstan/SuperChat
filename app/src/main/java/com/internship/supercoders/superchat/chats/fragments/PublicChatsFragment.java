package com.internship.supercoders.superchat.chats.fragments;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.github.florent37.viewanimator.ViewAnimator;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chat.ChatActivity;
import com.internship.supercoders.superchat.chat.service.SmackService;
import com.internship.supercoders.superchat.chats.UserActionsListener;
import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.chats.chat_model.ChatModel;
import com.internship.supercoders.superchat.chats.presenters.PublicChatPresenter;
import com.internship.supercoders.superchat.chats.presenters.PublicChatPresenterImpl;
import com.internship.supercoders.superchat.chats.views.FragmentChatView;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PublicChatsFragment extends Fragment implements FragmentChatView {
    List<DialogData> chatsList = new ArrayList<>();
    RecyclerView recyclerView;
    ChatsRecyclerAdapter adapter;
    PublicChatPresenter presenter;
    private DBMethods db;
    RelativeLayout conProblemBlock;
    RelativeLayout photoProblemBlock;
    TextView problem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_public_chat, container, false);
        presenter = new PublicChatPresenterImpl(this);
        db = new DBMethods(getActivity());
        loadUI(v);
        loadData();


        db.checkError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == 1) {
                        showUploadPhotoError();

                        db.removeError()

                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aVoid -> {
                                }, error -> Log.d("stas",
                                        "removeError error = " + error.getMessage()));

                    }


                }, error -> Log.d("stas",
                        "checkError error = " + error.getMessage()));


        return v;
    }

    @Override
    public void loadUI(View v) {
        problem = (TextView)v.findViewById(R.id.problem);
        conProblemBlock = (RelativeLayout) v.findViewById(R.id.conProblemBlock);
        photoProblemBlock = (RelativeLayout) v.findViewById(R.id.photoProblemBlock);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        adapter = new ChatsRecyclerAdapter(chatsList, listener, db);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    UserActionsListener listener = id -> {
        if (!InternetConnection.hasConnection(getActivity())) {
            showConnectError();
        } else {

            Intent intent = new Intent(getContext(), SmackService.class);
            intent.putExtra("id", id);
            getContext().startService(intent);
            Intent intent1 = new Intent(getContext(), ChatActivity.class);
            intent1.putExtra("chatId", id);
            getContext().startActivity(intent1);
        }
    };


    @Override
    public void loadData() {
        if (!InternetConnection.hasConnection(getActivity()))
            presenter.loadDataWithOutInternet(db, adapter);
        else
            presenter.loadData(db, adapter);
    }

    @Override
    public void goToChat() {

    }

    public void showConnectError() {

        conProblemBlock.setVisibility(View.VISIBLE);
        conProblemBlock.postDelayed(() ->
                conProblemBlock.setVisibility(View.GONE), 2000);

    }

    public void showUploadPhotoError() {
        photoProblemBlock.setVisibility(View.VISIBLE);
        photoProblemBlock.postDelayed(() ->
                photoProblemBlock.setVisibility(View.GONE), 5000);

    }

}
