package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.profile.IProfile;
import com.example.facebook_like_android.profile.Profile;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;
import com.example.facebook_like_android.viewmodels.FeedViewModel;
import com.example.facebook_like_android.viewmodels.PostsViewModel;

import java.lang.reflect.Field;

/**
 * The Feed activity displays the main feed of posts in the application.
 * Users can view posts, access their profile, change theme, and navigate to other screens.
 */
public class Feed extends AppCompatActivity implements IProfile, OnEditClickListener.OnLikeClickListener {
    private ActivityFeedBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private PostsListAdapter adapter;
    private FeedViewModel viewModel;
    private String postId;
    private ImageButton like;
    private int position;
    private TextView likes;
    private PostsViewModel postsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // View Model
//        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
//        postsViewModel.get().observe(this, posts -> adapter.setPosts(posts));

        // enlarging field size
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 50 * 1024 * 1024); // the new size is 100MB
        } catch (Exception e) {
            Log.d("DEBUG", e.getLocalizedMessage());
        }

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this, UserInfoManager.getUsername());
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnLikeClickListener(this);

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        postsViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged)
                postsViewModel.checkLiked(postId);
        });

        postsViewModel.isLiked().observe(this, isLiked -> adapter.setLiked(isLiked, position, like, likes));

        // Feed view model
        viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        viewModel.getMessage().observe(this, msg -> {
            binding.refreshLayout.setRefreshing(false);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        });
        viewModel.getPosts().observe(this, posts -> {
            // Set profile image and nickname
            UserInfoManager.setProfile(binding.btnProfile);
            UserInfoManager.setNickname(binding.tvNickname);
            adapter.setPosts(posts);
            binding.refreshLayout.setRefreshing(false);
        });

        binding.refreshLayout.setOnRefreshListener(() -> viewModel.reload());

        // Initialize posts and set visibility
//        adapter.initPosts();
        adapter.setFeedVisibility();
        adapter.setOnProfileClickListener(this);

        // Set click listeners for buttons
        binding.btnMenu.setOnClickListener(v -> startActivity(new Intent(this, Menu.class)));
        binding.btnSearch.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
        binding.btnProfile.setOnClickListener(v -> {
            Intent i = new Intent(this, Profile.class);
            i.putExtra("profile", UserInfoManager.getProfile())
                    .putExtra("firstname", UserInfoManager.getFirstName())
                    .putExtra("lastname", UserInfoManager.getLastName())
                    .putExtra("username", UserInfoManager.getUsername());
            startActivity(i);
        });

        // Set profile image and nickname
        UserInfoManager.setProfile(binding.btnProfile);
        UserInfoManager.setNickname(binding.tvNickname);

        // Apply circular outline to profile image
        CircularOutlineUtil.applyCircularOutline(binding.btnProfile);
    }

    // Handle the result from Profile activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
//            // Refresh feed if permission granted
//            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
//                adapter.refreshFeed();
//            }
//        }
//    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

    @Override
    public void viewProfile(String username, String profile, String nickname, String firstname, String lastname) {
        Intent i = new Intent(this, Profile.class);
        i.putExtra("username", username)
                .putExtra("profile", profile)
                .putExtra("firstname", firstname)
                .putExtra("lastname", lastname);
        startActivity(i);
    }

    @Override
    public void onLikeClick(String postId, int position, ImageButton like, TextView likes) {
        this.position = position;
        this.likes = likes;
        this.like = like;
        this.postId = postId;
        postsViewModel.like(postId);
    }

    // Refresh feed onResume if permission granted
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
//            adapter.refreshFeed();
//        }
//    }
}
