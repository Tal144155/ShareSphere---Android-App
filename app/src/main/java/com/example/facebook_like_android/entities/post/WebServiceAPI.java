package com.example.facebook_like_android.entities.post;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.responses.DefaultResponse;
import com.example.facebook_like_android.responses.LoginResponse;
import com.example.facebook_like_android.responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    // Users API

    // Friends API
    @PATCH("users/{id}/friends/{fid}")
    Call<DefaultResponse> approveFriendRequest(@Path("id") String id, @Path("fid") String fid);

    @DELETE("users/{id}/friends/{fid}")
    Call<DefaultResponse> deleteFriend(@Path("id") String id, @Path("fid") String fid);


    @GET("users/{id}/friends")
    Call<List<User>> getFriends(@Path("id") String id);

    @POST("users/{id}/friends")
    Call<DefaultResponse> friendRequest(@Path("id") String friend, @Header("username") String id);

    // Comment API
    @GET("users/{id}/posts/{pid}/comments")
    Call<List<Comment>> getComments(@Path("id") String id, @Path("pid") String postId);

    @FormUrlEncoded
    @POST("users/{id}/posts/{pid}/comments")
    Call<DefaultResponse> createComment(@Path("id") String id, @Path("pid") String postId,
                                @Field("content") String content);

    @PATCH("users/{id}/posts/{pid}/comments/{cid}")
    Call<DefaultResponse> editComment(@Path("id") String id, @Path("pid") String pid,
                                      @Path("cid") String commentId, @Header("content") String content);

    @DELETE("users/{id}/posts/{pid}/comments/{cid}")
    Call<DefaultResponse> deleteComment(@Path("id") String id, @Path("pid") String postId, @Path("cid") String commentId);

    // Likes API
    @GET("users/{id}/posts/{pid}/likes")
    Call<Boolean> isLiked(@Path("id") String id, @Path("pid") String postId);

    @PATCH("users/{id}/posts/{pid}/likes")
    Call<DefaultResponse> like(@Path("id") String id, @Path("pid") String postId);

    // Posts API
    @GET("posts")
    Call<List<Post>> getFeed(@Header("username") String username);

    @FormUrlEncoded
    @PATCH("users/{id}/posts/{pid}")
    Call<Post> editPost(@Path("id") String id, @Path("pid") String postId,
                        @Field("content") String content, @Field("pic") String pic);

    @DELETE("users/{id}/posts/{pid}")
    Call<DefaultResponse> deletePost(@Path("id") String id, @Path("pid") String postId);

    @GET("users/{id}/posts")
    Call<List<Post>> getUsersPosts(@Path("id") String friend, @Header("username") String id);

    @FormUrlEncoded
    @POST("users/{id}/posts")
    Call<Post> createPost(@Path("id") String id, @Field("user_name") String user_name,
                          @Field("first_name") String first_name, @Field("last_name") String last_name,
                          @Field("pic") String pic, @Field("profile") String profile,
                          @Field("content") String content, @Field("publish_date") String publish_date);

    // Users
    @GET("users/{id}")
    Call<UserResponse> getUser(@Path("id") String id, @Header("authorization") String token);

    @DELETE("users/{id}")
    Call<DefaultResponse> deleteUser(@Path("id") String id);

    @FormUrlEncoded
    @PATCH("users/{id}")
    Call<DefaultResponse> updateUser(@Path("id") String id, @Field("first_name") String firstname,
                          @Field("last_name") String lastname, @Field("pic") String pic);

    @FormUrlEncoded
    @POST("users/")
    Call<DefaultResponse> createUser(@Field("user_name") String user_name, @Field("password") String password,
                            @Field("first_name") String first_name, @Field("last_name") String last_name,
                            @Field("pic") String pic);

    @GET("users/")
    Call<DefaultResponse> doesExistUserName(@Header("id") String username);

    // Tokens API
    @FormUrlEncoded
    @POST("tokens")
    Call<LoginResponse> processLogin(@Field("username") String user_name, @Field("password") String password);




}
