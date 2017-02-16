package com.internship.supercoders.superchat.chats;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.clans.fab.FloatingActionButton;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chats.adapters.ChatsViewPagerAdapter;
import com.internship.supercoders.superchat.chats.views.ChatsView;

public class ChatsActivity extends AppCompatActivity implements ChatsView {
    ViewPager viewPager;
    ChatsViewPagerAdapter adapter;
    Toolbar toolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        loadUI();
    }


    @Override
    public void addNewChat() {

    }

    @Override
    public void loadUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.chats));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> addNewChat());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.public_chats)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.private_chats)));
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);
        adapter = new ChatsViewPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
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


    }
}
