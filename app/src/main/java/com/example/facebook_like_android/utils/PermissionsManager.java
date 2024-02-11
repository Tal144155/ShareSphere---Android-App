package com.example.facebook_like_android.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsManager {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    public static boolean checkPermissionREAD_EXTERNAL_STORAGE(@NonNull Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            Log.d("DEBUG", "Permission not granted, requesting it");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return false;
        } else {
            // Permission already granted
            Log.d("DEBUG", "Permission already granted");
            return true;
        }

    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults, Activity activity) {
        Log.d("DEBUG", "request code = " + requestCode);
        Log.d("DEBUG", "grant result length = " + grantResults.length);
        if (grantResults.length > 0 )
            Log.d("DEBUG", "grant result[0] = " + grantResults[0]);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Log.d("DEBUG", "Permission is granted");
            } else {
                // Permission denied
                showPermissionDeniedDialog(activity);
                Log.d("DEBUG", "Permission denied");
            }
        }
    }

    private static void showPermissionDeniedDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Permission Denied")
                .setMessage("Without this permission, the app cannot access media files.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                })
                .show();
    }
}
