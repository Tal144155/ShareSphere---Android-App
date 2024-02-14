package com.example.facebook_like_android.utils;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Utility class for applying circular outline to ImageView and AppCompatImageView.
 */
public class CircularOutlineUtil {

    /**
     * Applies circular outline to the given ImageView.
     *
     * @param imageView The ImageView to which circular outline will be applied.
     */
    public static void applyCircularOutline(ImageView imageView) {
        imageView.setClipToOutline(true);
        imageView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 5000f);
            }
        });
    }

    /**
     * Applies circular outline to the given AppCompatImageView.
     *
     * @param imageButton The AppCompatImageView to which circular outline will be applied.
     */
    public static void applyCircularOutline(AppCompatImageView imageButton) {
        imageButton.setClipToOutline(true);
        imageButton.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 100f);
            }
        });
    }
}
