package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;

public class Profile extends AppCompatActivity implements OnEditClickListener {
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;
    private final ImageHandler imageHandler = new ImageHandler(this);
    Uri picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            // Initialize RecyclerView for displaying posts
            RecyclerView lstPosts = binding.lstPosts;
            adapter = new PostsListAdapter(this);
            lstPosts.setAdapter(adapter);
            lstPosts.setLayoutManager(new LinearLayoutManager(this));
            adapter.setProfileVisibility();
            SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
            adapter.setUsername(preferences.getString("username", ""));

            adapter.setOnEditClickListener(this);
        }

        UserInfoManager.setProfile(this, binding.ivProfile);
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        UserInfoManager.setNickname(this, binding.tvNickname);

        binding.btnHome.setOnClickListener(v -> finish());

    }


    @Override
    public void onEditClick(int position) {
        startEditVisibility();
        TextView tv = binding.lstPosts.findViewById(R.id.tv_content);
        EditText content = binding.lstPosts.findViewById(R.id.et_content);
        content.setText(tv.getText());

        binding.lstPosts.findViewById(R.id.btn_update).setOnClickListener(v -> {
            adapter.updatePost(position, content.getText().toString(), picture);
            finishEditVisibility();
            setResult(RESULT_OK);
        });

        binding.lstPosts.findViewById(R.id.btn_changeImg).setOnClickListener(v -> {
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this))
                imageHandler.openChooser();
        });
    }

    private void startEditVisibility() {
        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.VISIBLE);
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
            picture = imageHandler.handleActivityResult(requestCode, resultCode, data,
                    binding.lstPosts.findViewById(R.id.iv_pic));
            Log.d("DEBUG", "Picture Uri: " + picture); // Log the Uri to verify its correctness
        } else {
            // Handle error or cancellation
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("DEBUG", "onRequestPermissionsResult: requestCode=" + requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.onRequestPermissionsResult(requestCode, grantResults, this);
    }

}