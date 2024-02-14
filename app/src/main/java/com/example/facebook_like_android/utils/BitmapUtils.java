package com.example.facebook_like_android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Utility class for converting Bitmap to String and vice versa.
 */
public class BitmapUtils {

    /**
     * Converts a Base64 encoded string to a Bitmap.
     *
     * @param encodedString The Base64 encoded string representing the image.
     * @return The decoded Bitmap.
     */
    public static Bitmap stringToBitmap(String encodedString) {
        if (encodedString == null) return null;
        byte[] decodedByteArray = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    /**
     * Converts a Bitmap to a Base64 encoded string.
     *
     * @param bitmap The Bitmap to encode.
     * @return The Base64 encoded string representing the image.
     */
    public static String bitmapToString(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
