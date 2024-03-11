package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.repositories.RequestsRepository;

import java.util.List;

public class RequestsViewModel extends ViewModel {
    private RequestsRepository mRepository;

    public RequestsViewModel() {
        mRepository = new RequestsRepository();
    }

    public void add(User user) {
        mRepository.add(user);
    }

    public void delete(User user) { mRepository.delete(user); }

    public void reload() { mRepository.reload(); }

    public LiveData<List<User>> getRequests() {
        return mRepository.getRequests();
    }

    public LiveData<String> getMessage() {
        return mRepository.getMessage();
    }
}
