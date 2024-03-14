package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.LoginRepository;
import com.example.facebook_like_android.responses.UserResponse;

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

    public LiveData<UserResponse> getUserInfo() { return loginRepository.getUserInfo(); }

    public LiveData<Boolean> getIsLoggedIn() {
        return loginRepository.getIsLoggedIn();
    }

    public LiveData<String> getLoginResult() {
        return loginRepository.getLoginResult(); }
}
