package com.internship.supercoders.superchat.chats.interactors;

import android.graphics.LightingColorFilter;
import android.util.Log;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.api.ApiConstant;
import com.internship.supercoders.superchat.chats.adapters.ChatsRecyclerAdapter;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.internship.supercoders.superchat.models.dialog.DialogModel;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.points.Points;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PublicChatInteractorImpl implements PublicChatInteractor {


    public PublicChatInteractorImpl() {

    }

    @Override
    public void loadData(ChatsRecyclerAdapter adapter, DBMethods db) {
//http://stackoverflow.com/questions/36785090/chaining-requests-in-retrofit-rxjava
        //http://stackoverflow.com/questions/38974960/rxjava-running-multiple-observables-after-another-like-concat-but-with-oncom

       // final Points.RetrieveDialogs retrieveDialog = ApiClient.getRxRetrofit().create(Points.RetrieveDialogs.class);
       /* retrieveDialog.retrieveDialogs(db.getToken()).map(DialogModel::getList)
                .doOnNext(adapter::loadChats)
                .flatMap(new Func1<List<DialogData>, Observable<DialogData>>() {
                    @Override
                    public Observable<DialogData> call(List<DialogData> dialogDatas) {
                        return Observable.from(dialogDatas);
                    }
                })
*/



      /*  retrieveDialog.retrieveDialogs(db.getToken()).map(DialogModel::getList)
                .doOnNext(adapter::loadChats)
                .flatMap(Observable::from)
                .doOnNext(data -> {
                    List<Integer> occupantsList = data.getOccupants_ids();
                    String occupants="";
                    for (int c = 0; c < occupantsList.size(); c++) {
                         occupants = occupants + occupantsList.get(c);
                        if (c < occupantsList.size() - 1)
                            occupants = occupants + ",";
                    }
                    Log.d("stas", occupants);

                })  .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tt->{});


*/

       /*retrieveDialog.retrieveDialogs(db.getToken()).map(DialogModel::getList)
              .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::loadChats);*/


       /* retrieveDialog.retrieveDialogs(db.getToken()).map(DialogModel::getList)
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    String chatId = data.getChatId();
                    Log.d("stas", chatId + " chatId");
                    List<Integer> list = data.getOccupants_ids();
                    String occupants = "";
                    for (int c = 0; c < list.size(); c++) {
                        occupants = occupants + list.get(c);
                        if (c < list.size() - 1)
                            occupants = occupants + ",";
                    }
                    Log.d("stas", occupants);
                    String finalOccupants = occupants;
                    db.checkChat(chatId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(chatExist -> {
                                        Log.d("stas", chatExist + " chatExist");
                                        if (chatExist == 0) {

                                            db.writeChatsData(data, finalOccupants).
                                                    subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(result -> Log.d("stas", result + " - write chat in db"));
                                        } else Log.d("stas", "chat exists in db");
                                    }

                            );


                });

*/




















        final Points.RetrieveDialogs retrieveDialogs = ApiClient.getRetrofit().create(Points.RetrieveDialogs.class);
        Call<DialogModel> call = retrieveDialogs.retrieve(db.getToken());
        call.enqueue(new Callback<DialogModel>() {
            @Override
            public void onResponse(Call<DialogModel> call, Response<DialogModel> response) {
                if (response.isSuccessful()) {
                    DialogModel model = response.body();
                    List<DialogData> dataList = model.getList();
                    for (int i = 0; i < dataList.size(); i++) {
                        DialogData data = dataList.get(i);
                        String chatId = data.getChatId();
                        Log.d("stas", chatId + " chatId");
                        List<Integer> list = data.getOccupants_ids();
                        String occupants = "";
                        for (int c = 0; c < list.size(); c++) {
                            occupants = occupants + list.get(c);
                            if (c < list.size() - 1)
                                occupants = occupants + ",";
                        }
                        Log.d("stas", occupants);

                        String finalOccupants = occupants;
                        db.checkChat(chatId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(integer -> {
                                    Log.d("stas", integer + " integer");
                                            if (integer == 0) {

                                                db.writeChatsData(data, finalOccupants).
                                                        subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(result -> Log.d("stas", result + " - write chat in db"));
                                            } else Log.d("stas", "chat exists in db");
                                        }


                                );
                    }




                    adapter.loadChats(model.getList());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("stas", "loadData error = " + jObjError.getString("errors"));

                    } catch (Exception e) {
                        Log.d("stas", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DialogModel> call, Throwable t) {

            }
        });


    }


}
