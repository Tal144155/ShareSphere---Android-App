package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.SignUpRepository;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<Boolean> isUnique;
    private MutableLiveData<Boolean> isAdded;
    private SignUpRepository signUpRepository;


    public SignUpViewModel() {
        isUnique = new MutableLiveData<>(true);
        isAdded = new MutableLiveData<>();
        signUpRepository = new SignUpRepository();
    }

    public void unique(String username) {
        signUpRepository.unique(username, isUnique);
    }

    public void signup(String username, String password, String firstname, String lastname, String profile) {
        signUpRepository.signup(username, password, firstname, lastname, profile, isAdded);
    }

    public LiveData<Boolean> getUnique() { return isUnique; }

    public LiveData<Boolean> getAdded() { return isAdded; }


}
