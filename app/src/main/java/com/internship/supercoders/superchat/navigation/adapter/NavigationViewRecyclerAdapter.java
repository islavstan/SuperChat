package com.internship.supercoders.superchat.navigation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.navigation.interfaces.NavigationItemClickListener;

import java.util.List;

/**
 * Created by Max on 11.02.2017.
 */

public class NavigationViewRecyclerAdapter extends RecyclerView.Adapter<NavigationViewRecyclerAdapter.NavigationMenuViewHolder> {
    private List<NavMenuItem> mMenu;
    private NavigationItemClickListener mItemClickListener;

    public class NavigationMenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemIcon;
        public TextView itemText;

        public NavigationMenuViewHolder(View itemView) {
            super(itemView);
            itemIcon = (ImageView) itemView.findViewById(R.id.nav_menu_icon);
            itemText = (TextView) itemView.findViewById(R.id.tvItem);
            itemView.setOnClickListener(view -> {
                if (mItemClickListener != null) {
                    mItemClickListener.menuItemOnClick(mMenu.get(getAdapterPosition()).getItemId());
                }
            });
        }
    }

    public NavigationViewRecyclerAdapter(List<NavMenuItem> navMenu, NavigationItemClickListener listener) {
        this.mMenu = navMenu;
        this.mItemClickListener = listener;
    }

    @Override
    public NavigationMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item_menu_navigation, parent, false);
        return new NavigationMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NavigationMenuViewHolder holder, int position) {
        NavMenuItem menuItem = mMenu.get(position);
        holder.itemIcon.setImageResource(menuItem.getIconRes());
        holder.itemText.setText(menuItem.getText());

    }

    @Override
    public int getItemCount() {
        return mMenu.size();
    }


}
