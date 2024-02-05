package com.example.facebook_like_android.users;

import android.net.Uri;
import android.widget.EditText;

public class User {
    private final EditText username;
    private final EditText password;
    private final EditText re_password;
    private final EditText nickname;
    private Uri profilePhoto;

    // TODO: add picture

    public User(EditText username, EditText password, EditText re_password, EditText nickname) {
        this.username = username;
        this.password = password;
        this.re_password = re_password;
        this.nickname = nickname;
        this.profilePhoto = null;
    }

    public void setProfilePhoto(Uri profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Uri getProfilePhoto() {
        return profilePhoto;
    }


    public EditText getUsername() {
        return username;
    }

    public EditText getPassword() {
        return password;
    }

    public EditText getRe_password() {
        return re_password;
    }

    public EditText getNickname() {
        return nickname;
    }


}
