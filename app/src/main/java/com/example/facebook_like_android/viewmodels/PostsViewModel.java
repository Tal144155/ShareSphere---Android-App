package com.example.facebook_like_android.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.repositories.PostRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {
    private PostRepository mRepository;
    private LiveData<List<Post>> posts;
    public PostsViewModel() {
        mRepository = new PostRepository();
        posts = mRepository.getAll();
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }
    public void add(Post post) {
        mRepository.add(post);
    }
    public void delete(Post post) {
        mRepository.delete(post);
    }
    public void reload() {
        mRepository.reload();
    }
}
