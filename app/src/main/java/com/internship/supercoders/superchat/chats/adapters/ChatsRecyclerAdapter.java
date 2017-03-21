package com.internship.supercoders.superchat.chats.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chat.ChatActivity;
import com.internship.supercoders.superchat.chat.service.SmackService;
import com.internship.supercoders.superchat.chats.UserActionsListener;
import com.internship.supercoders.superchat.chats.chat_model.ChatModel;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.utils.InternetConnection;
import com.vanniktech.emoji.EmojiTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ChatsRecyclerAdapter extends RecyclerView.Adapter<ChatsRecyclerAdapter.CustomViewHolder> {
    private List<DialogData> chatsList;
    private UserActionsListener mItemListener;
    DBMethods dbMethods;

    public ChatsRecyclerAdapter(List<DialogData> chatsList, UserActionsListener mItemListener, DBMethods dbMethods) {
        this.mItemListener =mItemListener;
        this.chatsList = chatsList;
        this.dbMethods = dbMethods;
    }


    @Override
    public ChatsRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats_item, parent, false);

        return new ChatsRecyclerAdapter.CustomViewHolder(itemView, mItemListener);
    }

    @Override
    public void onBindViewHolder(final ChatsRecyclerAdapter.CustomViewHolder holder, int position) {
        DialogData model = chatsList.get(position);
        holder.groupName.setText(model.getName());
        holder.message.setText(model.getLastMessage());
        if (model.getLastMessageDateSent() != null)
            holder.date.setText(getTime(model.getLastMessageDateSent()));
        if (model.getLastMessageUserId() != null) {
            dbMethods.getUserEmail(Integer.parseInt(model.getLastMessageUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(email -> holder.lastMessageUserName.setText(email),
                            error -> Log.d("stas", " dbMethods.getUserEmail error = " + error.getMessage()));
        }

        holder.cardView.setOnClickListener(v -> {
            mItemListener.openChat(model.getChatId());


        });


    }

   public String getTime(String time) {
       long updatedTime = Long.parseLong(time);
       long minute = (updatedTime / 60) % 60;
       long hour = (updatedTime / (60 * 60)) % 24;
       return String.format("%02d:%02d", hour, minute);
   }


    public void loadChats(List<DialogData> chatsList){
        this.chatsList.addAll(chatsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView userPhoto;
        TextView groupName;
        TextView lastMessageUserName;
        EmojiTextView message;
        TextView date;
        CardView cardView;
        private UserActionsListener mItemListener;

        public CustomViewHolder(View itemView, UserActionsListener listener) {
            super(itemView);
            mItemListener = listener;
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            userPhoto = (CircleImageView) itemView.findViewById(R.id.photo);
            groupName = (TextView) itemView.findViewById(R.id.groupName);
            lastMessageUserName = (TextView) itemView.findViewById(R.id.userName);
            message = (EmojiTextView) itemView.findViewById(R.id.lastMessage);
            date = (TextView) itemView.findViewById(R.id.date);


        }

        @Override
        public void onClick(View view) {


        }
    }
}