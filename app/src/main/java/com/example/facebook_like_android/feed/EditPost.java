package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.facebook_like_android.databinding.ActivityEditPostBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.PermissionsManager;
import com.example.facebook_like_android.utils.UserInfoManager;
import com.example.facebook_like_android.viewmodels.ProfileViewModel;

public class EditPost extends AppCompatActivity {
    private ActivityEditPostBinding binding;
    private EditText content;
    private final ImageHandler imageHandler = new ImageHandler(this);
    private ImageView prvImg;
    private Bitmap bitmap;
    private boolean isPicSelected = true;
    private final ThemeMode mode = ThemeMode.getInstance();
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnBack.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        // Setting the original post's data
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        bitmap = UserInfoManager.getProfileBitmap();
        binding.ivProfile.setImageBitmap(bitmap);
        binding.tvAuthor.setText(UserInfoManager.getNickname());
        binding.etContent.setText(getIntent().getStringExtra("content"));
        binding.ivPic.setImageBitmap(Base64Utils.decodeBase64ToBitmap(getIntent().getStringExtra("pic")));

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.setProfileRepository(UserInfoManager.getUsername());

        Log.d("DEBUG", "inside edit user: " + getIntent().getStringExtra("postId"));


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
            profileViewModel.confirmLinks(content.getText().toString());
//            List<String> links = LinkExtractor.extractLinks(content.getText().toString());
//
//            if (links != null) {
//                // make async request
//                binding.progressBar.setVisibility(View.VISIBLE);
//                profileViewModel.confirmLinks(links);
//            } else {
//                setResult(RESULT_OK, new Intent()
//                        .putExtra("content", content.getText().toString())
//                        .putExtra("pic", Base64Utils.encodeBitmapToBase64(bitmap))
//                        .putExtra("postId", getIntent().getStringExtra("postId")));
//                finish();
//            }
        });

        profileViewModel.isValid().observe(this, valid -> {
            binding.progressBar.setVisibility(View.GONE);
            if (valid) {
                setResult(RESULT_OK, new Intent()
                        .putExtra("content", content.getText().toString())
                        .putExtra("pic", Base64Utils.encodeBitmapToBase64(bitmap))
                        .putExtra("postId", getIntent().getStringExtra("postId")));
                finish();
            }
        });

        profileViewModel.getMessage().observe(this, msg -> Toast.makeText(this, msg, Toast.LENGTH_LONG).show());
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