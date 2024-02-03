package com.example.facebook_like_android.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.databinding.ActivitySignUpBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.users.User;
import com.example.facebook_like_android.users.Users;

import java.io.IOException;
import java.io.InputStream;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;  // View binding instance for the activity
    private final ThemeMode mode = ThemeMode.getInstance();  // ThemeMode singleton instance for theme management
    private User user;  // User object to store registration information
    private final Users users = Users.getInstance();  // Singleton instance for managing user data
    private InputError inputError;  // Object to handle input validation errors
    private ImageView imgView;  // ImageView to display the selected profile photo
    // Declare a member variable to store the selected image URI
    private Uri photo;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Enable or disable the signup button based on input validity
            binding.btnSignup.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Validate password and confirm password fields after text changes
            if (!inputError.isPwdValid())
                binding.etPasswordSu.setError("Invalid password!");
            if (!inputError.arePwdSame())
                binding.etConfirmPasswordSu.setError("Passwords must match!");
            // Enable or disable the signup button based on overall input validity
            binding.btnSignup.setEnabled(inputError.isValid());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize User object for storing registration information
        user = new User(findViewById(R.id.et_username), findViewById(R.id.et_password_su), findViewById(R.id.et_confirmPassword_su), findViewById(R.id.et_nickname));
        // Initialize InputError object for handling input validation
        inputError = new InputError(user);

        // Add TextWatcher to input fields for dynamic validation
        binding.etUsername.addTextChangedListener(watcher);
        binding.etPasswordSu.addTextChangedListener(watcher);
        binding.etConfirmPasswordSu.addTextChangedListener(watcher);
        binding.etNickname.addTextChangedListener(watcher);

        // Disable the signup button upon entering
        binding.btnSignup.setEnabled(inputError.isValid());

        // Set click listeners for buttons
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));
        binding.btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(v -> {
            // Set the selected profile photo to the User object
            user.setProfilePhoto(photo);
            // Add the user to the Users collection
            users.addUser(user);
            // Navigate to the login screen
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        Button selectImg = binding.btnImg;
        imgView = binding.ivPrvImg;

        // Set click listener for the image selection button
        selectImg.setOnClickListener(v -> openGallery());
    }

    // Method to open the gallery for image selection
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        pickImage.launch(galleryIntent);
    }

    // Activity result launcher for handling image pick results
    ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::handleImagePickResult
    );

    // Method to handle the result of image pick operation
    private void handleImagePickResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            photo = result.getData().getData();  // Automatically save the image URI to 'photo'
            showSelectedImage(photo);
        } else {
            showImagePickFailure();
        }
    }

    // Method to display the selected image in the ImageView
    private void showSelectedImage(Uri selectedImageUri) {
        imgView.setImageURI(selectedImageUri);
        binding.btnSignup.setEnabled(inputError.isValid());

        try {
            // Load the selected image into a Bitmap and set it to the ImageView
            Bitmap bitmap = loadBitmapFromUri(selectedImageUri);
            imgView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to display a failure message when image pick is unsuccessful
    private void showImagePickFailure() {
        Toast.makeText(this, "You must pick a photo!", Toast.LENGTH_SHORT).show();
        binding.btnSignup.setEnabled(false);
    }

    // Method to load a Bitmap from a given URI
    private Bitmap loadBitmapFromUri(Uri uri) throws IOException {
        try (InputStream input = getContentResolver().openInputStream(uri)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // Adjust the sample size if needed
            return BitmapFactory.decodeStream(input, null, options);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Clear input fields and image preview on pause to ensure a clean state when returning to the activity
        binding.etUsername.setText(null);
        binding.etPasswordSu.setText(null);
        binding.etConfirmPasswordSu.setText(null);
        binding.etNickname.setText(null);
        imgView.setImageBitmap(null);
    }
}
