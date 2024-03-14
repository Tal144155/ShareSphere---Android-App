package com.example.facebook_like_android.entities.post.buttons;

import android.widget.ImageButton;

import com.example.facebook_like_android.R;

public class LikeButton {
    private boolean isLiked;
    public LikeButton(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public void like(ImageButton imageButton) {
        isLiked = !isLiked;  // Toggle the like status
        updateAppearance(imageButton);
    }

    public void updateAppearance(ImageButton imageButton) {
        // Change the button's appearance based on the like status
        if (isLiked) {
            // Set the button to the "liked" state
            imageButton.setImageResource(R.drawable.ic_unlike); // Liked icon
        } else {
            // Set the button to the "unliked" state
            imageButton.setImageResource(R.drawable.ic_like); // Unliked icon
        }
    }

    public static void setLike(ImageButton imageButton) {
        // Set the button to the "liked" state
        imageButton.setImageResource(R.drawable.ic_like); // Liked icon
    }

    public static void setUnlike(ImageButton imageButton) {
        // Set the button to the "unliked" state
        imageButton.setImageResource(R.drawable.ic_unlike); // Unliked icon
    }
}
