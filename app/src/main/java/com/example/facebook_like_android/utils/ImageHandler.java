package com.example.facebook_like_android.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class ImageHandler {
    private final Activity activity;
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;

    public ImageHandler(Activity activity) {
        this.activity = activity;
    }

    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, REQUEST_CODE_PICK_IMAGE);
        //activity.startActivity(galleryIntent);
    }

    public Uri handleActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView) {
        Uri photoUri = null;
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                photoUri = data.getData();
                try {
                    Bitmap bitmap = loadBitmapFromUri(photoUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, "Image selection canceled", Toast.LENGTH_SHORT).show();
            }
        }
        return photoUri;
    }

    private Bitmap loadBitmapFromUri(Uri uri) throws IOException {
        try (InputStream input = activity.getContentResolver().openInputStream(uri)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            return BitmapFactory.decodeStream(input, null, options);
        }
    }
}

