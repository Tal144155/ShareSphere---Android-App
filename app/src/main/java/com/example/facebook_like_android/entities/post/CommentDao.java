package com.example.facebook_like_android.entities.post;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    List<Comment> index();

    // Retrieve comments for a specific post by post id
    @Query("SELECT * FROM comment WHERE postId = :postId")
    List<Comment> getCommentsForPost(int postId);

    @Insert
    void insert(Comment... comments);

    @Update
    void update(Comment... comments);

    @Delete
    void delete(Comment... comments);
}
