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

import com.example.facebook_like_android.databinding.ActivityEditPostBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;

public class EditPost extends AppCompatActivity {
    private ActivityEditPostBinding binding;
    private EditText content;
    private final ImageHandler imageHandler = new ImageHandler(this);
    private ImageView prvImg;
    private Bitmap bitmap;
    private boolean isPicSelected = false;
    private final ThemeMode mode = ThemeMode.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnHome.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        // Setting the original post's data
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        binding.ivProfile.setImageBitmap(UserInfoManager.getProfileBitmap(this));
        binding.tvAuthor.setText(UserInfoManager.getNickname(this));
        binding.etContent.setText(getIntent().getStringExtra("content"));
        binding.ivPic.setImageBitmap(getIntent().getParcelableExtra("pic"));


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnUpdate.setEnabled(isContentValid());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.btnUpdate.setEnabled(isContentValid());
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.btnUpdate.setEnabled(isContentValid());
            }
        };
        content = binding.etContent;
        content.addTextChangedListener(watcher);

        // Uploading an image
        binding.btnChangeImg.setOnClickListener(v -> {
            prvImg = binding.ivPic;
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
                imageHandler.openChooser();
            }
        });

        // Clicking on Update Post button
        binding.btnUpdate.setOnClickListener(v -> {
            setResult(RESULT_OK, new Intent()
                    .putExtra("content", content.getText().toString())
                    .putExtra("pic", bitmap)
                    .putExtra("position", getIntent().getIntExtra("position", 0)));
            finish();
        });
    }


    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            bitmap = imageHandler.handleActivityResult(requestCode, resultCode, data, prvImg);
            prvImg.setVisibility(View.VISIBLE);
            isPicSelected = true;
            binding.btnUpdate.setEnabled(isContentValid());
        } else {
            // Handle error or cancellation
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
            isPicSelected = false;
        }
    }

    // Checking if the content for the updated post is valid
    private boolean isContentValid() {
        return isPicSelected && content != null && !TextUtils.isEmpty(content.getText());
    }
}