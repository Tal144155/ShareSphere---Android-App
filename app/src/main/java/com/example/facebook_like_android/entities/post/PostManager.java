package com.example.facebook_like_android.entities.post;

import java.util.ArrayList;
import java.util.List;

/**
 * The PostManager class manages posts in the application.
 * It provides methods for adding, removing, updating posts,
 * and retrieving posts and comments.
 */
public class PostManager {
    private static PostManager instance = null;
    private final List<Post> posts;

    // Private constructor to prevent instantiation from outside
    private PostManager() {
        posts = new ArrayList<>();
    }

    /**
     * Get the singleton instance of the PostManager class.
     *
     * @return The singleton instance of PostManager.
     */
    public static synchronized PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }

    /**
     * Get the list of all posts.
     *
     * @return The list of all posts.
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Add a new post.
     *
     * @param post The post to add.
     */
    public void addPost(Post post) {
        posts.add(post);
    }

    /**
     * Remove a post.
     *
     * @param post The post to remove.
     */
    public void removePost(Post post) {
        posts.remove(post);
    }

    /**
     * Update a post at a specific index.
     *
     * @param index   The index of the post to update.
     * @param newPost The new post.
     */
    public void updatePost(int index, Post newPost) {
        if (index >= 0 && index < posts.size()) {
            posts.set(index, newPost);
        }
    }

    /**
     * Get the position of a post in the list.
     *
     * @param post The post to find.
     * @return The position of the post, or -1 if not found.
     */
    public int getPosition(Post post) {
        return posts.indexOf(post);
    }

    /**
     * Get the comments of a post at a specific position.
     *
     * @param position The position of the post.
     * @return The list of comments for the post.
     */
    public List<Comment> getComments(int position) {
        return posts.get(position).getComments();
    }
}
