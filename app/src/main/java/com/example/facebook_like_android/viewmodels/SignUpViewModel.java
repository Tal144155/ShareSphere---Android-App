package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.SignUpRepository;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository signUpRepository;


    public SignUpViewModel() {
        signUpRepository = new SignUpRepository();
    }

    public void unique(String username) {
        signUpRepository.unique(username);
    }

    public void signup(String username, String password, String firstname, String lastname, String profile) {
        signUpRepository.signup(username, password, firstname, lastname, profile);
    }

    public LiveData<Boolean> getUnique() { return signUpRepository.getUnique(); }

    public LiveData<Boolean> getAdded() { return signUpRepository.getAdded(); }


}
