package com.example.facebook_like_android.style;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.facebook_like_android.R;

public class ThemeMode{
    private boolean isDarkTheme;
    private static ThemeMode instance = null;

    private ThemeMode() {
        this.isDarkTheme = false;
    }

    public static synchronized ThemeMode getInstance() {
        if (instance == null)
            instance = new ThemeMode();
        return instance;
    }

    public void changeTheme(AppCompatActivity activity) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        if (isDarkTheme) {
            activity.setTheme(R.style.AppTheme_Light);
        } else {
            activity.setTheme(R.style.AppTheme_Dark);
        }
        isDarkTheme = !isDarkTheme; // Toggle the theme flag
    }
}
