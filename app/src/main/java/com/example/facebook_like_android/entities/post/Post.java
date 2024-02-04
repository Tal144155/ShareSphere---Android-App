package com.example.facebook_like_android.entities.post;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;  // Unique identifier for the post
    private String author;  // Author of the post
    private String content;  // Content of the post
    private int likes;  // Number of likes on the post
    private int pic;  // Resource ID for the post picture
    private int profile;  // Resource ID for the profile picture of the post's author
    private boolean isLiked;  // Flag to indicate whether the post is liked
    private List<Comment> comments;

    // Getter method to check if the post is liked
    public boolean isLiked() {
        return isLiked;
    }

    // Getter method to retrieve the author of the post
    public String getAuthor() {
        return author;
    }

    // Setter method to set the author of the post
    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter method to retrieve the content of the post
    public String getContent() {
        return content;
    }

    // Setter method to set the content of the post
    public void setContent(String content) {
        this.content = content;
    }

    // Getter method to retrieve the number of likes on the post
    public int getLikes() {
        return likes;
    }

    // Setter method to set the number of likes on the post
    public void setLikes(int likes) {
        this.likes = likes;
    }

    // Getter method to retrieve the resource ID for the post picture
    public int getPic() {
        return pic;
    }

    // Setter method to set the resource ID for the post picture
    public void setPic(int pic) {
        this.pic = pic;
    }

    // Getter method to retrieve the resource ID for the profile picture of the post's author
    public int getProfile() {
        return profile;
    }

    // Constructor to initialize a new post with essential information
    public Post(String author, String content, int pic, int profile) {
        this.author = author;
        this.content = content;
        this.pic = pic;
        this.profile = profile;
        this.likes = 0;  // Initial likes count is set to 0
        this.comments = new ArrayList<>();
    }

    // Method to handle the liking of the post
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
    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
