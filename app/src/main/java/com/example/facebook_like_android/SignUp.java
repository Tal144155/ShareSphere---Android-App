package com.example.facebook_like_android;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivitySignUpBinding;
import com.example.facebook_like_android.style.ThemeMode;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private User user;
    private InputError inputError;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            binding.btnSignup.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // check whether both the fields are empty or not
            binding.btnSignup.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean valid = true;
            if (!inputError.isPwdValid()) {
                binding.etPasswordSu.setError("Invalid password!");
                valid = false;
            }
            if (!inputError.arePwdSame()){
                binding.etConfirmPasswordSu.setError("Passwords must match!");
                valid = false;
            }

            // You can sign up only if the info is correct
            binding.btnSignup.setEnabled(valid);
            binding.btnSignup.setEnabled(!inputError.checkEmpty());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User(findViewById(R.id.et_username), findViewById(R.id.et_password_su), findViewById(R.id.et_confirmPassword_su), findViewById(R.id.et_nickname));
        inputError = new InputError(user);

        // Setting textChange listener to attributes
        binding.etUsername.addTextChangedListener(watcher);
        binding.etPasswordSu.addTextChangedListener(watcher);
        binding.etConfirmPasswordSu.addTextChangedListener(watcher);
        binding.etNickname.addTextChangedListener(watcher);

        binding.btnSignup.setEnabled(!inputError.checkEmpty());

        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        binding.btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(v -> {
            Intent i = new Intent(this, Feed.class);
            startActivity(i);
        });
    }
}