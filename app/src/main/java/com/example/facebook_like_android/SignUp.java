package com.example.facebook_like_android;

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

import com.example.facebook_like_android.databinding.ActivitySignUpBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.users.User;
import com.example.facebook_like_android.users.Users;

import java.io.IOException;
import java.io.InputStream;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private User user;
    private final Users users = Users.getInstance();
    private InputError inputError;
    private ImageView imgView;
    // Declare a member variable to store the selected image URI
    private Uri photo;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // check whether both the fields are empty or not
            binding.btnSignup.setEnabled(!inputError.checkEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean valid = true;
            if (!inputError.isUnique()) {
                binding.etUsername.setError("Username must be unique!");
                valid = false;
            }
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
            binding.btnSignup.setEnabled(inputError.isValid());
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

        binding.btnSignup.setEnabled(inputError.isValid());


        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        binding.btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(v -> {
            user.setProfilePhoto(photo);
            users.addUser(user);
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        Button selectImg = binding.btnImg;
        imgView = binding.ivPrvImg;

        selectImg.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        pickImage.launch(galleryIntent);
    }



    ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::handleImagePickResult
    );

    private void handleImagePickResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            photo = result.getData().getData();  // Automatically save the image URI to 'photo'
            showSelectedImage(photo);
        } else {
            showImagePickFailure();
        }
    }

    private void showSelectedImage(Uri selectedImageUri) {
        imgView.setImageURI(selectedImageUri);
        binding.btnSignup.setEnabled(inputError.isValid());

        try {
            Bitmap bitmap = loadBitmapFromUri(selectedImageUri);
            imgView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showImagePickFailure() {
        Toast.makeText(this, "You must pick a photo!", Toast.LENGTH_SHORT).show();
        binding.btnSignup.setEnabled(false);
    }

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
        // The app is not persistent
        binding.etUsername.setText(null);
        binding.etPasswordSu.setText(null);
        binding.etConfirmPasswordSu.setText(null);
        binding.etNickname.setText(null);
        binding.ivPrvImg.setImageBitmap(null);
    }
}

