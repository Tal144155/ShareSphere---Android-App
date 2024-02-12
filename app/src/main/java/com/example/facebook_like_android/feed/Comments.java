package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.CommentsListAdapter;
import com.example.facebook_like_android.databinding.ActivityCommentsBinding;
import com.example.facebook_like_android.entities.post.Comment;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.UserInfoManager;

public class Comments extends AppCompatActivity {
    private ActivityCommentsBinding binding;
    private CommentsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            binding.btnComment.setEnabled(!TextUtils.isEmpty(binding.etContent.getText()));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.btnComment.setEnabled(!TextUtils.isEmpty(binding.etContent.getText()));
        }

        @Override
        public void afterTextChanged(Editable s) {
            binding.btnComment.setEnabled(!TextUtils.isEmpty(binding.etContent.getText()));
        }
    };

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

         binding.etContent.addTextChangedListener(watcher);

        binding.btnComment.setOnClickListener(v -> {
            Comment comment = new Comment(UserInfoManager.getUsername(this),
                    UserInfoManager.getNickname(this),
                    UserInfoManager.getProfile(this),
                    binding.etContent.getText().toString());
            adapter.addComment(comment);
            binding.etContent.setText(null);
        });

        binding.btnBack.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("content", binding.etContent.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}