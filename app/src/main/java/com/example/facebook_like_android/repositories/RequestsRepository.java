package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.RequestsAPI;
import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.entities.User;

import java.util.ArrayList;
import java.util.List;

public class RequestsRepository {
    private UserDao userDao;
    private MutableLiveData<List<User>> requests;
    private MutableLiveData<String> message;
    private RequestsAPI requestsAPI;

    public RequestsRepository() {
        AppDB db = AppDB.getDatabase();
        userDao = db.userDao();
        requests = new MutableLiveData<>(new ArrayList<>());
        message = new MutableLiveData<>();
        requestsAPI = new RequestsAPI(userDao);
    }

    public LiveData<List<User>> getRequests() { return requests; }

    public LiveData<String> getMessage() { return message; }
    public void add(String requestUsername) {
        requestsAPI.addFriendRequest(requestUsername, requests, message);
    }
    public void reload() {
        requestsAPI.getFriendRequests(requests, message);
    }
    public void delete(String requestUsername) {
        requestsAPI.deleteFriendRequest(requestUsername, requests, message);
    }
}
