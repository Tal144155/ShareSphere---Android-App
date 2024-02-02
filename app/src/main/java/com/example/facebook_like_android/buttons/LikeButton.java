package com.example.facebook_like_android.buttons;

import android.view.View;
import android.widget.ImageButton;

import com.example.facebook_like_android.R;

public class LikeButton {
    private final ImageButton button;
    private boolean isLiked;

    public LikeButton(ImageButton button) {
        this.button = button;
        this.isLiked = false;

        // Set initial appearance
        updateButtonAppearance();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        button.setOnClickListener(v -> {
            toggleLike();
            onClickListener.onClick(v);
        });
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void toggleLike() {
        isLiked = !isLiked;
        updateButtonAppearance();
    }

    private void updateButtonAppearance() {
        // Change the button's appearance based on the like status
        if (isLiked) {
            // Set the button to the "liked" state
            button.setImageResource(R.drawable.ic_unlike); // liked icon
        } else {
            // Set the button to the "unliked" state
            button.setImageResource(R.drawable.ic_like); // unliked icon
        }
    }


}
