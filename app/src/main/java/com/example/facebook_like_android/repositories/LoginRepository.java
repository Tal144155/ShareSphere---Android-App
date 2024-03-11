package com.example.facebook_like_android.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.LoginAPI;
import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.entities.UserDao;
import com.example.facebook_like_android.entities.post.AppDB;

public class LoginRepository {
    private UserDao userDao;
    private LoginAPI loginAPI;

    public LoginRepository() {
        AppDB db = AppDB.getDatabase();
        userDao = db.userDao();
        loginAPI = new LoginAPI();
    }

    public void login(String username, String password, MutableLiveData<Boolean> isLoggedIn, MutableLiveData<String> loginResult) {
        loginAPI.login(username, password, isLoggedIn, loginResult);
    }


    public void getUser(String username, MutableLiveData<User> user) {
        loginAPI.getUser(username, user);
    }
}
