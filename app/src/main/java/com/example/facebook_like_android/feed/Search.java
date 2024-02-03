package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivitySearchBinding;
import com.example.facebook_like_android.style.ThemeMode;

public class Search extends AppCompatActivity {
    private ActivitySearchBinding binding;  // View binding instance for the activity
    private final ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnBack.setOnClickListener(v -> startActivity(new Intent(this, Feed.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}
