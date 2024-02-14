package com.example.facebook_like_android.entities.post.buttons;

public interface OnEditClickListener {
    void onEditClick(int position);
    interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}
