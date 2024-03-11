package com.example.facebook_like_android.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.daos.CommentDao;
import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.feed.CreatePost;
import com.example.facebook_like_android.feed.EditPost;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.BitmapUtils;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;
import com.example.facebook_like_android.viewmodels.FriendsViewModel;
import com.example.facebook_like_android.viewmodels.RequestsViewModel;

/**
 * The Profile activity displays the user's profile information and posts.
 * Users can create, edit, and delete posts from their profile.
 */

public class Profile extends AppCompatActivity implements OnEditClickListener, OnEditClickListener.OnDeleteClickListener {
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();
    private AppDB db;
    private PostDao postDao;
    private CommentDao commentDao;
    private UserDao userDao;
    private FriendsViewModel friendsViewModel;
    private RequestsViewModel requestsViewModel;
    private String username;
    private boolean isMyProfile = false;
    public static int CREATE_POST = 789;
    public static int EDIT_POST = 837;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = AppDB.getDatabase();
        postDao = db.postDao();
        commentDao = db.commentDao();
        userDao = db.userDao();

        friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);

        // Returns the specific username whose profile we are watching
        username = getIntent().getStringExtra("username");
        // Set the proper visibilities accordingly
        if (username.equals(UserInfoManager.getUsername())) {
            myProfile();
            isMyProfile = true;
        }
        else {
            // check if friend
            if (userDao.areFriends(username, UserInfoManager.getUsername()) > 0)
                friendProfile();
            else
                someProfile();
        }

        binding.btnFriends.setOnClickListener(v -> startActivity(new Intent(this, Friends.class)));

        binding.btnRequests.setOnClickListener(v -> {
            Intent i = new Intent(this, Requests.class);
            i.putExtra("isMyProfile", isMyProfile);
            startActivity(i);

        });

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this, postDao);
        lstPosts.setAdapter(adapter);

        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter.setProfileVisibility();
        adapter.setUsername(UserInfoManager.getUsername());
        adapter.setOnEditClickListener(this);
        adapter.setOnDeleteClickListener(this);

        // Set profile information
        UserInfoManager.setProfile(binding.ivProfile);
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        UserInfoManager.setNickname(binding.tvNickname);

        // Set click listeners for buttons
        binding.btnHome.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

//        // Set listener for creating a new post
//        binding.btnCreatePost.setOnClickListener(v -> {
//            binding.btnImg.setVisibility(View.VISIBLE);
//            binding.btnCreate.setVisibility(View.VISIBLE);
//            createPost();
//        });


        // Going to the activity that creates a new post
        binding.btnCreatePost.setOnClickListener(v -> {
            Intent i = new Intent(this, CreatePost.class);
            startActivityForResult(i, CREATE_POST);
        });


    }

    private void myProfile() {
        binding.btnRequests.setVisibility(View.VISIBLE);
        binding.btnFriends.setVisibility(View.VISIBLE);
        binding.btnCreatePost.setVisibility(View.VISIBLE);
        binding.btnFriendRequest.setVisibility(View.GONE);
        binding.lstPosts.setVisibility(View.VISIBLE);
    }

    private void friendProfile() {
        binding.btnRequests.setVisibility(View.GONE);
        binding.btnFriends.setVisibility(View.VISIBLE);
        binding.btnCreatePost.setVisibility(View.GONE);
        binding.btnFriendRequest.setVisibility(View.GONE);
        binding.lstPosts.setVisibility(View.VISIBLE);
    }

    private void someProfile() {
        binding.btnRequests.setVisibility(View.GONE);
        binding.btnFriends.setVisibility(View.GONE);
        binding.btnCreatePost.setVisibility(View.GONE);
        binding.btnFriendRequest.setVisibility(View.VISIBLE);
        binding.lstPosts.setVisibility(View.GONE);
    }

    // Method to handle creating a new post
    private void createPost(String content, Bitmap bitmap) {
//        TextWatcher watcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                binding.btnCreate.setEnabled(isContentValid());
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.btnCreate.setEnabled(isContentValid());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                binding.btnCreate.setEnabled(isContentValid());
//            }
//        };
//        content = binding.etCreatePost;
//        content.addTextChangedListener(watcher);
//
//        // Uploading an image
//        binding.btnImg.setOnClickListener(v -> {
//            prvImg = binding.ivPic;
//            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
//                imageHandler.openChooser();
//                prvImg.setVisibility(View.VISIBLE);
//            }
//        });

        Post post = new Post(UserInfoManager.getUsername(),
                UserInfoManager.getNickname(),
                content, bitmap,
                UserInfoManager.getProfileBitmap());
        adapter.addPost(post);
//        prvImg.setVisibility(View.GONE);
//        binding.btnImg.setVisibility(View.GONE);
//        binding.btnCreate.setVisibility(View.GONE);
    }

    @Override
    public void onDeleteClick(int position) {
        adapter.deletePost(position);
    }

    // Method to handle edit click event for a post
    @Override
    public void onEditClick(int position) {

        Intent i = new Intent(this, EditPost.class);
        TextView tvContent = binding.lstPosts.findViewById(R.id.tv_content);
        ImageView prvImg = binding.lstPosts.findViewById(R.id.iv_pic);
        prvImg.setDrawingCacheEnabled(true);
        prvImg.buildDrawingCache();
        Bitmap image = prvImg.getDrawingCache();
        i.putExtra("content", tvContent.getText())
                .putExtra("pic", BitmapUtils.bitmapToString(image))
                .putExtra("position", position);
        startActivityForResult(i, EDIT_POST);

        /** OLD CODE **/
//        startEditVisibility();
//        prvImg = binding.lstPosts.findViewById(R.id.iv_pic);
//        TextView tv = binding.lstPosts.findViewById(R.id.tv_content);
//        EditText content = binding.lstPosts.findViewById(R.id.et_content);
//        content.setText(tv.getText());
//
//        // Clicking on update post
//        binding.lstPosts.findViewById(R.id.btn_update).setOnClickListener(v -> {
//            adapter.updatePost(position, content.getText().toString(), bitmap);
//            finishEditVisibility();
//            setResult(RESULT_OK);
//        });
//
//        // Clicking on change image
//        binding.lstPosts.findViewById(R.id.btn_changeImg).setOnClickListener(v -> {
//            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this))
//                imageHandler.openChooser();
//        });
    }

    private void editPost(String content, Bitmap pic) {
        adapter.updatePost(getIntent().getIntExtra("position", 0), content, pic);
    }

//    private void startEditVisibility() {
//        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.GONE);
//        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.VISIBLE);
//        bitmap = null;
//    }
//
//    private void finishEditVisibility() {
//        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.GONE);
//        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.VISIBLE);
//        binding.lstPosts.findViewById(R.id.btn_update).setVisibility(View.GONE);
//        binding.lstPosts.findViewById(R.id.btn_changeImg).setVisibility(View.GONE);
//    }

    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String content = data.getStringExtra("content");
            Bitmap pic = BitmapUtils.stringToBitmap(data.getStringExtra("pic"));
            if (requestCode == CREATE_POST) {
                createPost(content, pic);
            } else if (requestCode == EDIT_POST) {
                editPost(content, pic);
            }
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

}