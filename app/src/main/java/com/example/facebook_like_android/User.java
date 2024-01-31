package com.example.facebook_like_android;

import android.widget.EditText;

public class User {
    private EditText username;
    private EditText password;
    private EditText re_password;
    private EditText nickname;

    // TODO: add picture

    public User(EditText username, EditText password, EditText re_password, EditText nickname) {
        this.username = username;
        this.password = password;
        this.re_password = re_password;
        this.nickname = nickname;
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
