package com.example.facebook_like_android.entities.post.buttons;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.facebook_like_android.responses.PostResponse;

public interface OnEditClickListener {
    void onEditClick(String postId);
    interface OnDeleteClickListener {
        void onDeleteClick(PostResponse post);
    }
    interface OnLikeClickListener {
        void onLikeClick(String postId, int position, ImageButton like, TextView likes);
    }
}
