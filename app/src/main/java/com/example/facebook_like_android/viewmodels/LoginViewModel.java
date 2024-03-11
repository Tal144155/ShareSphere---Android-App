package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoggedIn;
    private MutableLiveData<String> loginResult;
    private MutableLiveData<User> user;
    private LoginRepository loginRepository;

    public LoginViewModel() {
        isLoggedIn = new MutableLiveData<>();
        loginResult = new MutableLiveData<>();
        user = new MutableLiveData<>(null);
        loginRepository = new LoginRepository();
    }

    public void login(String username, String password) {
        loginRepository.login(username, password, isLoggedIn, loginResult);
    }

    public void getUser(String username, String token) {
        loginRepository.getUser(username, user, token);
    }

    public LiveData<User> getUserInfo() { return user; }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<String> getLoginResult() {
        return loginResult; }
}
