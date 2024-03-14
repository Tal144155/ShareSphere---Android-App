package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.PostAPI;

public class PostRepository {

    private MutableLiveData<Boolean> hasChanged;
    private MutableLiveData<Boolean> isLiked;
    private PostAPI postAPI;


    public PostRepository() {

        hasChanged = new MutableLiveData<>();
        isLiked = new MutableLiveData<>();
        postAPI = new PostAPI(isLiked, hasChanged);
    }

    public LiveData<Boolean> hasChanged() { return hasChanged; }

    public void like(String postId) { postAPI.like(postId); }

    public LiveData<Boolean> isLiked() { return isLiked; }
    public void checkLiked(String postId) { postAPI.isLiked(postId); }

//    public void delete(String postId) { postAPI.delete(postId); }
//
//
//    public void update(String pic, String content, String postId) {
//        postAPI.update(pic, content, postId);
//    }
}
