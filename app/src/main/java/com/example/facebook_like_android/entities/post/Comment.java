package com.example.facebook_like_android.entities.post;

import android.graphics.Bitmap;

public class Comment {
    private final String username;
    private final String author;
    private final Bitmap profile;
    private String content;

    public Comment(String username, String author, Bitmap profile, String content) {
        this.username = username;
        this.author = author;
        this.profile = profile;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
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
