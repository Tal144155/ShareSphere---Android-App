package com.example.facebook_like_android.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.facebook_like_android.databinding.ActivityLoginBinding;
import com.example.facebook_like_android.feed.Feed;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.users.Users;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.viewmodels.LoginViewModel;

import java.util.Base64;
import java.util.Map;

public class Login extends AppCompatActivity {
    private final Users users = Users.getInstance();  // Singleton instance for managing user data
    private ActivityLoginBinding binding;  // View binding instance for the activity
    private final ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management
    private InputError inputError;  // Object to handle input validation errors
    private LoginViewModel viewModel;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Enable or disable the login button based on input validity
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Enable or disable the login button based on input validity
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize InputError object for handling input validation
        inputError = new InputError(binding.etUsername, binding.etPassword);

        // Add TextWatcher to input fields for dynamic validation
        binding.etUsername.addTextChangedListener(watcher);
        binding.etPassword.addTextChangedListener(watcher);

        // Set click listeners for buttons
        binding.btnSignup.setOnClickListener(v -> {
            finish();
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });

        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.getLoginResult().observe(this, token -> {
            Log.d("DEBUG", "the token: " + token);
            SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.apply();
            viewModel.getUser(binding.etUsername.getText().toString(), token);
        });

        viewModel.getUserInfo().observe(this, user -> {
            if (user != null) {
                Log.d("DEBUG", "got user back!");
                SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", user.getUsername());
                String nickname = user.getFirstname() + " " + user.getLastname();
                editor.putString("nickname", nickname);
                editor.putString("firstname", user.getFirstname());
                editor.putString("lastname", user.getLastname());
                editor.putString("profile", user.getProfile());
                editor.apply();
                startActivity(new Intent(this, Feed.class));
            }
        });

        viewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
            if (!isLoggedIn) {
                Toast.makeText(getApplicationContext(), "Username or password invalid!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("DEBUG", "login success");
            }
        }
        );

        binding.btnLogin.setOnClickListener(v -> {
            viewModel.login(binding.etUsername.getText().toString(), binding.etPassword.getText().toString());


//            // Check if the provided username and password are valid
//            if (users.isSigned(binding.etUsername, binding.etPassword)) {
//                saveCurrentUser();
//                Intent i = new Intent(this, Feed.class);
//                startActivity(i);
//            } else {
//                // Display a toast message for invalid credentials
//                Toast toast = Toast.makeText(this, "Username or password invalid!", Toast.LENGTH_SHORT);
//                toast.show();
//            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Clear input fields on pause to ensure a clean state when returning to the activity
        binding.etUsername.setText(null);
        binding.etPassword.setText(null);
    }

    // Saving the logged in user's info for future use
    private void saveCurrentUser() {
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Map<Users.FIELD, String> user = users.getUserByUsername(binding.etUsername.getText().toString());
        editor.putString("username", user.get(Users.FIELD.Username));
        String nickname = user.get(Users.FIELD.FirstName) + " " + user.get(Users.FIELD.LastName);
        editor.putString("nickname", nickname);
        editor.putString("firstname", user.get(Users.FIELD.FirstName));
        editor.putString("lastname", user.get(Users.FIELD.LastName));
        editor.putString("profile", user.get(Users.FIELD.ProfilePhoto));
        editor.apply();
    }
}
