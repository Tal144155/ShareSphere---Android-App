package com.example.facebook_like_android.entities.post;

import androidx.lifecycle.MutableLiveData;

public class PostViewModel {

    private MutableLiveData<Post> post;

    public MutableLiveData<Post> getPost() {
        if (post == null) {
            post = new MutableLiveData<>();
        }
        return post;
    }
}
