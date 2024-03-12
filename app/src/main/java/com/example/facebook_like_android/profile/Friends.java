package com.example.facebook_like_android.profile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.FriendsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFriendsBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.viewmodels.FriendsViewModel;

public class Friends extends AppCompatActivity {

    private ActivityFriendsBinding binding;
    private FriendsViewModel viewModel;
    private FriendsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(FriendsViewModel.class);

        RecyclerView lstFriends = binding.lstUsers;
        adapter = new FriendsListAdapter(this, viewModel, getIntent().getStringExtra("username"));
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));

        binding.refreshLayout.setOnRefreshListener(() -> viewModel.reload());

        viewModel.getFriends().observe(this, friends -> {
            adapter.setFriends(friends);
            binding.refreshLayout.setRefreshing(false);
        });

        viewModel.getMessage().observe(this, message -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

    }
}