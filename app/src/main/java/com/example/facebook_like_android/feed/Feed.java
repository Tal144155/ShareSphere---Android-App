package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.parsers.JsonParser;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.io.IOException;

public class Feed extends AppCompatActivity {
    private static final int PROFILE_REQUEST_CODE = 100;
    private ActivityFeedBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private PostsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        // Call the method to read and parse the JSON file
        try {
            JsonParser.parsePosts(getAssets().open("posts.json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.setFeedVisibility();

        // Set click listeners for buttons
        binding.btnMenu.setOnClickListener(v -> startActivity(new Intent(this, Menu.class)));
        binding.btnSearch.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
        binding.btnProfile.setOnClickListener(v -> startActivityForResult(new Intent(this, Profile.class), PROFILE_REQUEST_CODE));

        UserInfoManager.setProfile(this, binding.btnProfile);

        CircularOutlineUtil.applyCircularOutline(binding.btnProfile);
    }

    // Handle the result from Profile
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this))
                adapter.refreshFeed();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("DEBUG", "onRequestPermissionsResult: requestCode=" + requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this))
            adapter.refreshFeed();
    }
}
