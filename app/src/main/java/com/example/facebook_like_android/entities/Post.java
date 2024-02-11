package com.example.facebook_like_android.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String author;
    private String content;
    private int likes;
    private int pic;
    private int profile;
    private boolean isLiked;
    public boolean isLiked() {
        return isLiked;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getProfile() {
        return profile;
    }

    public Post(String author, String content, int pic, int profile) {
        this.author = author;
        this.content = content;
        this.pic = pic;
        this.profile = profile;
        this.likes = 0;
    }

    public void like() {
        // Toggle the like status
        isLiked = !isLiked;
        // Adjust the number of likes based on the like status
        if (isLiked) {
            likes++;
        } else {
            likes--;
        }
    }
}
