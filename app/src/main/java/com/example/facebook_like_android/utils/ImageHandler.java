package com.example.facebook_like_android.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for handling image selection and retrieval.
 */
public class ImageHandler {
    private final Activity activity;
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;

    /**
     * Constructor for ImageHandler.
     *
     * @param activity The activity context.
     */
    public ImageHandler(Activity activity) {
        this.activity = activity;
    }

    /**
     * Opens a chooser for selecting an image from the gallery or capturing a new image from the camera.
     */
    public void openChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooser = Intent.createChooser(galleryIntent, "Select Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });

        activity.startActivityForResult(chooser, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * Handles the result of the image selection or capture operation.
     *
     * @param requestCode The request code.
     * @param resultCode  The result code.
     * @param data        The Intent containing the result data.
     * @param imageView   The ImageView to display the selected image.
     * @return The Bitmap representing the selected image.
     */
    public Bitmap handleActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView) {
        Uri photoUri;
        Bitmap bitmap = null;
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (data.getData() != null) { // Image selected from Gallery
                    photoUri = data.getData();
                    bitmap = loadBitmapFromUri(photoUri);
                    imageView.setImageBitmap(bitmap);
                }
                else if (data.getExtras() != null && data.getExtras().get("data") != null) {
                    // Image captured from the camera
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                }
                if (bitmap == null)
                    Toast.makeText(activity, "Failed to load image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Image selection canceled", Toast.LENGTH_SHORT).show();
            }
        }
        return bitmap;
    }

    /**
     * Loads a Bitmap from the given Uri.
     *
     * @param uri The Uri of the image.
     * @return The loaded Bitmap.
     */
    public Bitmap loadBitmapFromUri(Uri uri){
        try (InputStream input = activity.getContentResolver().openInputStream(uri)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            return BitmapFactory.decodeStream(input, null, options);
        } catch (IOException e) {
            return null;
        }
    }
}
