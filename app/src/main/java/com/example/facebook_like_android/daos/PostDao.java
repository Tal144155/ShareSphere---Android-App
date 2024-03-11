package com.example.facebook_like_android.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.facebook_like_android.entities.Comment;
import com.example.facebook_like_android.entities.post.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> index();

    @Query("SELECT * FROM post WHERE id = :id")
    Post get(int id);


    // Retrieve comments for a specific post by post id
    @Query("SELECT * FROM comment WHERE postId = :postId")
    List<Comment> getCommentsForPost(int postId);

    // Retrieve posts for a specific user by username
    @Query("SELECT * FROM post WHERE username = :username")
    List<Post> getPostsByUser(String username);


    @Transaction
    @Query("DELETE FROM post WHERE id = :postId")
    void deletePostAndComments(int postId);

    @Transaction
    @Query("DELETE FROM comment WHERE postId = :postId")
    void deleteCommentsForPost(int postId);

    // Delete all posts
    @Transaction
    @Query("DELETE FROM post")
    void clear();

    // Insert multiple posts
    @Insert
    void insertList(List<Post> posts);


    @Insert
    void insert(Post... posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post... posts);

}
