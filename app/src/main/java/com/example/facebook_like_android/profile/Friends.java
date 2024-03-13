package com.example.facebook_like_android.profile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.FriendsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFriendsBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.viewmodels.FriendsViewModel;

public class Friends extends AppCompatActivity {

    private ActivityFriendsBinding binding;
    private FriendsViewModel viewModel;
    private FriendsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(FriendsViewModel.class);

        RecyclerView lstFriends = binding.lstUsers;
        adapter = new FriendsListAdapter(this, viewModel);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));

        username = getIntent().getStringExtra("username");

        binding.refreshLayout.setOnRefreshListener(() -> {
            viewModel.reload(username);
        });

        if (getIntent().getBooleanExtra("isMyProfile", false))
            adapter.myProfile();

        viewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged)
                viewModel.reload(username);
        });

        viewModel.getFriends().observe(this, friends -> {
            adapter.setFriends(friends);
            binding.refreshLayout.setRefreshing(false);
        });

        viewModel.getMessage().observe(this, message -> {
            binding.refreshLayout.setRefreshing(false);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

    }
}