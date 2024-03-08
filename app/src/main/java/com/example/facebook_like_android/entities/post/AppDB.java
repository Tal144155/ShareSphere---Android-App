package com.example.facebook_like_android.entities.post;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class, Comment.class}, version = 3)

public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
}