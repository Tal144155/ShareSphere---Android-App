package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.api.ProfileAPI;
import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.responses.PostResponse;

import java.util.List;

public class ProfileRepository {

    private PostDao postDao;
    private MutableLiveData<List<PostResponse>> posts;
    private MutableLiveData<String> message;
    private MutableLiveData<Boolean> hasChanged;
    private MutableLiveData<Boolean> isValid;

    private ProfileAPI profileAPI;

    public ProfileRepository(String username) {
        postDao = AppDB.getDatabase().postDao();
        posts = new MutableLiveData<>();
        message = new MutableLiveData<>();
        hasChanged = new MutableLiveData<>();
        isValid = new MutableLiveData<>();
        profileAPI = new ProfileAPI(postDao, username, hasChanged, message, isValid);
    }

    public LiveData<List<PostResponse>> getPosts() {
        return posts;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Boolean> hasChanged() { return hasChanged; }
    public LiveData<Boolean> isValid() { return isValid; }


    public void add(String username, String firstname, String lastname, String profile, String pic, String content, String date) { profileAPI.add(username, firstname, lastname, profile, pic, content, date); }

    public void delete(String postId) { profileAPI.delete(postId); }

    public void reload() { profileAPI.get(posts);}

    public void update(String postId, String content, String pic) {
        profileAPI.update(postId, content, pic);
    }

    public void confirmLinks(String content) {
        profileAPI.checkLinks(content);
    }
}
