package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.repositories.FriendsRepository;

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

    public void reload() { mRepository.reload(); }

    public LiveData<List<User>> getFriends() {
        return mRepository.getFriends();
    }

    public LiveData<String> getMessage() {
        return mRepository.getMessage();
    }


}
