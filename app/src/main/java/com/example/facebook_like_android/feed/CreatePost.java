package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.facebook_like_android.databinding.ActivityCreatePostBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.PermissionsManager;

public class CreatePost extends AppCompatActivity {
    private ActivityCreatePostBinding binding;
    private EditText content;
    private final ImageHandler imageHandler = new ImageHandler(this);
    private ImageView prvImg;
    private Bitmap bitmap;
    private boolean isPicSelected = false;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnHome.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnCreate.setEnabled(isContentValid());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.btnCreate.setEnabled(isContentValid());
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.btnCreate.setEnabled(isContentValid());
            }
        };
        content = binding.etContent;
        content.addTextChangedListener(watcher);

        // Uploading an image
        binding.btnImg.setOnClickListener(v -> {
            prvImg = binding.ivPic;
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
                imageHandler.openChooser();
            }
        });

        // Clicking on Create Post button
        binding.btnCreate.setOnClickListener(v -> {
            setResult(RESULT_OK, new Intent()
                    .putExtra("content", content.getText().toString())
                    .putExtra("pic", bitmap));
            finish();
        });
    }


    // Checking if the content for the new post is valid
    private boolean isContentValid() {
        return isPicSelected && content != null && !TextUtils.isEmpty(content.getText());
    }

    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            bitmap = imageHandler.handleActivityResult(requestCode, resultCode, data, prvImg);
            prvImg.setVisibility(View.VISIBLE);
            isPicSelected = true;
            binding.btnCreate.setEnabled(isContentValid());
        } else {
            // Handle error or cancellation
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
            isPicSelected = false;
        }
    }
}