package com.example.facebook_like_android.entities.post;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.facebook_like_android.entities.Comment;
import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.UserInfoManager;
import com.example.facebook_like_android.utils.converters.CommentConverter;
import com.example.facebook_like_android.utils.converters.UserConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @PrimaryKey @NonNull
    @SerializedName("_id")
    @Expose
    private String postId;  // Unique identifier for the post
//    @PrimaryKey
//    private String id;
    @Ignore
    private String author;  // Author of the post

    private String first_name;
    private String last_name;
    private String content;  // Content of the post
    private int likes;  // Number of likes on the post
    @Ignore
    private Bitmap picBitmap = null;
    @Ignore
    private Bitmap profileBitmap = null;
    private String profile;
    private String pic;

    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("liked_by")
    @Expose
    @TypeConverters(UserConverter.class)
    private List<User> likedBy;
    @Ignore
    private boolean isLiked;  // Flag to indicate whether the post is liked
    @SerializedName("user_name")
    @Expose
    private String username;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public void addlike() { this.likes++; }
    public void unlike() { this.likes--; }


    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<User> likedBy) {
        this.likedBy = likedBy;
    }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @TypeConverters(CommentConverter.class)
    private List<Comment> comments;
    @Ignore
    private int picID = NOT_RES;
    @Ignore
    private int profileID = NOT_RES;
    public static final int NOT_RES = -1;
    @Ignore
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");

    public String getPublishDate() {
        return publishDate;
    }


    // Getter method to check if the post is liked
    public boolean isLiked() {
        return isLiked;
    }

    // Getter method to retrieve the author of the post
    public String getAuthor() {
        return author;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    public Bitmap getPicBitmap() {
        picBitmap = Base64Utils.decodeBase64ToBitmap(pic);
        return picBitmap;
    }

    // Setter method to set the resource ID for the post picture
    public void setPic(Bitmap pic) {
        //this.pic = pic;
        this.pic = Base64Utils.encodeBitmapToBase64(pic);
        this.picID = NOT_RES;
    }

    // Getter method to retrieve the resource ID for the profile picture of the post's author
    public Bitmap getProfileBitmap() {
        profileBitmap = Base64Utils.decodeBase64ToBitmap(profile);
        return profileBitmap;
    }

    public String getProfile() {
        return profile;
    }

    public String getPic() {
        return pic;
    }

    public Post(String username, String first_name, String last_name, String content, String pic, String profile,
                int likes, List<User> likedBy, String publishDate, @NonNull String postId) {
        this.postId = postId;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.author = first_name + " " + last_name;
        this.content = content;
        this.pic = pic;
        this.picBitmap = Base64Utils.decodeBase64ToBitmap(pic);
        this.profile = profile;
        this.profileBitmap = Base64Utils.decodeBase64ToBitmap(profile);
        this.likedBy = likedBy;
        if (likedBy == null)
            this.likedBy = new ArrayList<>();
        this.likes = likes;
        this.isLiked = false;
        if (likedBy != null) {
            for (User user : likedBy) {
                if (user.getUsername().equals(username)) {
                    this.isLiked = true;
                    break;
                }
            }
        }
        this.comments = new ArrayList<>();
        this.publishDate = publishDate;
    }


    @Ignore
    // Constructor to initialize a new post with essential information
    public Post(String username, String first_name, String last_name, String content, Bitmap pic, Bitmap profile, @NonNull String postId) {
        this.postId = postId;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.author = first_name + " " + last_name;
        this.content = content;
        this.pic = Base64Utils.encodeBitmapToBase64(pic);
        this.profile = Base64Utils.encodeBitmapToBase64(profile);
        this.picBitmap = pic;
        this.profileBitmap = profile;
        this.likes = 0;  // Initial likes count is set to 0
        this.comments = new ArrayList<>();
        Date now = Calendar.getInstance().getTime();
        this.publishDate = dateFormat.format(now);
        this.likedBy = new ArrayList<>();
    }
    @Ignore
    // Constructor to initialize a new post with essential information
    public Post(String username, String first_name, String last_name, String content, Bitmap pic, Bitmap profile, Date publishDate, @NonNull String postId) {
        this.postId = postId;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.author = first_name + " " + last_name;
        this.content = content;
        this.pic = Base64Utils.encodeBitmapToBase64(pic);
        this.profile = Base64Utils.encodeBitmapToBase64(profile);
        this.likes = 0;  // Initial likes count is set to 0
        this.comments = new ArrayList<>();
        this.publishDate = dateFormat.format(publishDate);
        this.likedBy = new ArrayList<>();
    }

    @Ignore
    // Constructor to initialize a new post with essential information
    public Post(String username, String first_name, String last_name, String content, int pic, int profile, @NonNull String postId) {
        this.postId = postId;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.author = first_name + " " + last_name;
        this.content = content;
        this.picID = pic;
        this.profileID = profile;
        this.likes = 0;  // Initial likes count is set to 0
        this.comments = new ArrayList<>();
        Date now = Calendar.getInstance().getTime();
        this.publishDate = dateFormat.format(now);
        this.likedBy = new ArrayList<>();
    }
    @Ignore
    public int getProfileID() {
        return profileID;
    }
    @Ignore
    public int getPicID() {
        return picID;
    }

    // Method to handle the liking of the post
    public void like() {
        User user = UserInfoManager.getUser();
        // Toggle the like status
        isLiked = !isLiked;
        // Adjust the number of likes based on the like status
        if (isLiked) {
            likes++;
            likedBy.add(user);
        } else {
            likes--;
            likedBy.remove(user);
        }
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
