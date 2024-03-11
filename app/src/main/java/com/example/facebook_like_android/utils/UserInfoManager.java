// UserInfoManager.java
package com.example.facebook_like_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.facebook_like_android.ShareSphere;
import com.example.facebook_like_android.entities.User;

/**
 * Utility class for managing user information such as profile picture and nickname.
 */
public class UserInfoManager {
    private static Context context = ShareSphere.context;

    public static User getUser() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        String firstname = preferences.getString("firstname", "");
        String lastname = preferences.getString("lastname", "");
        String username = preferences.getString("username", "");
        return new User(username, firstname, lastname, "", profile, null, null, null);
    }

    /**
     * Sets the profile picture for an ImageButton from SharedPreferences.
     *
     * @param btnProfile The ImageButton to set the profile picture.
     */
    public static void setProfile(ImageButton btnProfile) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        btnProfile.setImageBitmap(BitmapUtils.stringToBitmap(profile));
    }

    /**
     * Sets the profile picture for an ImageView from SharedPreferences.
     *
     * @param ivProfile The ImageView to set the profile picture.
     */
    public static void setProfile(ImageView ivProfile) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        ivProfile.setImageBitmap(BitmapUtils.stringToBitmap(profile));
    }

    /**
     * Sets the nickname for a TextView from SharedPreferences.
     *
     * @param tvNickname The TextView to set the nickname.
     */
    public static void setNickname(TextView tvNickname) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String nickname = preferences.getString("nickname", "");
        tvNickname.append(nickname);
    }

    /**
     * Retrieves the username from SharedPreferences.
     *
     * @return The username.
     */
    public static String getUsername() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }

    /**
     * Retrieves the nickname from SharedPreferences.
     *
     * @return The nickname.
     */
    public static String getNickname() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("nickname", "");
    }

    /**
     * Retrieves the profile picture as a Bitmap from SharedPreferences.
     *
     * @return The profile picture as a Bitmap.
     */
    public static Bitmap getProfileBitmap() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String s = preferences.getString("profile", "");
        return BitmapUtils.stringToBitmap(s);
    }

    /**
     * Retrieves the profile picture as a String from SharedPreferences.
     *
     * @return The profile picture as a String.
     */
    public static String getProfile() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("profile", "");
    }

    public static String getFirstName() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("firstname", "");
    }

    public static String getLastName() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("lastname", "");
    }

    public static String getToken() {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("token", "");
    }

}
