package com.example.facebook_like_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivityMenuBinding;
import com.example.facebook_like_android.style.ThemeMode;

public class Menu extends AppCompatActivity {
    private ActivityMenuBinding binding;
    private ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogout.setOnClickListener(v -> startActivity(new Intent(this, Login.class)));
        binding.btnHome.setOnClickListener(v -> startActivity(new Intent(this, Feed.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

    }
}