package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.repositories.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository profileRepository;

    public ProfileViewModel() { profileRepository = null; }

    public void setProfileRepository(String username) {
        profileRepository = new ProfileRepository(username);
    }


    public void add(String username, String firstname, String lastname, String profile, String pic, String content, String date) {
        profileRepository.add(username, firstname, lastname, profile, pic, content, date);
    }

    public void update(String postId, String content, String pic) {
        profileRepository.update(postId, content, pic);
    }

    public void delete(String postId) {
        profileRepository.delete(postId);
    }

    public void reload() { profileRepository.reload(); }

    public LiveData<List<Post>> getPosts() { return profileRepository.getPosts(); }

    public LiveData<String> getMessage() { return profileRepository.getMessage(); }

    public LiveData<Boolean> hasChanged() { return profileRepository.hasChanged(); }




}
