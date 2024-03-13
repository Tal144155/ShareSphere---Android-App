package com.example.facebook_like_android.entities.post.buttons;

import com.example.facebook_like_android.entities.post.Post;

public interface OnEditClickListener {
    void onEditClick(String postId);
    interface OnDeleteClickListener {
        void onDeleteClick(Post post);
    }
}
