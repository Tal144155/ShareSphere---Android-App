package com.example.facebook_like_android.register;

import android.text.TextUtils;
import android.widget.EditText;

import com.example.facebook_like_android.users.User;
import com.example.facebook_like_android.users.Users;

public class InputError {
    enum FIELD {
        Username, Password, ConfirmPassword, Nickname
    }

    private final EditText[] fields;  // Array to store EditText fields for input validation

    // Constructor for handling validation of username and password during registration
    public InputError(EditText username, EditText password) {
        this.fields = new EditText[2];
        fields[FIELD.Username.ordinal()] = username;
        fields[FIELD.Password.ordinal()] = password;
    }

    // Constructor for handling validation of various fields during user creation
    public InputError(User user) {
        this.fields = new EditText[4];
        this.fields[FIELD.Username.ordinal()] = user.getUsername();
        this.fields[FIELD.Password.ordinal()] = user.getPassword();
        this.fields[FIELD.ConfirmPassword.ordinal()] = user.getRe_password();
        this.fields[FIELD.Nickname.ordinal()] = user.getNickname();
    }

    // Method to check if any input field is empty and display an error if so
    public boolean checkEmpty() {
        boolean isEmpty = false;
        for (int i = 0; i < fields.length; i++) {
            if (TextUtils.isEmpty(fields[i].getText())) {
                fields[i].setError(FIELD.values()[i] + " is required!");
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    // Method to check if any input field is empty
    public boolean isEmpty() {
        for (int i = 0; i < fields.length; i++) {
            if (TextUtils.isEmpty(fields[i].getText()))
                return true;
        }
        return false;
    }

    // Method to check if the username is unique and display an error if not
    public boolean isUnique() {
        Users users = Users.getInstance();
        if (!users.isUnique(fields[FIELD.Username.ordinal()])) {
            fields[FIELD.Username.ordinal()].setError("This username already exists!");
            return false;
        }
        return true;
    }

    // Method to check if all input fields are valid
    public boolean isValid() {
        return !isEmpty() && isPwdValid() && arePwdSame() && isUnique();
    }

    // Method to check if the password meets the specified criteria
    public boolean isPwdValid() {
        return fields[FIELD.Password.ordinal()].getText().toString().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
    }

    // Method to check if the password and confirm password fields match
    public boolean arePwdSame() {
        return fields[FIELD.Password.ordinal()].getText().toString().equals(fields[FIELD.ConfirmPassword.ordinal()].getText().toString());
    }
}
