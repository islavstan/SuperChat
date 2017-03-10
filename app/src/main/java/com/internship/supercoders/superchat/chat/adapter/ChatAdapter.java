package com.internship.supercoders.superchat.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.chat.chat_model.MessageModel;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.points.Points;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    List<MessageModel> messageList;
    Context context;
    LayoutInflater layoutInflater;
    public static final int MY_MESSAGE = 0;
    public static final int NO_MY_MESSAGE = 1;
    public static final int DATE = 2;
    public static final int DELETE_MY_MESSAGE = 3;
    public static final int DELETE_NO_MY_MESSAGE = 4;
    int myId;
    DBMethods dbMethods;

    public ChatAdapter(List<MessageModel> messageList, Context context, int myId, DBMethods dbMethods) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messageList = messageList;
        this.context = context;
        this.myId = myId;
        this.dbMethods = dbMethods;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DELETE_MY_MESSAGE) {
            return new MyViewHolder(layoutInflater.inflate(R.layout.item_delete_right, parent, false), viewType);
        }
        if (viewType == DELETE_NO_MY_MESSAGE) {
            return new MyViewHolder(layoutInflater.inflate(R.layout.item_delete_left, parent, false), viewType);
        }
        if (viewType == MY_MESSAGE) {
            return new MyViewHolder(layoutInflater.inflate(R.layout.item_chat_right, parent, false), viewType);
        }
        if (viewType == NO_MY_MESSAGE) {
            return new MyViewHolder(layoutInflater.inflate(R.layout.item_chat_left, parent, false), viewType);
        } else
            return new MyViewHolder(layoutInflater.inflate(R.layout.item_date, parent, false), viewType);

    }


    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).isDeleteMessage() && messageList.get(position).getSenderId() == myId) {
            return DELETE_MY_MESSAGE;
        }
        if (messageList.get(position).isDeleteMessage() && messageList.get(position).getSenderId() != myId) {
            return DELETE_NO_MY_MESSAGE;
        }

        if (messageList.get(position).getDate() != null) {
            return DATE;
        }
        else if (messageList.get(position).getSenderId() == myId) {
            return MY_MESSAGE;
        }
        else return NO_MY_MESSAGE;
    }


    public void retrieveMessages(List<MessageModel> retrieveMessageList) {

        for (int i = 0; i < retrieveMessageList.size(); i++) {
            MessageModel model = retrieveMessageList.get(i);
            if (!messageList.isEmpty()) {

                String currentDate = getDateforSeparator(model.getUpdated_at());
                String previousDate = getDateforSeparator(messageList.get(messageList.size() - 1).getUpdated_at());


                if (!previousDate.equals(currentDate)) {
                    Log.d("Date", currentDate);
                    messageList.add(new MessageModel(currentDate));
                }

            }

            messageList.add(model);
            //Log.d("stas", model.getMessage());

        }
        notifyDataSetChanged();

    }


    private String getDateforSeparator(String mDate) {
        TimeZone tz = TimeZone.getTimeZone("Europe/Kiev");
        Calendar cal = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setCalendar(cal);
        try {
            cal.setTime(sdf.parse(mDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM");
        String currentDate = formatter.format(date);
        return currentDate;

    }

    private String getMessageDate(String mDate) {
        TimeZone serverTimeZone = TimeZone.getTimeZone("Etc/GMT-0");
        TimeZone currentTimeZone = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(currentTimeZone);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = cal.getTime();
        try {
            if (mDate != null) {
                cal.setTimeZone(serverTimeZone);
                dateFormat.setCalendar(cal);
                cal.setTime(dateFormat.parse(mDate));
                cal.setTimeZone(currentTimeZone);
                date = cal.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String currentDate = formatter.format(date);

        return currentDate;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessageModel model = messageList.get(position);

        if (model.getDate() != null) {
            holder.date.setText(model.getDate());
        } else if (!model.isDeleteMessage()) {

            holder.message.setText(model.getMessage());
            holder.time.setText(getMessageDate(model.getUpdated_at()));


            holder.bubble.setOnLongClickListener(view -> {
                deleteMessage(model, position);
                return true;
            });
        }

    }


    public void deleteMessage(MessageModel model, int position) {
        final Points.DeleteMessage apiService = ApiClient.getRxRetrofit().create(Points.DeleteMessage.class);
        Observable<Response<Void>> deleteMessageObservable = apiService.deleteMessage("application/json",
                dbMethods.getToken(), model.getMessageId());
        deleteMessageObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageResponse -> {
                            if (messageResponse.isSuccessful()) {
                                Log.d("stas", "delete ok");
                                model.setDeleteMessage(true);
                                notifyItemChanged(position);

                            } else try {
                                JSONObject jObjError = new JSONObject(messageResponse.errorBody().string());
                                Log.d("stas", "deleteMessage error = " + jObjError.getString("errors"));

                            } catch (Exception e) {
                                Log.d("stas", e.getMessage());
                            }
                        }


                );
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public void addNewMessage(String message, String from, String messageId) {
        messageList.add(new MessageModel(getSenderId(from), message, messageId));
        notifyItemInserted(messageList.size() - 1);


    }


    private int getSenderId(String from) {
        //52822_589b355da0eb4736db000004@muc.chat.quickblox.com/23738787-52822@chat.quickblox.com/Smack

        Log.d("stas", "from = " + from);
        String senderId = from.substring(from.indexOf("/") + 1, from.indexOf("-5"));
        Log.d("stas", Integer.parseInt(senderId) + " ---id");
        return Integer.parseInt(senderId);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userPhoto;
        RelativeLayout bubble;
        TextView message;
        TextView userName;
        TextView time;
        TextView date;


        public MyViewHolder(View v, int viewType) {
            super(v);
            switch (viewType) {
                //my message
                case 0:
                    userPhoto = (CircleImageView) v.findViewById(R.id.user_photo);
                    bubble = (RelativeLayout) v.findViewById(R.id.message_block);
                    message = (TextView) v.findViewById(R.id.txt_msg);
                    userName = (TextView) v.findViewById(R.id.name);
                    time = (TextView) v.findViewById(R.id.time);
                    break;
                //no my message
                case 1:
                    userPhoto = (CircleImageView) v.findViewById(R.id.user_photo);
                    bubble = (RelativeLayout) v.findViewById(R.id.message_block);
                    message = (TextView) v.findViewById(R.id.txt_msg);
                    userName = (TextView) v.findViewById(R.id.name);
                    time = (TextView) v.findViewById(R.id.time);
                    break;
                //date
                case 2:
                    date = (TextView) v.findViewById(R.id.date);
                    break;

            }
        }
    }
}