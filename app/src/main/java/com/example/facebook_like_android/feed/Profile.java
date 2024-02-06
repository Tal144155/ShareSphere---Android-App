package com.example.facebook_like_android.feed;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.parsers.JsonParser;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private List<Post> myPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        // Call the method to read and parse the JSON file
        try {
            List<Post> posts;
            posts = JsonParser.parsePosts(this, getAssets().open("posts.json"));
            // Save only the files that are mine
            for (Post post : posts) {
                if (post.getAuthor().equals(UserInfoManager.getUsername(this)))
                    myPosts.add(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.setPosts(myPosts);

        adapter.setProfileVisibility();

        UserInfoManager.setProfile(this, binding.ivProfile);
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);

        UserInfoManager.setNickname(this, binding.tvNickname);
    }


}