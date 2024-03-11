package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.entities.FriendsAPI;
import com.example.facebook_like_android.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsRepository {
    private UserDao userDao;
    private MutableLiveData<List<User>> friends;
    private MutableLiveData<String> message;
    private FriendsAPI friendsAPI;

    public FriendsRepository() {
        AppDB db = AppDB.getDatabase();
        userDao = db.userDao();
        friends = new MutableLiveData<>(new ArrayList<>());
        message = new MutableLiveData<>();
        friendsAPI = new FriendsAPI(userDao);
    }

    public LiveData<List<User>> getFriends() { return friends; }

    public LiveData<String> getMessage() { return message; }
    public void add(User user) {
        friendsAPI.addFriend(user, friends, message);
    }
    public void reload() {
        friendsAPI.getFriends(friends, message);
    }
    public void delete(User user) {
        friendsAPI.deleteFriend(user, friends, message);
    }
}
