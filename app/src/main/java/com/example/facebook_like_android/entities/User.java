package com.example.facebook_like_android.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.utils.Base64Utils;

import java.util.List;

@Entity

public class User {
    @PrimaryKey
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String profile;
    @Ignore
    private Bitmap profileBitmap;
    private List<Post> posts;
    private List<User> friends;
    private List<User> friendRequests;

    public User(String username, String firstname, String lastname, String password, String profile, List<Post> posts, List<User> friends, List<User> friendRequests) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.profile = profile;
        this.posts = posts;
        this.friends = friends;
        this.friendRequests = friendRequests;
        profileBitmap = Base64Utils.decodeBase64ToBitmap(profile);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
        return friendRequests;
    }

    public void setFriendRequests(List<User> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
