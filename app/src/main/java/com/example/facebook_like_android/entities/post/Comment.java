package com.example.facebook_like_android.entities.post;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.facebook_like_android.utils.Base64Utils;

@Entity(
        foreignKeys = @ForeignKey(entity = Post.class,
                parentColumns = "id",
                childColumns = "postId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("postId")}
)
public class Comment {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int postId; // Foreign key referencing the Post entity
    private final String username;
    private final String author;
    private final String profile;
    private String content;
    @Ignore
    private Bitmap profileBitmap;

    public Comment(String username, String author, String profile, String content) {
        this.username = username;
        this.author = author;
        this.profile = profile;
        this.content = content;
        this.profileBitmap = Base64Utils.decodeBase64ToBitmap(profile);
    }

    public Bitmap getProfileBitmap() {
        return profileBitmap;
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

    public String getProfile() {
        return profile;
    }

    // Getter and setter for postId
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
