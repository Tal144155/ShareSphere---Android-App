package com.example.facebook_like_android.entities.post.buttons;

import android.view.View;
import android.widget.ImageButton;

import com.example.facebook_like_android.R;

public class LikeButton {
    private final ImageButton button;  // The ImageButton associated with the LikeButton
    private boolean isLiked;  // Flag indicating whether the button is in a liked state

    // Constructor to initialize the LikeButton with an ImageButton
    public LikeButton(ImageButton button) {
        this.button = button;
        this.isLiked = false;  // Initial state is unliked

        // Set initial appearance based on the like status
        updateButtonAppearance();
    }

    // Method to set an OnClickListener on the underlying ImageButton
    public void setOnClickListener(View.OnClickListener onClickListener) {
        // Toggle the like status and invoke the provided OnClickListener
        button.setOnClickListener(v -> {
            toggleLike();
            onClickListener.onClick(v);
        });
    }

    // Getter method to check if the button is in a liked state
    public boolean isLiked() {
        return isLiked;
    }

    // Method to toggle the like status of the button
    public void toggleLike() {
        isLiked = !isLiked;  // Toggle the like status
        updateButtonAppearance();  // Update the appearance of the button
    }

    // Method to update the appearance of the button based on the like status
    private void updateButtonAppearance() {
        // Change the button's appearance based on the like status
        if (isLiked) {
            // Set the button to the "liked" state
            button.setImageResource(R.drawable.ic_unlike); // Liked icon
        } else {
            // Set the button to the "unliked" state
            button.setImageResource(R.drawable.ic_like); // Unliked icon
        }
    }
}
