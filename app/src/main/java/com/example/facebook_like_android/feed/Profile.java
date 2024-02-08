package com.example.facebook_like_android.feed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityProfileBinding;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.ImageHandler;
import com.example.facebook_like_android.utils.UserInfoManager;

public class Profile extends AppCompatActivity implements OnEditClickListener {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 158942;
    private ActivityProfileBinding binding;
    private PostsListAdapter adapter;
    private final ImageHandler imageHandler = new ImageHandler(this);
    Uri picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = binding.lstPosts;
        adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter.setProfileVisibility();
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        adapter.setUsername(preferences.getString("username", ""));

        UserInfoManager.setProfile(this, binding.ivProfile);
        CircularOutlineUtil.applyCircularOutline(binding.ivProfile);
        UserInfoManager.setNickname(this, binding.tvNickname);

        adapter.setOnEditClickListener(this);

        binding.btnHome.setOnClickListener(v -> finish());

    }


    @Override
    public void onEditClick(int position) {
        startEditVisibility();
        TextView tv = binding.lstPosts.findViewById(R.id.tv_content);
        EditText content = binding.lstPosts.findViewById(R.id.et_content);
        content.setText(tv.getText());

        binding.lstPosts.findViewById(R.id.btn_update).setOnClickListener(v -> {
            adapter.updatePost(position, content.getText().toString(), picture);
            finishEditVisibility();
            setResult(RESULT_OK);
        });

        binding.lstPosts.findViewById(R.id.btn_changeImg).setOnClickListener(v -> {
//            if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
//                Log.d("DEBUG", "permissions are granted. calling openChooser");
                imageHandler.openChooser();
            //}
        });
    }

    private void startEditVisibility() {
        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_update).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_changeImg).setVisibility(View.VISIBLE);
    }
    private void finishEditVisibility() {
        binding.lstPosts.findViewById(R.id.et_content).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.tv_content).setVisibility(View.VISIBLE);
        binding.lstPosts.findViewById(R.id.btn_update).setVisibility(View.GONE);
        binding.lstPosts.findViewById(R.id.btn_changeImg).setVisibility(View.GONE);
    }

    // Handle activity result using onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            picture = imageHandler.handleActivityResult(requestCode, resultCode, data,
                    binding.lstPosts.findViewById(R.id.iv_pic));
            Log.d("DEBUG", "Picture Uri: " + picture); // Log the Uri to verify its correctness
        } else {
            // Handle error or cancellation
            Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
        }
    }
//    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
//            final Context context) {
//        int currentAPIVersion = Build.VERSION.SDK_INT;
//        Log.d("DEBUG", "current api version: " + currentAPIVersion);
//        Log.d("DEBUG", "min api version: " + android.os.Build.VERSION_CODES.M);
//        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
//            Log.d("DEBUG", "api version is ok");
//            if (ContextCompat.checkSelfPermission(context,
//                    READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                Log.d("DEBUG", "external storage = " + READ_EXTERNAL_STORAGE);
//                Log.d("DEBUG", "no access");
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        (Activity) context,
//                        READ_EXTERNAL_STORAGE)) {
//                    showDialog("External storage", context,
//                            READ_EXTERNAL_STORAGE);
//
//                } else {
//                    ActivityCompat
//                            .requestPermissions(
//                                    (Activity) context,
//                                    new String[] { READ_EXTERNAL_STORAGE },
//                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                    showDialog("External storage", context,
//                            READ_EXTERNAL_STORAGE);
//                    Log.d("DEBUG", "requesting permissions");
//                }
//                return false;
//            } else {
//                return true;
//            }
//
//        } else {
//            return true;
//        }
//    }
//
//    public void showDialog(final String msg, final Context context,
//                           final String permission) {
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//        alertBuilder.setCancelable(true);
//        alertBuilder.setTitle("Permission necessary");
//        alertBuilder.setMessage(msg + " permission is necessary");
//        alertBuilder.setPositiveButton(android.R.string.yes,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCompat.requestPermissions((Activity) context,
//                                new String[] { permission },
//                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                    }
//                });
//        AlertDialog alert = alertBuilder.create();
//        alert.show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d("DEBUG", "onRequestPermissionsResult: requestCode=" + requestCode);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("DEBUG", "onRequestPermisssionsResult works");
//                    //imageHandler.openChooser();
//                } else {
//                    Toast.makeText(this, "GET_ACCOUNTS Denied",
//                            Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions,
//                        grantResults);
//        }
//    }

}