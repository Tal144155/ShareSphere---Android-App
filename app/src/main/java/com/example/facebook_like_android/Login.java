package com.example.facebook_like_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivityLoginBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.users.Users;

public class Login extends AppCompatActivity {
    private Users users = Users.getInstance();
    private ActivityLoginBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private InputError inputError;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            binding.btnLogin.setEnabled(!inputError.checkEmpty());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inputError = new InputError(binding.etUsername, binding.etPassword);

        binding.etUsername.addTextChangedListener(watcher);
        binding.etPassword.addTextChangedListener(watcher);

        binding.btnLogin.setEnabled(!inputError.isEmpty());

        binding.btnSignup.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });

        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        binding.btnLogin.setOnClickListener(v -> {
            // TODO: remove that line!
            startActivity(new Intent(this, Feed.class));
            if (users.isSigned(binding.etUsername, binding.etPassword)) {
                Intent i = new Intent(this, Feed.class);
                startActivity(i);
            } else {
                Toast toast = Toast.makeText(this, "Username or password invalid!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.etUsername.setText(null);
        binding.etPassword.setText(null);
    }
}