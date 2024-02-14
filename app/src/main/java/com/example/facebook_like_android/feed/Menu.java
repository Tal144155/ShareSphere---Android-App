package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.register.Login;
import com.example.facebook_like_android.databinding.ActivityMenuBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.UserInfoManager;

public class Menu extends AppCompatActivity {
    private ActivityMenuBinding binding;  // View binding instance for the activity
    private ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnLogout.setOnClickListener(v ->  {
            Intent i = new Intent(this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
        binding.btnHome.setOnClickListener(v -> finish());
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        // Set profile image and nickname
        UserInfoManager.setProfile(this, binding.btnProfile);
        UserInfoManager.setNickname(this, binding.tvNickname);
        CircularOutlineUtil.applyCircularOutline(binding.btnProfile);
    }
}
