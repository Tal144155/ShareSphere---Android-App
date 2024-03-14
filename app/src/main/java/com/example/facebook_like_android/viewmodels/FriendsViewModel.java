package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.FriendsRepository;
import com.example.facebook_like_android.responses.ListUsersResponse;

import java.util.List;

public class FriendsViewModel extends ViewModel {

    private FriendsRepository mRepository;

    public FriendsViewModel() {
        mRepository = new FriendsRepository();
    }

    public void add(String friend) {
        mRepository.add(friend);
    }

    public void delete(String friend) { mRepository.delete(friend); }

    public void reload(String username) { mRepository.reload(username); }

    public LiveData<List<ListUsersResponse>> getFriends() {
        return mRepository.getFriends();
    }

    public LiveData<String> getMessage() {
        return mRepository.getMessage();
    }


    public LiveData<Boolean> checkFriends() {
        return mRepository.checkFriends();
    }
    public LiveData<Boolean> hasChanged() {
        return mRepository.hasChanged();
    }

    public void checkIfFriends(String user1, String user2) { mRepository.areFriends(user1, user2); }
}
