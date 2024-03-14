package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.LoginAPI;
import com.example.facebook_like_android.responses.UserResponse;

public class LoginRepository {
    private LoginAPI loginAPI;
    private MutableLiveData<UserResponse> user;
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

    public LiveData<UserResponse> getUserInfo() { return user; }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<String> getLoginResult() {
        return loginResult; }
}
