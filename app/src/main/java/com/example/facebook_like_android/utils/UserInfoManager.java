// UserInfoManager.java
package com.example.facebook_like_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoManager {

    public static void setProfile(Context context, ImageButton btnProfile) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        btnProfile.setImageBitmap(BitmapUtils.stringToBitmap(profile));
    }

    public static void setProfile(Context context, ImageView ivProfile) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String profile = preferences.getString("profile", "");
        ivProfile.setImageBitmap(BitmapUtils.stringToBitmap(profile));
    }

    private static boolean isValidUri(String uriString) {
        try {
            Uri.parse(uriString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void setNickname(Context context, TextView tvNickname) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String nickname = preferences.getString("nickname", "");
        tvNickname.setText(nickname);
    }
    public static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }
}
