package com.example.facebook_like_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivityLoginBinding;
import com.example.facebook_like_android.style.ThemeMode;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private InputError inputError;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO: add check to see if user exists
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inputError = new InputError(binding.etUsername, binding.etPassword);

        binding.etUsername.addTextChangedListener(watcher);
        binding.etPassword.addTextChangedListener(watcher);

        binding.btnLogin.setEnabled(!inputError.checkEmpty());

        binding.btnSignup.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });

        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        binding.btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, Feed.class);
            startActivity(i);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}