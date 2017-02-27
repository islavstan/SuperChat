package com.internship.supercoders.superchat.users.interfaces;

import com.internship.supercoders.superchat.models.user_info.UserDataPage;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Max on 17.02.2017.
 */

public interface UsersInteractor {
    Observable<UserDataPage> getUsers();

    Observable<okhttp3.ResponseBody> getFile(String blobId);

    void writeAvatarToDisk(ResponseBody body, String id) throws IOException;
}
