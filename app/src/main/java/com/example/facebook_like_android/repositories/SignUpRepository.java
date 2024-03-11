package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.SignUpAPI;

public class SignUpRepository {
    private MutableLiveData<Boolean> isUnique;
    private MutableLiveData<Boolean> isAdded;
    private SignUpAPI signUpAPI;
    public SignUpRepository() {
        isUnique = new MutableLiveData<>(true);
        isAdded = new MutableLiveData<>();
        signUpAPI = new SignUpAPI();
    }

    public void signup(String username, String password, String firstname, String lastname, String profile) {
        signUpAPI.signup(username, password, firstname, lastname, profile, isAdded);
    }

    public void unique(String username) {
        signUpAPI.isUnique(username, isUnique);
    }

    public LiveData<Boolean> getUnique() { return isUnique; }

    public LiveData<Boolean> getAdded() { return isAdded; }

}
