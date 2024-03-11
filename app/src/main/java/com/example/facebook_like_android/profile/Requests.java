package com.example.facebook_like_android.profile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.RequestsListAdapter;
import com.example.facebook_like_android.databinding.ActivityRequestsBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.viewmodels.RequestsViewModel;

public class Requests extends AppCompatActivity {
    private ActivityRequestsBinding binding;
    private RequestsViewModel viewModel;
    private RequestsListAdapter adapter;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityRequestsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(RequestsViewModel.class);

        RecyclerView lstFriends = binding.lstUsers;
        adapter = new RequestsListAdapter(this, viewModel);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getBooleanExtra("isMyProfile", false))
            adapter.myProfile();

        binding.refreshLayout.setOnRefreshListener(() -> viewModel.reload());

        viewModel.getRequests().observe(this, requests -> {
            adapter.setRequests(requests);
            binding.refreshLayout.setRefreshing(false);
        });

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
    }
}