package com.example.facebook_like_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.entities.Post;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {
    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView lstPosts = binding.lstPosts;
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Alice", "Hello world", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice2", "Hello 2", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice3", "Hello 3", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice4", "Hello 4", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice5", "Hello 5", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice6", "Hello 6", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice7", "Hello 7", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice8", "Hello 8", R.drawable.ic_darkmd, R.drawable.ic_user));
        posts.add(new Post("Alice9", "Hello 9", R.drawable.ic_darkmd, R.drawable.ic_user));
        adapter.setPosts(posts);

    }
}