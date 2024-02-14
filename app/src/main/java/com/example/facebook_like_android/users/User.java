package com.example.facebook_like_android.users;

import android.graphics.Bitmap;
import android.widget.EditText;

// Class representing a user entity with registration information
public class User {
    private final EditText username;  // User's username input field
    private final EditText password;  // User's password input field
    private final EditText re_password;  // User's re-entered password input field
    private final EditText nickname;  // User's nickname input field
    private Bitmap profilePhoto;  // URI for the user's profile photo

    // Constructor to initialize user attributes
    public User(EditText username, EditText password, EditText re_password, EditText nickname) {
        this.username = username;
        this.password = password;
        this.re_password = re_password;
        this.nickname = nickname;
        this.profilePhoto = null;  // Default profile photo is null
    }

    // Method to set the user's profile photo URI
    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    // Method to get the user's profile photo URI
    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }

    // Getter method for the username input field
    public EditText getUsername() {
        return username;
    }

    // Getter method for the password input field
    public EditText getPassword() {
        return password;
    }

    // Getter method for the re-entered password input field
    public EditText getRe_password() {
        return re_password;
    }

    // Getter method for the nickname input field
    public EditText getNickname() {
        return nickname;
    }
}
