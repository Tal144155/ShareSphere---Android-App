package com.example.facebook_like_android.responses;

public class BooleanResponse {
    private boolean isLiked;

    public BooleanResponse(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
