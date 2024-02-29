// UserInfoManager.java
package com.example.facebook_like_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Utility class for managing user information such as profile picture and nickname.
 */
public class UserInfoManager {

    /**
     * Sets the profile picture for an ImageButton from SharedPreferences.
     *
     * @param context    The context to access SharedPreferences.
     * @param btnProfile The ImageButton to set the profile picture.
     */
    public static void setProfile(Context context, ImageButton btnProfile) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        btnProfile.setImageBitmap(BitmapUtils.stringToBitmap(profile));
    }

    /**
     * Sets the profile picture for an ImageView from SharedPreferences.
     *
     * @param context  The context to access SharedPreferences.
     * @param ivProfile The ImageView to set the profile picture.
     */
    public static void setProfile(Context context, ImageView ivProfile) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        ivProfile.setImageBitmap(BitmapUtils.stringToBitmap(profile));
    }

    /**
     * Sets the nickname for a TextView from SharedPreferences.
     *
     * @param context    The context to access SharedPreferences.
     * @param tvNickname The TextView to set the nickname.
     */
    public static void setNickname(Context context, TextView tvNickname) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String nickname = preferences.getString("nickname", "");
        tvNickname.append(nickname);
    }

    /**
     * Retrieves the username from SharedPreferences.
     *
     * @param context The context to access SharedPreferences.
     * @return The username.
     */
    public static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }

    /**
     * Retrieves the nickname from SharedPreferences.
     *
     * @param context The context to access SharedPreferences.
     * @return The nickname.
     */
    public static String getNickname(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("nickname", "");
    }

    /**
     * Retrieves the profile picture as a Bitmap from SharedPreferences.
     *
     * @param context The context to access SharedPreferences.
     * @return The profile picture as a Bitmap.
     */
    public static Bitmap getProfileBitmap(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String s = preferences.getString("profile", "");
        return BitmapUtils.stringToBitmap(s);
    }

    /**
     * Retrieves the profile picture as a String from SharedPreferences.
     *
     * @param context The context to access SharedPreferences.
     * @return The profile picture as a String.
     */
    public static String getProfile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("profile", "");
    }

}
