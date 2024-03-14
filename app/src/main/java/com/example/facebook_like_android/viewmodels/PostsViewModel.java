package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.repositories.PostRepository;

public class PostsViewModel extends ViewModel {
    private PostRepository mRepository;
    public PostsViewModel() {
        mRepository = new PostRepository();

    }


    public LiveData<Boolean> hasChanged() { return mRepository.hasChanged(); }
    public LiveData<Boolean> isLiked() { return mRepository.isLiked(); }

    public void like(String postId) { mRepository.like(postId); }

    public void checkLiked(String postId) { mRepository.checkLiked(postId); }

//    public LiveData<Boolean> hasRemoved() { return mRepository.hasRemoved(); }
//    public LiveData<Boolean>getMessage() { return mRepository.getMessage(); }
//    public void update(String pic, String content, String postId) {
//        mRepository.update(pic, content, postId);
//    }
//    public void delete(String postId) {
//        mRepository.delete(postId);
//    }

}
