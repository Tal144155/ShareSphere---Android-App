package com.example.facebook_like_android.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Utility class for managing permissions.
 */
public class PermissionsManager {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    /**
     * Checks if the READ_EXTERNAL_STORAGE permission is granted.
     *
     * @param activity The activity requesting the permission.
     * @return True if the permission is granted, false otherwise.
     */
    public static boolean checkPermissionREAD_EXTERNAL_STORAGE(@NonNull Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            Log.d("PermissionsManager", "Permission not granted, requesting it");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return false;
        } else {
            // Permission already granted
            Log.d("PermissionsManager", "Permission already granted");
            return true;
        }
    }

    /**
     * Handles the result of permission requests.
     *
     * @param requestCode  The request code.
     * @param grantResults The results of the permission requests.
     * @param activity     The activity that requested the permission.
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults, Activity activity) {
        if (grantResults.length > 0)
            if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    Log.d("PermissionsManager", "Permission is granted");
                } else {
                    // Permission denied
                    showPermissionDeniedDialog(activity);
                    Log.d("PermissionsManager", "Permission denied");
                }
            }
    }

    /**
     * Displays a dialog informing the user that the permission was denied.
     *
     * @param activity The activity requesting the permission.
     */
    private static void showPermissionDeniedDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Permission Denied")
                .setMessage("Without this permission, the app cannot access media files.")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                })
                .show();
    }
}
