package com.example.facebook_like_android.entities.post;

import android.graphics.Bitmap;

public class Comment {
    private String username;
    private String author;
    private Bitmap profile;
    private String content;

    public Comment(String username, String author, Bitmap profile, String content) {
        this.username = username;
        this.author = author;
        this.profile = profile;
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthor() {
        return author;
    }

    public Bitmap getProfile() {
        return profile;
    }
}
