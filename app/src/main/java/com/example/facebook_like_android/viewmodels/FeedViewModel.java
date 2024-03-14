package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.FeedRepository;
import com.example.facebook_like_android.responses.PostResponse;

import java.util.List;

public class FeedViewModel extends ViewModel {
    private FeedRepository feedRepository;

    public FeedViewModel() { feedRepository = new FeedRepository(); }

    public void reload() { feedRepository.reload(); }

    public LiveData<List<PostResponse>> getPosts() { return feedRepository.getPosts(); }

    public LiveData<String> getMessage() { return feedRepository.getMessage(); }


}
