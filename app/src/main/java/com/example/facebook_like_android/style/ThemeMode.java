package com.example.facebook_like_android.style;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.facebook_like_android.R;

// Singleton class to manage the app's theme mode
public class ThemeMode {
    private boolean isDarkTheme;  // Flag to track the current theme mode
    private static ThemeMode instance = null;  // Singleton instance

    // Private constructor to enforce singleton pattern
    private ThemeMode() {
        this.isDarkTheme = false;  // Default theme mode is light
    }

    // Method to get or create the singleton instance
    public static synchronized ThemeMode getInstance() {
        if (instance == null)
            instance = new ThemeMode();
        return instance;
    }

    // Method to toggle between light and dark theme modes
    public void changeTheme(AppCompatActivity activity) {
        // Change the default night mode of the app
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        // Apply the appropriate theme to the current activity
        if (isDarkTheme) {
            activity.setTheme(R.style.AppTheme_Light);
        } else {
            activity.setTheme(R.style.AppTheme_Dark);
        }

        // Toggle the theme flag for the next change
        isDarkTheme = !isDarkTheme;
    }
}
