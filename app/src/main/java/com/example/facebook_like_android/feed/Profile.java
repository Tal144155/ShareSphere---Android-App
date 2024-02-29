package com.example.facebook_like_android.feed;

import static com.example.facebook_like_android.adapters.PostsListAdapter.COMMENTS_REQUEST_CODE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.entities.post.AppDB;
import com.example.facebook_like_android.entities.post.CommentDao;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.PostDao;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;

/**
 * The Profile activity displays the user's profile information and posts.
 * Users can create, edit, and delete posts from their profile.
 */

public class Profile extends AppCompatActivity implements OnEditClickListener, OnEditClickListener.OnDeleteClickListener {
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;
    private final ImageHandler imageHandler = new ImageHandler(this);
    private ImageView prvImg;
    private final ThemeMode mode = ThemeMode.getInstance();
    private Bitmap bitmap;
    private boolean isPicSelected = false;
    private EditText content;
    private AppDB db;
    private PostDao postDao;
    private CommentDao commentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "PostsDB")
                .allowMainThreadQueries().build();
        postDao = db.postDao();
        commentDao = db.commentDao();


        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this, postDao);
        lstPosts.setAdapter(adapter);

        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter.setProfileVisibility();
        adapter.setUsername(UserInfoManager.getUsername(this));
        adapter.setOnEditClickListener(this);
        adapter.setOnDeleteClickListener(this);

        // Set profile information
        UserInfoManager.setProfile(this, binding.ivProfile);
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        UserInfoManager.setNickname(this, binding.tvNickname);

        // Set click listeners for buttons
        binding.btnHome.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        // Set listener for creating a new post
        binding.etCreatePost.setOnClickListener(v -> {
            binding.btnImg.setVisibility(View.VISIBLE);
            binding.btnCreate.setVisibility(View.VISIBLE);
            createPost();
        });


    }

    // Method to handle creating a new post
    private void createPost() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnCreate.setEnabled(isContentValid());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.btnCreate.setEnabled(isContentValid());
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.btnCreate.setEnabled(isContentValid());
            }
        };
        content = binding.etCreatePost;
        content.addTextChangedListener(watcher);

        // Uploading an image
        binding.btnImg.setOnClickListener(v -> {
            prvImg = binding.ivPic;
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
                imageHandler.openChooser();
                prvImg.setVisibility(View.VISIBLE);
            }
        });

        // Clicking on Create Post button
        binding.btnCreate.setOnClickListener(v -> {
            Post post = new Post(UserInfoManager.getUsername(this),
                    UserInfoManager.getNickname(this),
                    content.getText().toString(),
                    bitmap,
                    UserInfoManager.getProfileBitmap(this));
            adapter.addPost(post);
            prvImg.setVisibility(View.GONE);
            binding.btnImg.setVisibility(View.GONE);
            binding.btnCreate.setVisibility(View.GONE);
            binding.etCreatePost.setText(null);
        });
    }

    @Override
    public void onDeleteClick(int position) {
        adapter.deletePost(position);
    }

    // Method to handle edit click event for a post
    @Override
    public void onEditClick(int position) {
        startEditVisibility();
        prvImg = binding.lstPosts.findViewById(R.id.iv_pic);
        TextView tv = binding.lstPosts.findViewById(R.id.tv_content);
        EditText content = binding.lstPosts.findViewById(R.id.et_content);
        content.setText(tv.getText());

        // Clicking on update post
        binding.lstPosts.findViewById(R.id.btn_update).setOnClickListener(v -> {
            adapter.updatePost(position, content.getText().toString(), bitmap);
            finishEditVisibility();
            setResult(RESULT_OK);
        });

        // Clicking on change image
        binding.lstPosts.findViewById(R.id.btn_changeImg).setOnClickListener(v -> {
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this))
                imageHandler.openChooser();
        });
    }

    private void startEditVisibility() {
        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.VISIBLE);
        bitmap = null;
    }
    private void finishEditVisibility() {
        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_update).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.btn_changeImg).setVisibility(View.GONE);
    }

    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                bitmap = imageHandler.handleActivityResult(requestCode, resultCode, data, prvImg);
                isPicSelected = true;
                binding.btnCreate.setEnabled(isContentValid());
            } else if (requestCode != COMMENTS_REQUEST_CODE){
                // Handle error or cancellation
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
                isPicSelected = false;
            }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

    // Checking if the content for the new post is valid
    private boolean isContentValid() {
        return isPicSelected && content != null && !TextUtils.isEmpty(content.getText());
    }
}