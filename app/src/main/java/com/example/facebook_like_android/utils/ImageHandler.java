package com.example.facebook_like_android.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooser = Intent.createChooser(galleryIntent, "Select Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });

        activity.startActivityForResult(chooser, REQUEST_CODE_PICK_IMAGE);
    }

    public Uri handleActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView) {
        Uri photoUri = null;
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (data.getData() != null) // Image selected from Gallery
                    photoUri = data.getData();
                else if (data.getExtras() != null && data.getExtras().get("data") != null) {
                    // Image captured from the camera
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    photoUri = getImageUri(activity, bitmap);
                }
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

    private Uri getImageUri(Activity activity, Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}

