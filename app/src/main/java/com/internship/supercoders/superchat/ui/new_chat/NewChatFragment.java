package com.internship.supercoders.superchat.ui.new_chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chat.ChatActivity;
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
    EditText chatName;
    RadioGroup rgPrivacy;
    Button createBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_chat, container, false);

        chatName = (EditText) v.findViewById(R.id.et_chat_name);
        rgPrivacy = (RadioGroup) v.findViewById(R.id.radio_group_privacy);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_user_list);
        createBtn = (Button) v.findViewById(R.id.btn_create_chat);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        createBtn.setOnClickListener(view -> createChat());
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
    public void createChat() {
        String name = chatName.getText().toString();
        boolean isPublic;
        switch (rgPrivacy.getCheckedRadioButtonId()) {
            case R.id.radio_btn_private:
                isPublic = false;
                break;
            default:
                isPublic = true;
        }

        mPresenter.createNewChat(isPublic, name, userListAdapter);

    }

    @Override
    public void goToCreatedChat(String chatId) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("chatId", chatId);
        startActivity(intent);
    }


    @Override
    public void updateUserList() {
        userListAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
