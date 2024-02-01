package com.example.facebook_like_android.users;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Users {
    enum FIELD {
        Username, Password, ConfirmPassword, Nickname
    }
    private final Map<String, Map<FIELD, String>> users;

    private static Users instance = null;

    private Users() {
        users = new HashMap<>();
    }
    public static synchronized Users getInstance() {
        if (instance == null) {
            instance = new Users();
        }
        return instance;
    }

    public void addUser(User user) {
        Map<FIELD, String> map = new HashMap<>();
        map.put(FIELD.Username, user.getUsername().getText().toString());
        map.put(FIELD.Password, user.getPassword().getText().toString());
        map.put(FIELD.ConfirmPassword, user.getRe_password().getText().toString());
        map.put(FIELD.Nickname, user.getNickname().getText().toString());
        users.put(user.getUsername().getText().toString(), map);
    }

    public boolean isSigned(EditText username, EditText password) {
        if (!users.containsKey(username.getText().toString()))
            return false;
        return Objects.equals(Objects.requireNonNull(
                users.get(username.getText().toString())).get(FIELD.Password),
                password.getText().toString());
    }

}
