package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.parsers.JsonParser;
import com.example.facebook_like_android.style.ThemeMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {
    private ActivityFeedBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private List<Post> posts = new ArrayList<>();

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
            posts = JsonParser.parsePosts(this, getAssets().open("posts.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the parsed posts to the adapter
        adapter.setPosts(posts);

        // Set click listeners for buttons
        binding.btnMenu.setOnClickListener(v -> startActivity(new Intent(this, Menu.class)));
        binding.btnSearch.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}
