package com.example.facebook_like_android.users;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Singleton class to manage user data and authentication
public class Users {
    // Enum representing user registration fields
    enum FIELD {
        Username, Password, ConfirmPassword, Nickname, ProfilePhoto
    }

    private final Map<String, Map<FIELD, String>> users;  // Map to store user data

    private static Users instance = null;  // Singleton instance

    // Private constructor to enforce singleton pattern
    private Users() {
        users = new HashMap<>();
    }

    // Method to get or create the singleton instance
    public static synchronized Users getInstance() {
        if (instance == null) {
            instance = new Users();
        }
        return instance;
    }

    // Method to add a new user to the users map
    public void addUser(User user) {
        Map<FIELD, String> map = new HashMap<>();
        map.put(FIELD.Username, user.getUsername().getText().toString());
        map.put(FIELD.Password, user.getPassword().getText().toString());
        map.put(FIELD.ConfirmPassword, user.getRe_password().getText().toString());
        map.put(FIELD.Nickname, user.getNickname().getText().toString());
        map.put(FIELD.ProfilePhoto, String.valueOf(user.getProfilePhoto()));
        users.put(user.getUsername().getText().toString(), map);
    }

    // Method to check if a user with the given username and password exists
    public boolean isSigned(EditText username, EditText password) {
        if (!users.containsKey(username.getText().toString()))
            return false;
        return Objects.equals(Objects.requireNonNull(
                        users.get(username.getText().toString())).get(FIELD.Password),
                password.getText().toString());
    }

    // Method to check if a username is unique (not already in use)
    public boolean isUnique(EditText username) {
        return !users.containsKey(username.getText().toString());
    }
}
