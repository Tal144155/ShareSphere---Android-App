package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.facebook_like_android.adapters.CommentsListAdapter;
import com.example.facebook_like_android.databinding.ActivityCommentsBinding;
import com.example.facebook_like_android.entities.post.AppDB;
import com.example.facebook_like_android.entities.post.Comment;
import com.example.facebook_like_android.entities.post.CommentDao;
import com.example.facebook_like_android.entities.post.PostDao;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.UserInfoManager;

/**
 * The Comments activity displays comments for a particular post.
 * Users can view, add, and interact with comments here.
 */
public class Comments extends AppCompatActivity {
    private ActivityCommentsBinding binding;
    private CommentsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();
    private AppDB db;
    private PostDao postDao;
    private CommentDao commentDao;
    // TextWatcher to enable/disable the comment button based on input
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

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "PostsDB")
                .allowMainThreadQueries().build();
        postDao = db.postDao();
        commentDao = db.commentDao();

        // Retrieve the Intent that started this activity and the extra info (the post position)
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        // Initialize RecyclerView for displaying comments
        RecyclerView lstComments = binding.lstComments;
        adapter = new CommentsListAdapter(this, position);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));

        // Add text changed listener to the comment EditText
        binding.etContent.addTextChangedListener(watcher);

        // Add click listener to the comment button to add a new comment
        binding.btnComment.setOnClickListener(v -> {
            Comment comment = new Comment(UserInfoManager.getUsername(),
                    UserInfoManager.getNickname(),
                    UserInfoManager.getProfile(),
                    binding.etContent.getText().toString());
            commentDao.insert(comment);
            adapter.addComment(comment);
            binding.etContent.setText(null);
        });

        // Add click listener to the back button to finish the activity
        binding.btnBack.setOnClickListener(v -> finish());

        // Add click listener to the theme change button to change the app theme
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}
