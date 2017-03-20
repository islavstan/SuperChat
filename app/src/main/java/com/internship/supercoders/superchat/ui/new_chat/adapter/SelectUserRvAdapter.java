package com.internship.supercoders.superchat.ui.new_chat.adapter;

import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Max on 13.03.2017.
 */

public class SelectUserRvAdapter extends RecyclerView.Adapter<SelectUserRvAdapter.UserItemViewHolder> {
    private List<UserDataPage.UserDataList> mUserList;
    private List<Integer> mSelectedUserListId;
    private boolean allItemsEnable = true;

    public SelectUserRvAdapter() {
        this.mSelectedUserListId = new ArrayList<>();
        this.mUserList = new ArrayList<>();
    }

    @Override
    public SelectUserRvAdapter.UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item_checkbox, parent, false);
        return new SelectUserRvAdapter.UserItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectUserRvAdapter.UserItemViewHolder holder, int position) {
        UserDataPage.UserDataList currentItem = mUserList.get(position);
        String userName = currentItem.getItem().getName();
        byte[] imageSource = currentItem.getItem().getAvatarObj();

        holder.cbxSelectUser.setEnabled(allItemsEnable);
        holder.cbxSelectUser.setOnCheckedChangeListener(null);
        holder.cbxSelectUser.setChecked(currentItem.isSelected());
        if (currentItem.isSelected()) {
            holder.tvFullName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorTextBlack));
        } else {
            holder.tvFullName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorTextGray));
        }
        holder.cbxSelectUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentItem.setSelected(isChecked);
            if (isChecked) {
                mSelectedUserListId.add(currentItem.getItem().getId());
                holder.tvFullName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorTextBlack)); //change TextView color when item check
            } else {
                holder.tvFullName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorTextGray));
                int removeIndex = mSelectedUserListId.indexOf(currentItem.getItem().getId());
                if (removeIndex != -1) {
                    mSelectedUserListId.remove(removeIndex);
                }
            }
        });


        if (userName == null) {
            userName = currentItem.getItem().getEmail();
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

    public List<Integer> getSelectedUserId() {
        return mSelectedUserListId;
    }

    public void setAllItemsEnable(boolean allItemsEnable) {
        this.allItemsEnable = allItemsEnable;
        notifyItemRangeChanged(0, getItemCount());
    }

    public void setUserList(List<UserDataPage.UserDataList> userInfo) {
        mUserList = userInfo;
        notifyDataSetChanged();
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
