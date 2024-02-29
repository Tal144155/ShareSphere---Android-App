package com.example.facebook_like_android.entities.post;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PostWithComments {
    @Embedded
    public Post post;

    @Relation(parentColumn = "id", entityColumn = "postId", entity = Comment.class)
    public List<Comment> comments;
}
