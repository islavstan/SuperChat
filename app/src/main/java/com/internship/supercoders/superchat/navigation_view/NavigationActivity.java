package com.internship.supercoders.superchat.navigation_view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.models.navigation_menu.NavMenuItem;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    private List<NavMenuItem> topNavMenu = new ArrayList<>();
    private List<NavMenuItem> bottomNavMenu = new ArrayList<>();
    private RecyclerView topRecyclerView;
    private RecyclerView bottomRecyclerView;
    private NavigationViewRecyclerAdapter topMenuAdapter;
    private NavigationViewRecyclerAdapter bottomMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        topRecyclerView = (RecyclerView) findViewById(R.id.rv_navigation_menu_top);
        bottomRecyclerView = (RecyclerView) findViewById(R.id.rv_navigation_menu_bottom);
        createMenu(topRecyclerView, topNavMenu, topMenuAdapter, NavigationMenuType.TOP);
        createMenu(bottomRecyclerView, bottomNavMenu, bottomMenuAdapter, NavigationMenuType.BOTTOM);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createMenu(RecyclerView recyclerView, List<NavMenuItem> navMenu, NavigationViewRecyclerAdapter mAdapter, NavigationMenuType menuType) {
        prepareMenuItems(navMenu, menuType);
        mAdapter = new NavigationViewRecyclerAdapter(navMenu);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void prepareMenuItems(List<NavMenuItem> navMenu, NavigationMenuType menuType) {
        navMenu.clear();
        switch (menuType) {
            case TOP:
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_add_chat, getString(R.string.menu_create_new_chat)));
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_users, getString(R.string.menu_users)));
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_invite_users, getString(R.string.menu_invite_users)));
                break;
            case BOTTOM:
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_settings, getString(R.string.menu_settings)));
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_log_out, getString(R.string.menu_log_out)));
                break;
        }
    }

}
