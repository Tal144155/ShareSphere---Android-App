package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.parsers.JsonParser;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.io.IOException;

public class Feed extends AppCompatActivity {
    private ActivityFeedBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        // Call the method to read and parse the JSON file
        try {
            JsonParser.parsePosts(this, getAssets().open("posts.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.setFeedVisibility();

        // Set click listeners for buttons
        binding.btnMenu.setOnClickListener(v -> startActivity(new Intent(this, Menu.class)));
        binding.btnSearch.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
        binding.btnProfile.setOnClickListener(v -> startActivity(new Intent(this, Profile.class)));

        UserInfoManager.setProfile(this, binding.btnProfile);

        CircularOutlineUtil.applyCircularOutline(binding.btnProfile);
    }

}
