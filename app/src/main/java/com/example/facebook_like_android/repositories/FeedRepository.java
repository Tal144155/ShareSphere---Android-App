package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.FeedAPI;
import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.entities.post.Post;

import java.util.List;

public class FeedRepository {
    private PostDao postDao;
    private MutableLiveData<List<Post>> posts;
    private MutableLiveData<String> message;
    private FeedAPI feedAPI;

    public FeedRepository() {
        postDao = AppDB.getDatabase().postDao();
        message = new MutableLiveData<>();
        posts = new MutableLiveData<>(postDao.index());
        feedAPI = new FeedAPI(postDao);
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void reload() { feedAPI.get(posts, message);}

}
