package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.LoginAPI;
import com.example.facebook_like_android.entities.User;

public class LoginRepository {
    private LoginAPI loginAPI;
    private MutableLiveData<User> user;
    private MutableLiveData<Boolean> isLoggedIn;
    private MutableLiveData<String> loginResult;

    public LoginRepository() {
        isLoggedIn = new MutableLiveData<>();
        loginResult = new MutableLiveData<>();
        user = new MutableLiveData<>(null);
        loginAPI = new LoginAPI();
    }

    public void login(String username, String password) {
        loginAPI.login(username, password, isLoggedIn, loginResult);
    }


    public void getUser(String username, String token) {
        loginAPI.getUser(username, user, token);
    }

    public LiveData<User> getUserInfo() { return user; }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<String> getLoginResult() {
        return loginResult; }
}
