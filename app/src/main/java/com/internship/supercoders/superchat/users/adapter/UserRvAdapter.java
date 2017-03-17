package com.internship.supercoders.superchat.users.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.models.user_info.UserDataFullProfile;
import com.internship.supercoders.superchat.models.user_info.UserDataPage.UserDataList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Max on 19.02.2017.
 */

public class UserRvAdapter extends RecyclerView.Adapter<UserRvAdapter.UserItemViewHolder> {
    private List<UserDataFullProfile> mUserList;

    public UserRvAdapter(List<UserDataFullProfile> userDataList) {
        this.mUserList = userDataList;
    }

    @Override
    public UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserItemViewHolder holder, int position) {
        String userName = mUserList.get(position).getName();
       // byte[] imageSource = mUserList.get(position).getAvatarObj();
        if (userName == null) {
            userName = mUserList.get(position).getEmail();
        }
        holder.tvFullName.setText(userName);
      //  if (imageSource == null) {
            holder.ivAvatar.setImageResource(R.drawable.ic_userpic_default);
      //  } else {
         //   holder.ivAvatar.setImageBitmap(BitmapFactory.decodeByteArray(imageSource, 0, imageSource.length));
        //}
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class UserItemViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivAvatar;
        public TextView tvFullName;

        public UserItemViewHolder(View itemView) {
            super(itemView);
            this.ivAvatar = (CircleImageView) itemView.findViewById(R.id.iv_userpic);
            this.tvFullName = (TextView) itemView.findViewById(R.id.tv_full_name);
        }
    }

}
