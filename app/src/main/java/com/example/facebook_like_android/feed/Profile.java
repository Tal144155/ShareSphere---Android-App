package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.facebook_like_android.utils.UserInfoManager;

public class Profile extends AppCompatActivity implements OnEditClickListener {
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;
    private ImageHandler imageHandler = new ImageHandler(this);
    Uri picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter.setProfileVisibility();
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        adapter.setUsername(preferences.getString("username", ""));


        UserInfoManager.setProfile(this, binding.ivProfile);
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        UserInfoManager.setNickname(this, binding.tvNickname);

        adapter.setOnEditClickListener(this);

    }


    @Override
    public void onEditClick(int position) {
        setVisibility();
        TextView tv = binding.lstPosts.findViewById(R.id.tv_content);
        EditText content = binding.lstPosts.findViewById(R.id.et_content);
        content.setText(tv.getText());

        binding.lstPosts.findViewById(R.id.btn_update).setOnClickListener(v -> {
            adapter.updatePost(position, content.getText().toString(), null);
            content.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        });

        binding.lstPosts.findViewById(R.id.btn_changeImg).setOnClickListener(v -> imageHandler.openChooser());
    }

    private void setVisibility() {
        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_update).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_changeImg).setVisibility(View.VISIBLE);
    }

    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picture = imageHandler.handleActivityResult(requestCode, resultCode, data,
                binding.lstPosts.findViewById(R.id.iv_pic));
    }
}