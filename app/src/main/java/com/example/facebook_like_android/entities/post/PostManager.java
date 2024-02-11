package com.example.facebook_like_android.entities.post;

import java.util.ArrayList;
import java.util.List;

public class PostManager {
    private static PostManager instance = null;
    private List<Post> posts;

    private PostManager() {
        posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public static synchronized PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }
    public void addPost(Post post) {
        posts.add(post);
    }
    public void removePost(Post post) {
        posts.remove(post);
    }
    public void updatePost(int index, Post newPost) {
        if (index >= 0 && index < posts.size())
            posts.set(index, newPost);
    }
    public int getPosition(Post post) {
        return posts.indexOf(post);
    }
}
