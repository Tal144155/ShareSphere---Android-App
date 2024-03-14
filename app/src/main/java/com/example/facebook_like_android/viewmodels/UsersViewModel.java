package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.UserRepository;

public class UsersViewModel extends ViewModel {

    private UserRepository mRepository;

    public UsersViewModel() {
        mRepository = new UserRepository();
    }

    public LiveData<Boolean> hasChanged() { return mRepository.hasChanged(); }
    public LiveData<Boolean> hasRemoved() { return mRepository.hasRemoved(); }
    public LiveData<String> getMessage() { return mRepository.getMessage(); }

    public void update(String firstname, String lastname, String profile) {
        mRepository.update(firstname, lastname, profile);
    }

    public void delete() { mRepository.delete(); }
}
