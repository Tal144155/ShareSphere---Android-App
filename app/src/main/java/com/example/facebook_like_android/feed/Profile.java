package com.example.facebook_like_android.feed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.UserInfoManager;

public class Profile extends AppCompatActivity implements OnEditClickListener {
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;

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
        TextView tv = binding.lstPosts.findViewById(R.id.tv_content);
        tv.setVisibility(View.GONE);
        EditText content = binding.lstPosts.findViewById(R.id.et_content);
        content.setText(tv.getText());
        content.setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_update).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_update).setOnClickListener(v -> {
            adapter.updatePost(position, content.getText().toString(), -1);
            content.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        });
    }
}