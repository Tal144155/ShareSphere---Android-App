package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.FriendsAPI;
import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.responses.ListUsersResponse;

import java.util.ArrayList;
import java.util.List;

public class FriendsRepository {
    private UserDao userDao;
    private MutableLiveData<List<ListUsersResponse>> friends;
    private MutableLiveData<String> message;
    private MutableLiveData<Boolean> areFriends;
    private MutableLiveData<Boolean> hasChanged;
    private FriendsAPI friendsAPI;

    public FriendsRepository() {
        AppDB db = AppDB.getDatabase();
        userDao = db.userDao();
        friends = new MutableLiveData<>(new ArrayList<>());
        message = new MutableLiveData<>();
        hasChanged = new MutableLiveData<>();
        areFriends = new MutableLiveData<>(false);
        friendsAPI = new FriendsAPI(userDao);
    }

    public LiveData<List<ListUsersResponse>> getFriends() { return friends; }

    public LiveData<String> getMessage() { return message; }
    public void add(String friend) {
        friendsAPI.addFriend(friend, hasChanged, message);
    }
    public void reload(String username) {
        friendsAPI.getFriends(friends, message, username);
    }
    public void delete(String friend) {
        friendsAPI.deleteFriend(friend, hasChanged, message);
    }

    public void areFriends(String user1, String user2) {
        friendsAPI.areFriends(user1, user2, areFriends);
    }
    public LiveData<Boolean> checkFriends() { return areFriends; }
    public LiveData<Boolean> hasChanged() { return hasChanged; }
}
