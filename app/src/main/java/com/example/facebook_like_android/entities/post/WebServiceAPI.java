package com.example.facebook_like_android.entities.post;

import com.example.facebook_like_android.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // Users API

    // Friends API
    @PATCH("users/{id}/friends/{fid}")
    Call<Void> approveFriendRequest(@Path("id") String id, @Path("fid") String fid);

    @DELETE("users/{id}/friends/{fid}")
    Call<Void> deleteFriend(@Path("id") String id, @Path("fid") String fid);

    @GET("users/{id}/friends")
    Call<List<User>> getFriends(@Path("id") String id);

    @POST("users/{id}/friends")
    Call<Void> friendRequest(@Path("id") String id);

    // Comment API
    @GET("users/{id}/posts/{pid}/comments")
    Call<List<Comment>> getComments(@Path("id") String id, @Path("pid") String postId);

    @POST("users/{id}/posts/{pid}/comments")
    Call<Comment> createComment(@Path("id") String id, @Path("pid") String postId);

    @PATCH("users/{id}/posts/{pid}/comments/{cid}")
    Call<Comment> editComment(@Path("id") String id, @Path("pid") String postId, @Path("cid") String commentId);

    @DELETE("users/{id}/posts/{pid}/comments/{cid}")
    Call<Void> deleteComment(@Path("id") String id, @Path("pid") String postId, @Path("cid") String commentId);

    // Likes API
    @GET("users/{id}/posts/{pid}/likes")
    Call<Boolean> isLiked(@Path("id") String id, @Path("pid") String postId);

    @PATCH("users/{id}/posts/{pid}/likes")
    Call<Void> like(@Path("id") String id, @Path("pid") String postId);

    // Posts API
    @GET("posts")
    Call<List<Post>> getFeed();


    @PATCH("users/{id}/posts/{pid}")
    Call<Post> editPost(@Path("id") String id, @Path("pid") String postId);

    @DELETE("users/{id}/posts/{pid}")
    Call<Void> deletePost(@Path("id") String id, @Path("pid") String postId);

    @GET("users/{id}/posts")
    Call<List<Post>> getUsersPosts(@Path("id") String id);

    @POST("users/{id}/posts")
    Call<Post> createPost(@Path("id") String id);

    // Users
    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

    @GET("users/{id}")
    Call<User> updateUser(@Path("id") String id);

    @POST("users/")
    Call<User> createUser(@Path("id") String id);


    // Tokens API
    @POST("tokens")
    Call<String> processLogin();


}
