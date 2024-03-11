package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public void login(String username, String password) {
        loginRepository.login(username, password);
    }

    public void getUser(String username, String token) {
        loginRepository.getUser(username, token);
    }

    public LiveData<User> getUserInfo() { return loginRepository.getUserInfo(); }

    public LiveData<Boolean> getIsLoggedIn() {
        return loginRepository.getIsLoggedIn();
    }

    public LiveData<String> getLoginResult() {
        return loginRepository.getLoginResult(); }
}
