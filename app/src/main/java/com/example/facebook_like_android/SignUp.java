package com.example.facebook_like_android;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
    private static final int PERMISSION_REQUEST_CODE = 2;
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

        binding.btnSignup.setEnabled(!inputError.isEmpty());


        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        binding.btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        binding.btnSignup.setOnClickListener(v -> {
            users.addUser(user);
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        });

        Button selectImg = binding.btnImg;
        imgView = binding.ivPrvImg;

        selectImg.setOnClickListener(v -> openGallery());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        pickImage.launch(galleryIntent);
    }



    ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Uri selectedImageUri;
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                     imgView.setImageURI(selectedImageUri);

                    try {
                        Bitmap bitmap = loadBitmapFromUri(selectedImageUri);
                        imgView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private Bitmap loadBitmapFromUri(Uri uri) throws IOException {
        try (InputStream input = getContentResolver().openInputStream(uri)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // Adjust the sample size if needed
            return BitmapFactory.decodeStream(input, null, options);
        }
    }


}

