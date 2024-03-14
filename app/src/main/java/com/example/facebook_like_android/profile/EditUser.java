package com.example.facebook_like_android.profile;

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

import com.example.facebook_like_android.databinding.ActivityEditUserBinding;
import com.example.facebook_like_android.style.ThemeMode;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.PermissionsManager;

public class EditUser extends AppCompatActivity {

    private ActivityEditUserBinding binding;
    private ImageHandler imageHandler = new ImageHandler(this);
    private ImageView prvImg;
    private Bitmap bitmap;
    private boolean isPicSelected;
    private EditText firstname;
    private EditText lastname;
    private final ThemeMode mode = ThemeMode.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set click listeners for buttons
        binding.btnBack.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

        // Setting the original user's data
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        binding.ivProfile.setImageBitmap(Base64Utils.decodeBase64ToBitmap(getIntent().getStringExtra("profile")));
        binding.etFirstname.setText(getIntent().getStringExtra("firstname"));
        binding.etLastname.setText(getIntent().getStringExtra("lastname"));

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnSaved.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                binding.btnSaved.setEnabled(isContentValid());
            }
        };
        firstname = binding.etFirstname;
        lastname = binding.etLastname;
        firstname.addTextChangedListener(watcher);
        lastname.addTextChangedListener(watcher);

        // Uploading an image
        binding.btnChangeImg.setOnClickListener(v -> {
            prvImg = binding.ivProfile;
            if (PermissionsManager.checkPermissionREAD_EXTERNAL_STORAGE(this)) {
                imageHandler.openChooser();
            }
        });

        // Clicking on Save user button
        binding.btnSaved.setOnClickListener(v -> {
            setResult(RESULT_OK, new Intent()
                    .putExtra("firstname", firstname.getText().toString())
                    .putExtra("pic", Base64Utils.encodeBitmapToBase64(bitmap))
                    .putExtra("lastname", lastname.getText().toString()));
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
            binding.btnSaved.setEnabled(isContentValid());
        } else {
            // Handle error or cancellation
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
            isPicSelected = false;
        }
    }

    // Checking if the content for the updated post is valid
    private boolean isContentValid() {
        return isPicSelected && firstname != null && !TextUtils.isEmpty(firstname.getText())
                && lastname != null && !TextUtils.isEmpty(lastname.getText());
    }
}