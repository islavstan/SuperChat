package com.internship.supercoders.superchat.chats;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chats.adapters.ChatsViewPagerAdapter;
import com.internship.supercoders.superchat.chats.views.ChatsView;
import com.internship.supercoders.superchat.db.DBMethods;


public class ChatsFragment extends Fragment implements ChatsView {
    ChatsViewPagerAdapter adapter;
    DBMethods dbMethods;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        dbMethods = new DBMethods(getActivity());
        FloatingActionButton fab = (FloatingActionButton)v. findViewById(R.id.fab);
        fab.setOnClickListener(view -> addNewChat());
          TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.public_chats)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.private_chats)));
        viewPager = (ViewPager)v. findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        adapter = new ChatsViewPagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



        return v;
    }


    @Override
    public void addNewChat() {

    }

    @Override
    public void loadUI() {

    }
}