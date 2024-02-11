package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.CommentsListAdapter;
import com.example.facebook_like_android.databinding.ActivityCommentsBinding;
import com.example.facebook_like_android.entities.post.Comment;
import com.example.facebook_like_android.utils.UserInfoManager;

public class Comments extends AppCompatActivity {
    private ActivityCommentsBinding binding;
    private CommentsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the Intent that started this activity and the extra info (the post position)
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        // Initialize RecyclerView for displaying comments
        RecyclerView lstComments = binding.lstComments;
        adapter = new CommentsListAdapter(this, position);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));

        binding.btnComment.setOnClickListener(v -> {
            Comment comment = new Comment(UserInfoManager.getUsername(this),
                    UserInfoManager.getNickname(this),
                    UserInfoManager.getProfile(this),
                    binding.etContent.getText().toString());
            adapter.addComment(comment);
            binding.etContent.setText(null);
        });

        binding.btnHome.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("content", binding.etContent.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}