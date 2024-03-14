package com.example.facebook_like_android.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.databinding.ActivitySignUpBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.users.User;
import com.example.facebook_like_android.users.Users;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.viewmodels.SignUpViewModel;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;  // View binding instance for the activity
    private final ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management
    private User user;  // User object to store registration information
    private final Users users = Users.getInstance();  // Singleton instance for managing user data
    private InputError inputError;  // Object to handle input validation errors
    private ImageView imgView;  // ImageView to display the selected profile photo
    private Bitmap bitmap = null;
    private final ImageHandler imageHandler = new ImageHandler(this);
    private SignUpViewModel viewModel;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Enable or disable the signup button based on input validity
            binding.btnSignup.setEnabled(!inputError.checkEmpty() && bitmap != null);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Validate password and confirm password fields after text changes
            if (!inputError.isPwdValid())
                binding.etPasswordSu.setError("Invalid password!");
            if (!inputError.arePwdSame())
                binding.etConfirmPasswordSu.setError("Passwords must match!");
            // Enable or disable the signup button based on overall input validity
            binding.btnSignup.setEnabled(inputError.isValid() && bitmap != null);
        }
    };

    TextWatcher usernameUnique = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            viewModel.unique(binding.etUsername.getText().toString());
            // Enable or disable the signup button based on overall input validity
            binding.btnSignup.setEnabled(inputError.isValid() && bitmap != null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivPrvImg.setVisibility(View.GONE);

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        // Initialize User object for storing registration information
        user = new User(findViewById(R.id.et_username), findViewById(R.id.et_password_su),
                findViewById(R.id.et_confirmPassword_su), findViewById(R.id.et_firstname),
                findViewById(R.id.et_lastname));
        // Initialize InputError object for handling input validation
        inputError = new InputError(user);

        // Add TextWatcher to input fields for dynamic validation
        binding.etUsername.addTextChangedListener(watcher);
        binding.etPasswordSu.addTextChangedListener(watcher);
        binding.etConfirmPasswordSu.addTextChangedListener(watcher);
        binding.etFirstname.addTextChangedListener(watcher);
        binding.etLastname.addTextChangedListener(watcher);
        binding.etUsername.addTextChangedListener(usernameUnique);

        viewModel.getAdded().observe(this, isAdded -> {
            if (isAdded) {
                // Set the selected profile photo to the User object
                user.setProfilePhoto(bitmap);
                // Navigate to the login screen
                startActivity(new Intent(getApplicationContext(), Login.class));
            } else {
                Toast.makeText(getApplicationContext(), "Failed to sign up!", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getUnique().observe(this, isUnique -> inputError.setUnique(isUnique));

        // Set click listeners for buttons
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
        binding.btnLogin.setOnClickListener(v -> {
            finish();
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(v -> {
//            // Set the selected profile photo to the User object
//            user.setProfilePhoto(bitmap);
//            // Add the user to the Users collection
//            users.addUser(user);
//            // Navigate to the login screen
//            Intent i = new Intent(this, Login.class);
//            startActivity(i);
            viewModel.signup(binding.etUsername.getText().toString(), binding.etPasswordSu.getText().toString(),
                    binding.etFirstname.getText().toString(), binding.etLastname.getText().toString(),
                    Base64Utils.encodeBitmapToBase64(bitmap));
        });

        imgView = binding.ivPrvImg;

        // Set click listener for the image selection button
        binding.btnImg.setOnClickListener(v -> {
            imageHandler.openChooser();
        });

        CircularOutlineUtil.applyCircularOutline(binding.ivPrvImg);
    }

    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            bitmap = imageHandler.handleActivityResult(requestCode, resultCode, data, binding.ivPrvImg);
            binding.btnSignup.setEnabled(inputError.isValid());
            binding.ivPrvImg.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
            binding.btnSignup.setEnabled(bitmap != null && inputError.isValid());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Clear input fields and image preview on pause to ensure a clean state when returning to the activity
        binding.etUsername.setText(null);
        binding.etPasswordSu.setText(null);
        binding.etConfirmPasswordSu.setText(null);
        binding.etFirstname.setText(null);
        binding.etLastname.setText(null);
        imgView.setImageBitmap(null);
    }



}
