package com.internship.supercoders.superchat.ui.new_chat.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Max on 13.03.2017.
 */

public class SelectUserRvAdapter extends RecyclerView.Adapter<SelectUserRvAdapter.UserItemViewHolder> {
    private List<UserDataPage.UserDataList> mUserList;

    public SelectUserRvAdapter(List<UserDataPage.UserDataList> userDataList) {
        this.mUserList = userDataList;
    }

    @Override
    public SelectUserRvAdapter.UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item_checkbox, parent, false);
        return new SelectUserRvAdapter.UserItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectUserRvAdapter.UserItemViewHolder holder, int position) {
        String userName = mUserList.get(position).getItem().getName();
        byte[] imageSource = mUserList.get(position).getItem().getAvatarObj();
        if (userName == null) {
            userName = mUserList.get(position).getItem().getEmail();
        }
        holder.tvFullName.setText(userName);
        if (imageSource == null) {
            holder.ivAvatar.setImageResource(R.drawable.ic_userpic_default);
        } else {
            holder.ivAvatar.setImageBitmap(BitmapFactory.decodeByteArray(imageSource, 0, imageSource.length));
        }
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class UserItemViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivAvatar;
        public TextView tvFullName;
        public CheckBox cbxSelectUser;

        public UserItemViewHolder(View itemView) {
            super(itemView);
            this.ivAvatar = (CircleImageView) itemView.findViewById(R.id.iv_userpic);
            this.tvFullName = (TextView) itemView.findViewById(R.id.tv_full_name);
            this.cbxSelectUser = (CheckBox) itemView.findViewById(R.id.cbx_user_select);
        }
    }
}
