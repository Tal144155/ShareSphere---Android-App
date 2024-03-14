package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.UserAPI;

public class UserRepository {

    private UserAPI userAPI;
    private MutableLiveData<Boolean> hasChanged;
    private MutableLiveData<Boolean> hasRemoved;
    private MutableLiveData<String> message;

    public UserRepository() {
        userAPI = new UserAPI();
        hasChanged = new MutableLiveData<>();
        hasRemoved = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }

    public LiveData<Boolean> hasChanged() { return hasChanged; }
    public LiveData<Boolean> hasRemoved() { return hasRemoved; }
    public LiveData<String> getMessage() { return message; }

    public void update(String firstname, String lastname, String profile) {
        userAPI.updateUser(firstname, lastname, profile, hasChanged, message);
    }

    public void delete() {
        userAPI.deleteUser(hasRemoved, message);
    }
}
