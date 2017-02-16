package com.internship.supercoders.superchat.chats.presenters;


import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.chats.interactors.PublicChatInteractor;
import com.internship.supercoders.superchat.chats.interactors.PublicChatInteractorImpl;
import com.internship.supercoders.superchat.chats.views.FragmentChatView;
import com.internship.supercoders.superchat.registration.RegistrationInteractor;
import com.internship.supercoders.superchat.registration.RegistrationView;

public class PublicChatPresenterImpl implements PublicChatPresenter {
    private FragmentChatView fragmentChatView;
    private PublicChatInteractor publicChatInteractor;

    public PublicChatPresenterImpl(FragmentChatView fragmentChatView) {
        this.fragmentChatView = fragmentChatView;
        publicChatInteractor = new PublicChatInteractorImpl();
    }

    @Override
    public void loadData(String token, ChatsRecyclerAdapter adapter) {
   publicChatInteractor.loadData(adapter, token);
    }

    @Override
    public void openChat() {

    }
}
