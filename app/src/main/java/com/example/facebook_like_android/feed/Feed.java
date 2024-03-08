package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.entities.DatabaseHolder;
import com.example.facebook_like_android.entities.post.AppDB;
import com.example.facebook_like_android.entities.post.CommentDao;
import com.example.facebook_like_android.entities.post.PostDao;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;
import com.example.facebook_like_android.viewmodels.PostsViewModel;

/**
 * The Feed activity displays the main feed of posts in the application.
 * Users can view posts, access their profile, change theme, and navigate to other screens.
 */
public class Feed extends AppCompatActivity {
    private static final int PROFILE_REQUEST_CODE = 100;
    private ActivityFeedBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private PostsListAdapter adapter;
    private AppDB db;
    private PostDao postDao;
    private CommentDao commentDao;
    private PostsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = DatabaseHolder.getDatabase();
        commentDao = db.commentDao();

        // View Model
        viewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        viewModel.get().observe(this, posts -> adapter.setPosts(posts));

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this, postDao);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        // Initialize posts and set visibility
        adapter.initPosts();
        adapter.setFeedVisibility();

        // Set click listeners for buttons
        binding.btnMenu.setOnClickListener(v -> startActivity(new Intent(this, Menu.class)));
        binding.btnSearch.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
        binding.btnProfile.setOnClickListener(v -> startActivityForResult(new Intent(this, Profile.class), PROFILE_REQUEST_CODE));

        // Set profile image and nickname
        UserInfoManager.setProfile(binding.btnProfile);
        UserInfoManager.setNickname(binding.tvNickname);

        // Apply circular outline to profile image
        CircularOutlineUtil.applyCircularOutline(binding.btnProfile);
    }

    // Handle the result from Profile activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh feed if permission granted
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
                adapter.refreshFeed();
            }
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

    // Refresh feed onResume if permission granted
    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            adapter.refreshFeed();
        }
    }
}
