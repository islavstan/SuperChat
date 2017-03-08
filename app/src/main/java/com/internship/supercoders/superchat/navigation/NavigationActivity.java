package com.internship.supercoders.superchat.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.clans.fab.FloatingActionButton;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.authorization.AuthorizationActivity;
import com.internship.supercoders.superchat.chats.ChatsFragment;
import com.internship.supercoders.superchat.chats.adapters.ChatsViewPagerAdapter;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.navigation.adapter.NavMenuItem;
import com.internship.supercoders.superchat.navigation.adapter.NavigationItemId;
import com.internship.supercoders.superchat.navigation.adapter.NavigationMenuType;
import com.internship.supercoders.superchat.navigation.adapter.NavigationViewRecyclerAdapter;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationItemClickListener;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationView;
import com.internship.supercoders.superchat.users.UsersActivity;
import com.internship.supercoders.superchat.users.UsersFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.internship.supercoders.superchat.navigation.adapter.NavigationItemId.CREATE_NEW_CHAT;

public class NavigationActivity extends MvpAppCompatActivity implements NavigationView, NavigationItemClickListener {

   /* @InjectPresenter
    NavigationPresenterImpl mNavigationPresenter;*/
   NavigationPresenterImpl mNavigationPresenter;
    private List<NavMenuItem> topNavMenu = new ArrayList<>();
    private List<NavMenuItem> bottomNavMenu = new ArrayList<>();
    private RecyclerView topRecyclerView;
    private RecyclerView bottomRecyclerView;
    DrawerLayout drawer;
    CircleImageView ivAvatar;
    TextView name;
    TextView email;
    DBMethods dbMethods;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbMethods = new DBMethods(this);

        fragmentManager = getSupportFragmentManager();
        mNavigationPresenter = new NavigationPresenterImpl(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        topRecyclerView = (RecyclerView) findViewById(R.id.rv_navigation_menu_top);
        bottomRecyclerView = (RecyclerView) findViewById(R.id.rv_navigation_menu_bottom);
        createMenu(topRecyclerView, topNavMenu, NavigationMenuType.TOP);
        createMenu(bottomRecyclerView, bottomNavMenu, NavigationMenuType.BOTTOM);

        ivAvatar = (CircleImageView) findViewById(R.id.iv_user_avatar);
        name = (TextView) findViewById(R.id.tv_name);
        email = (TextView) findViewById(R.id.tv_email);
        mNavigationPresenter.getUserInfo();

        loadMyInfo();

        menuItemOnClick(CREATE_NEW_CHAT);


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

    private void createMenu(RecyclerView recyclerView, List<NavMenuItem> navMenu, NavigationMenuType menuType) {
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        prepareMenuItems(navMenu, menuType);
        NavigationViewRecyclerAdapter mAdapter = new NavigationViewRecyclerAdapter(navMenu, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void prepareMenuItems(List<NavMenuItem> navMenu, NavigationMenuType menuType) {
        navMenu.clear();
        switch (menuType) {
            case TOP:
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_add_chat, getString(R.string.chats_list), CREATE_NEW_CHAT));
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_users, getString(R.string.menu_users), NavigationItemId.USERS));
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_invite_users, getString(R.string.menu_invite_users), NavigationItemId.INVITE_USERS));
                break;
            case BOTTOM:
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_settings, getString(R.string.menu_settings), NavigationItemId.SETTINGS));
                navMenu.add(new NavMenuItem(R.drawable.ic_menu_log_out, getString(R.string.menu_log_out), NavigationItemId.LOG_OUT));
                break;
        }
    }

    @Override
    public void menuItemOnClick(NavigationItemId id) {
        drawer.closeDrawer(GravityCompat.START);
        switch (id) {
            case CREATE_NEW_CHAT:
                setTitle(getResources().getString(R.string.chats));
                ChatsFragment fragment = new ChatsFragment();
                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                break;
            case USERS:
                setTitle(getResources().getString(R.string.users));
                UsersFragment usersFragment = new UsersFragment();
                fragmentManager.beginTransaction().replace(R.id.content, usersFragment).commit();
                break;

            case INVITE_USERS:
                break;
            case SETTINGS:
                break;
            case LOG_OUT:

                mNavigationPresenter.logOut(dbMethods);


                break;
        }

    }


    @Override
    public void updateUserInfo() {

    }

    @Override
    public void addNewChat() {

    }

    @Override
    public void loadMyInfo() {
        dbMethods.getMyInfoForNavigation(ivAvatar, email, name);
    }

    @Override
    public void logOut() {
        Log.d("stas", "logOut");
        Intent intent = new Intent(NavigationActivity.this, AuthorizationActivity.class);
        startActivity(intent);
    }
}
