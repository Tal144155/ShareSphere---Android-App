package com.example.facebook_like_android.entities;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.converters.PostConverter;
import com.example.facebook_like_android.utils.converters.UserConverter;

import java.util.ArrayList;
import java.util.List;

@Entity

public class User {
    @PrimaryKey @NonNull
    private String user_name;
    private String first_name;
    private String last_name;

    @NonNull
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(@NonNull String user_name) {
        this.user_name = user_name;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<User> getFriend_requests() {
        return friend_requests;
    }

    public void setFriend_requests(List<User> friend_requests) {
        this.friend_requests = friend_requests;
    }

    private String password;
    private String pic;
    @Ignore
    private Bitmap profileBitmap;
    @TypeConverters(PostConverter.class)
    private List<Post> posts;
    @TypeConverters(UserConverter.class)
    private List<User> friends;
    @TypeConverters(UserConverter.class)
    private List<User> friend_requests;

    public User(String user_name, String first_name, String last_name, String password, String pic, List<Post> posts, List<User> friends, List<User> friend_requests) {
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.pic = pic;
        if (posts != null)
            this.posts = posts;
        else
            this.posts = new ArrayList<>();
        if (friends != null)
            this.friends = friends;
        else
            this.friends = new ArrayList<>();
        if (friend_requests != null)
            this.friend_requests = friend_requests;
        else
            this.friend_requests = new ArrayList<>();
        profileBitmap = Base64Utils.decodeBase64ToBitmap(pic);
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String user_name) {
        this.user_name = user_name;
    }

    public String getFirstname() {
        return first_name;
    }

    public void setFirstname(String first_name) {
        this.first_name = first_name;
    }

    public String getLastname() {
        return last_name;
    }

    public void setLastname(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return pic;
    }

    public void setProfile(String pic) {
        this.pic = pic;
    }

    public Bitmap getProfileBitmap() {
        return profileBitmap;
    }

    public void setProfileBitmap(Bitmap profileBitmap) {
        this.profileBitmap = profileBitmap;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriendRequests() {
        return friend_requests;
    }

    public void setFriendRequests(List<User> friend_requests) {
        this.friend_requests = friend_requests;
    }
}
