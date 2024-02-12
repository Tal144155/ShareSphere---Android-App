package com.example.facebook_like_android.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivityLoginBinding;
import com.example.facebook_like_android.feed.Feed;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.users.Users;

import java.util.Map;

public class Login extends AppCompatActivity {
    private Users users = Users.getInstance();  // Singleton instance for managing user data
    private ActivityLoginBinding binding;  // View binding instance for the activity
    private final ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management
    private InputError inputError;  // Object to handle input validation errors
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

        binding.btnLogin.setOnClickListener(v -> {
            // Check if the provided username and password are valid
            if (users.isSigned(binding.etUsername, binding.etPassword)) {
                saveCurrentUser();
                Intent i = new Intent(this, Feed.class);
                startActivity(i);
            } else {
                // Display a toast message for invalid credentials
                Toast toast = Toast.makeText(this, "Username or password invalid!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Clear input fields on pause to ensure a clean state when returning to the activity
        binding.etUsername.setText(null);
        binding.etPassword.setText(null);
    }

    private void saveCurrentUser() {
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Map<Users.FIELD, String> user = users.getUserByUsername(binding.etUsername.getText().toString());
        editor.putString("username", user.get(Users.FIELD.Username));
        editor.putString("nickname", user.get(Users.FIELD.Nickname));
        editor.putString("profile", user.get(Users.FIELD.ProfilePhoto));
        editor.apply();
    }
}
