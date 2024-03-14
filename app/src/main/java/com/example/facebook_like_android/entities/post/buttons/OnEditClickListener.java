package com.example.facebook_like_android.entities.post.buttons;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.facebook_like_android.entities.post.Post;

public interface OnEditClickListener {
    void onEditClick(String postId);
    interface OnDeleteClickListener {
        void onDeleteClick(Post post);
    }
    interface OnLikeClickListener {
        void onLikeClick(String postId, int position, ImageButton like, TextView likes);
    }
}
