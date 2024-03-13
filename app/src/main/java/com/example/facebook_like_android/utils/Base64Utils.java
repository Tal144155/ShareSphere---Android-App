package com.example.facebook_like_android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64Utils {

    public static Bitmap decodeBase64ToBitmap(String base64String) {
        // Find the index of the first comma (,) character to determine the start of Base64 data
        int commaIndex = base64String.indexOf(",");
        if (commaIndex != -1) {
            // Extract the substring containing only the Base64-encoded data
            String base64Data = base64String.substring(commaIndex + 1);

            // Decode the Base64-encoded data into a byte array
            byte[] decodedBytes = Base64.decode(base64Data, Base64.DEFAULT);

            // Create a Bitmap from the decoded byte array
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } else {
            // Handle the case where no comma is found
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }
    }

    public static String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return java.util.Base64.getEncoder().encodeToString(byteArray);
        //return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
