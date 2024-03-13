package com.example.facebook_like_android.profile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.RequestsListAdapter;
import com.example.facebook_like_android.databinding.ActivityRequestsBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.viewmodels.FriendsViewModel;
import com.example.facebook_like_android.viewmodels.RequestsViewModel;

public class Requests extends AppCompatActivity {
    private ActivityRequestsBinding binding;
    private RequestsViewModel viewModel;
    private FriendsViewModel friendsViewModel;
    private RequestsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRequestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
        friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);

        RecyclerView lstFriends = binding.lstUsers;
        adapter = new RequestsListAdapter(this, viewModel, friendsViewModel);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));


        viewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged)
                viewModel.reload();
        });

        friendsViewModel.hasChanged().observe(this, hasChanged -> {
            if (hasChanged)
                viewModel.reload();
        });

        binding.refreshLayout.setOnRefreshListener(() -> viewModel.reload());

        viewModel.getRequests().observe(this, requests -> {
            if (requests != null) {
                adapter.setRequests(requests);
                binding.refreshLayout.setRefreshing(false);
            }
        });

        viewModel.getMessage().observe(this, message -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}