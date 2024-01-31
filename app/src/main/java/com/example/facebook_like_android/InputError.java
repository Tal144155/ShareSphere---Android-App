package com.example.facebook_like_android;

import android.text.TextUtils;
import android.widget.EditText;

public class InputError {
    enum FIELD {
        Username, Password, ConfirmPassword, Nickname;
    }
    private EditText[] fields;

    public InputError(EditText username, EditText password) {
        this.fields = new EditText[2];
        fields[FIELD.Username.ordinal()] = username;
        fields[FIELD.Password.ordinal()] = password;
    }

    public InputError(User user) {
        this.fields = new EditText[4];
        this.fields[FIELD.Username.ordinal()] = user.getUsername();
        this.fields[FIELD.Password.ordinal()] = user.getPassword();
        this.fields[FIELD.ConfirmPassword.ordinal()] = user.getRe_password();
        this.fields[FIELD.Nickname.ordinal()] = user.getNickname();
    }

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

    public boolean isPwdValid() {
        return fields[FIELD.Password.ordinal()].getText().toString().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
    }

    public boolean arePwdSame() {
        return fields[FIELD.Password.ordinal()].equals(fields[FIELD.ConfirmPassword.ordinal()]);
    }
}
