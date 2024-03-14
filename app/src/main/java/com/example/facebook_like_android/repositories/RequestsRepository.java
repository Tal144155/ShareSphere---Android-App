package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.RequestsAPI;
import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.responses.ListUsersResponse;

import java.util.ArrayList;
import java.util.List;

public class RequestsRepository {
    private UserDao userDao;
    private MutableLiveData<List<ListUsersResponse>> requests;
    private MutableLiveData<String> message;
    private MutableLiveData<Boolean> hasChanged;
    private MutableLiveData<Boolean> hasRemoved;
    private RequestsAPI requestsAPI;

    public RequestsRepository() {
        AppDB db = AppDB.getDatabase();
        userDao = db.userDao();
        requests = new MutableLiveData<>(new ArrayList<>());
        message = new MutableLiveData<>();
        hasChanged = new MutableLiveData<>();
        hasRemoved = new MutableLiveData<>();
        requestsAPI = new RequestsAPI(userDao);
    }

    public LiveData<List<ListUsersResponse>> getRequests() { return requests; }

    public LiveData<String> getMessage() { return message; }
    public void add(String requestUsername) {
        requestsAPI.addFriendRequest(requestUsername, hasChanged, message);
    }
    public void reload() {
        requestsAPI.getFriendRequests(requests, message);
    }
    public void delete(String requestUsername) {
        requestsAPI.deleteFriendRequest(requestUsername, hasRemoved, message);
    }

    public LiveData<Boolean> hasChanged() { return hasChanged; }

    public LiveData<Boolean> hasRemoved() { return hasRemoved;
    }
}
