package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.repositories.FeedRepository;

import java.util.List;

public class FeedViewModel extends ViewModel {
    private FeedRepository feedRepository;

    public FeedViewModel() { feedRepository = new FeedRepository(); }

    public void reload() { feedRepository.reload(); }

    public LiveData<List<Post>> getPosts() { return feedRepository.getPosts(); }

    public LiveData<String> getMessage() { return feedRepository.getMessage(); }


}
