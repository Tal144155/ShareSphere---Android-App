package com.example.facebook_like_android.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.feed.CreatePost;
import com.example.facebook_like_android.feed.EditPost;
import com.example.facebook_like_android.register.Login;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;
import com.example.facebook_like_android.viewmodels.FeedViewModel;
import com.example.facebook_like_android.viewmodels.FriendsViewModel;
import com.example.facebook_like_android.viewmodels.PostsViewModel;
import com.example.facebook_like_android.viewmodels.ProfileViewModel;
import com.example.facebook_like_android.viewmodels.RequestsViewModel;
import com.example.facebook_like_android.viewmodels.UsersViewModel;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * The Profile activity displays the user's profile information and posts.
 * Users can create, update, and delete posts from their profile.
 */

public class Profile extends AppCompatActivity implements OnEditClickListener, OnEditClickListener.OnDeleteClickListener, OnEditClickListener.OnLikeClickListener {
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();
    private FriendsViewModel friendsViewModel;
    private RequestsViewModel requestsViewModel;
    private ProfileViewModel profileViewModel;
    private UsersViewModel usersViewModel;
    private FeedViewModel feedViewModel;
    private PostsViewModel postsViewModel;
    private String username;
    private String profile;
    private String nickname;
    private String firstname;
    private String lastname;
    private boolean isMyProfile = false;
    public static int CREATE_POST = 789;
    public static int EDIT_POST = 837;
    public static int EDIT_USER = 316;
    private String postId;
    private ImageButton like;
    private int position;
    private TextView likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // enlarging field size
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 50 * 1024 * 1024); // the new size is 100MB
        } catch (Exception e) {
            Log.d("DEBUG", e.getLocalizedMessage());
        }

        // Returns the specific username whose profile we are watching
        username = getIntent().getStringExtra("username");
        profile = getIntent().getStringExtra("profile");
        firstname = getIntent().getStringExtra("firstname");
        lastname = getIntent().getStringExtra("lastname");
        nickname = firstname + " " + lastname;
        friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.setProfileRepository(username);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);


        binding.btnDelete.setOnClickListener(v -> {
            usersViewModel.delete();
        });

        usersViewModel.hasRemoved().observe(this, hasRemoved -> {
            feedViewModel.reload();
            // Log out
            Intent i = new Intent(this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            startActivity(i);
        });

        binding.btnFriendRequest.setOnClickListener(v -> {
            requestsViewModel.add(username);
        });

        requestsViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged) {
                // change btn
                binding.btnFriendRequest.setVisibility(View.GONE);
                binding.btnUnsend.setVisibility(View.VISIBLE);
            }
        });

        binding.btnUnsend.setOnClickListener(v -> requestsViewModel.delete(username));

        requestsViewModel.hasRemoved().observe(this, hasRemoved -> {
            if (hasRemoved) {
                // change btn
                binding.btnUnsend.setVisibility(View.GONE);
                binding.btnFriendRequest.setVisibility(View.VISIBLE);
            }
        });

        friendsViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged) {
                // Set the visibility accordingly and refresh the profile
                friendProfile();
                profileViewModel.reload();
            }
        });

        binding.btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(this, EditUser.class);
            i.putExtra("firstname", firstname)
                    .putExtra("lastname", lastname)
                    .putExtra("profile", profile);
            startActivityForResult(i, EDIT_USER);
        });


        // Set the proper visibilities accordingly
        friendsViewModel.checkFriends().observe(this, areFriends -> {
            if (areFriends != null) {
                if (username.equals(UserInfoManager.getUsername())) {
                    myProfile();
                    isMyProfile = true;
                } else {
                    if (areFriends) {
                        friendProfile();
                    } else {
                        someProfile();
                    }
                }
            }
        });

        friendsViewModel.checkIfFriends(username, UserInfoManager.getUsername());

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this, username);
        lstPosts.setAdapter(adapter);


        profileViewModel.getMessage().observe(this, msg -> {
            binding.refreshLayout.setRefreshing(false);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        profileViewModel.getPosts().observe(this, posts -> {
            binding.refreshLayout.setRefreshing(false);
            adapter.setPosts(posts);
        });

        profileViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged) {
                profileViewModel.reload();
                feedViewModel.reload();
            }
        });


        usersViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged) {
                binding.ivProfile.setImageBitmap(Base64Utils.decodeBase64ToBitmap(profile));
                String nickname = firstname + " " + lastname;
                binding.tvNickname.setText(nickname);
                profileViewModel.reload();
                feedViewModel.reload();
            }

        });


        postsViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged)
                postsViewModel.checkLiked(postId);
        });

        postsViewModel.isLiked().observe(this, isLiked -> adapter.setLiked(isLiked, position, like, likes));

        binding.refreshLayout.setOnRefreshListener(() -> profileViewModel.reload());


        binding.btnFriends.setOnClickListener(v -> {
            Intent i = new Intent(this, Friends.class);
            i.putExtra("username", username)
                    .putExtra("isMyProfile", isMyProfile);
            startActivity(i);
        });

        binding.btnRequests.setOnClickListener(v -> {
            Intent i = new Intent(this, Requests.class);
            i.putExtra("isMyProfile", isMyProfile)
                    .putExtra("username", username);
            startActivity(i);
        });


        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter.setProfileVisibility(profileViewModel, username);
        adapter.setUsername(UserInfoManager.getUsername());
        adapter.setOnEditClickListener(this);
        adapter.setOnDeleteClickListener(this);
        adapter.setOnLikeClickListener(this);

        // Set profile information
        //UserInfoManager.setProfile(binding.ivProfile);
        setInfo();
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        //UserInfoManager.setNickname(binding.tvNickname);

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

    private void setInfo() {
        binding.ivProfile.setImageBitmap(Base64Utils.decodeBase64ToBitmap(profile));
        binding.tvNickname.setText(nickname);
    }

    private void myProfile() {
        binding.btnRequests.setVisibility(View.VISIBLE);
        binding.btnFriends.setVisibility(View.VISIBLE);
        binding.btnCreatePost.setVisibility(View.VISIBLE);
        binding.btnFriendRequest.setVisibility(View.GONE);
        binding.lstPosts.setVisibility(View.VISIBLE);
        binding.btnEdit.setVisibility(View.VISIBLE);
        binding.btnUnsend.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.VISIBLE);
    }

    private void friendProfile() {
        binding.btnRequests.setVisibility(View.GONE);
        binding.btnFriends.setVisibility(View.VISIBLE);
        binding.btnCreatePost.setVisibility(View.GONE);
        binding.btnFriendRequest.setVisibility(View.GONE);
        binding.lstPosts.setVisibility(View.VISIBLE);
        binding.btnEdit.setVisibility(View.GONE);
        binding.btnUnsend.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.GONE);
    }

    private void someProfile() {
        binding.btnRequests.setVisibility(View.GONE);
        binding.btnFriends.setVisibility(View.GONE);
        binding.btnCreatePost.setVisibility(View.GONE);
        binding.btnFriendRequest.setVisibility(View.VISIBLE);
        binding.lstPosts.setVisibility(View.GONE);
        binding.btnEdit.setVisibility(View.GONE);
        binding.btnUnsend.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.GONE);
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

        profileViewModel.add(UserInfoManager.getUsername(), UserInfoManager.getFirstName(), UserInfoManager.getLastName(),
                UserInfoManager.getProfile(), Base64Utils.encodeBitmapToBase64(bitmap), content, String.valueOf(Calendar.getInstance().getTime()));
        //adapter.addPost(post);
//        prvImg.setVisibility(View.GONE);
//        binding.btnImg.setVisibility(View.GONE);
//        binding.btnCreate.setVisibility(View.GONE);
    }

    @Override
    public void onDeleteClick(Post post) {
        profileViewModel.delete(post.getPostId());
        //adapter.deletePost(position);
    }

    // Method to handle update click event for a post
    @Override
    public void onEditClick(String postId) {

        Intent i = new Intent(this, EditPost.class);
        TextView tvContent = binding.lstPosts.findViewById(R.id.tv_content);
        ImageView prvImg = binding.lstPosts.findViewById(R.id.iv_pic);
        prvImg.setDrawingCacheEnabled(true);
        prvImg.buildDrawingCache();
        Bitmap image = prvImg.getDrawingCache();
        i.putExtra("content", tvContent.getText())
                .putExtra("pic", Base64Utils.encodeBitmapToBase64(image))
                .putExtra("postId", postId);
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
        //adapter.updatePost(getIntent().getIntExtra("position", 0), content, pic);
        profileViewModel.update(getIntent().getStringExtra("postId"), content, Base64Utils.encodeBitmapToBase64(pic));
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
            Bitmap pic = Base64Utils.decodeBase64ToBitmap(data.getStringExtra("pic"));
            if (requestCode == CREATE_POST) {
                createPost(content, pic);
            } else if (requestCode == EDIT_POST) {
                editPost(content, pic);
            } else if (requestCode == EDIT_USER) {
                firstname = data.getStringExtra("firstname");
                lastname = data.getStringExtra("lastname");
                profile = data.getStringExtra("pic");
                editUser(firstname, lastname, profile);
            }
        }
    }

    private void editUser(String firstname, String lastname, String profile) {
        usersViewModel.update(firstname, lastname, profile);
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

    @Override
    public void onLikeClick(String postId, int position, ImageButton like, TextView likes) {
        this.position = position;
        this.likes = likes;
        this.like = like;
        this.postId = postId;
        postsViewModel.like(postId);
    }
}