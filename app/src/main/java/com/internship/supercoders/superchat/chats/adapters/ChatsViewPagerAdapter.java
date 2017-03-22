package com.internship.supercoders.superchat.chats.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.internship.supercoders.superchat.chats.fragments.PrivateChatsFragment;
import com.internship.supercoders.superchat.chats.fragments.PublicChatsFragment;



public class ChatsViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ChatsViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PublicChatsFragment tab1 = new PublicChatsFragment();
                return tab1;
            case 1:
                PrivateChatsFragment tab2 = new PrivateChatsFragment();
                return tab2;
            default:
                return null;
        }
    }





    @Override
    public int getItemPosition(Object object) {
      /*  if (object instanceof MyCardsFragment) {
            ((MyCardsFragment) object).update("");
        }*/
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}