package com.example.facebook_like_android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsManager {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 158942;

    public static boolean checkPermissionREAD_EXTERNAL_STORAGE(@NonNull Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        Log.d("DEBUG", "current api version: " + currentAPIVersion);
        Log.d("DEBUG", "min api version: " + Build.VERSION_CODES.M);
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            Log.d("DEBUG", "api version is ok");
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("DEBUG", "no access");
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    showDialog("External storage", context, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    Log.d("DEBUG", "requesting permissions");
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes, (dialog, which) ->
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults, Activity activity) {
        Log.d("DEBUG", "onRequestPermissionsResult: requestCode=" + requestCode);
        // Handle other permission request results
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("DEBUG", "onRequestPermisssionsResult works");
                // Permission granted
            } else {
                // Permission denied
                Toast.makeText(activity, "media permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
