package com.example.facebook_like_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivitySearchBinding;
import com.example.facebook_like_android.style.ThemeMode;

public class Search extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> startActivity(new Intent(this, Feed.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}