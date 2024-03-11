package com.example.facebook_like_android.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.SignUpAPI;

public class SignUpRepository {
    private SignUpAPI signUpAPI;
    public SignUpRepository() {
        signUpAPI = new SignUpAPI();
    }

    public void signup(String username, String password, String firstname, String lastname, String profile, MutableLiveData<Boolean> isAdded) {
        signUpAPI.signup(username, password, firstname, lastname, profile, isAdded);
    }

    public void unique(String username, MutableLiveData<Boolean> unique) {
        signUpAPI.isUnique(username, unique);
    }

}
