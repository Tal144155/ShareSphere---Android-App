package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.register.Login;
import com.example.facebook_like_android.databinding.ActivityMenuBinding;
import com.example.facebook_like_android.style.ThemeMode;

public class Menu extends AppCompatActivity {
    private ActivityMenuBinding binding;  // View binding instance for the activity
    private ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnLogout.setOnClickListener(v -> startActivity(new Intent(this, Login.class)));
        binding.btnHome.setOnClickListener(v -> startActivity(new Intent(this, Feed.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}