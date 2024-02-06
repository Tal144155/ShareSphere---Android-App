package com.example.facebook_like_android.utils;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

// CircularOutlineUtil.java
public class CircularOutlineUtil {
    public static void applyCircularOutline(ImageView imageView) {
        imageView.setClipToOutline(true);
        imageView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 5000f);
            }
        });
    }

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
