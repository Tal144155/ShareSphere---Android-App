package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.FriendsAPI;
import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.entities.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsRepository {
    private UserDao userDao;
    private MutableLiveData<List<User>> friends;
    private MutableLiveData<String> message;
    private MutableLiveData<Boolean> areFriends;
    private FriendsAPI friendsAPI;

    public FriendsRepository() {
        AppDB db = AppDB.getDatabase();
        userDao = db.userDao();
        friends = new MutableLiveData<>(new ArrayList<>());
        message = new MutableLiveData<>();
        areFriends = new MutableLiveData<>(false);
        friendsAPI = new FriendsAPI(userDao);
    }

    public LiveData<List<User>> getFriends() { return friends; }

    public LiveData<String> getMessage() { return message; }
    public void add(String friend) {
        friendsAPI.addFriend(friend, friends, message);
    }
    public void reload() {
        friendsAPI.getFriends(friends, message);
    }
    public void delete(String friend) {
        friendsAPI.deleteFriend(friend, friends, message);
    }

    public void areFriends(String user1, String user2) {
        friendsAPI.areFriends(user1, user2, areFriends);
    }
    public LiveData<Boolean> checkFriends() { return areFriends; }
}
