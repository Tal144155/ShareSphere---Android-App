package com.example.facebook_like_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.facebook_like_android.databinding.ActivityLoginBinding;
import com.example.facebook_like_android.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private boolean isDarkTheme = false;

    private void changeTheme() {
        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Light);
        } else {
            setTheme(R.style.AppTheme_Dark);
        }
        // TODO: which action should we call?
        onRestart(); // Recreate the activity to apply the new theme
        isDarkTheme = !isDarkTheme; // Toggle the theme flag
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChangeMode.setOnClickListener(v -> {
            if (isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            changeTheme();
        });

        binding.btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etUsername.getText())) {
                binding.etUsername.setError("Username is required!");
            }
            if (TextUtils.isEmpty(binding.etPasswordSu.getText())) {
                binding.etPasswordSu.setError("Password is required!");
            }
            if (TextUtils.isEmpty(binding.etConfirmPasswordSu.getText())) {
                binding.etConfirmPasswordSu.setError("Confirm password is required!");
            }
            if (TextUtils.isEmpty(binding.etNickname.getText())) {
                binding.etNickname.setError("Nickname is required!");
            }
            if (binding.etPasswordSu.getText() != binding.etConfirmPasswordSu.getText()) {
                binding.etConfirmPasswordSu.setError("Passwords must match!");
            }
            String pwd = String.valueOf(binding.etPasswordSu.getText());
            if (!pwd.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
                binding.etPasswordSu.setError("Invalid password!");
            }
        });
    }
}