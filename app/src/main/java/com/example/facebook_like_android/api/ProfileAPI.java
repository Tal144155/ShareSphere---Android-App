package com.example.facebook_like_android.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.responses.DefaultResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileAPI {

    private PostDao postDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String username;
    private MutableLiveData<Boolean> hasChanged;
    private MutableLiveData<String> message;
    String token;
    private String current;

    public ProfileAPI(PostDao postDao, String username, MutableLiveData<Boolean> hasChanged, MutableLiveData<String> message) {
        this.postDao = postDao;
        this.username = username;
        this.current = UserInfoManager.getUsername();

        this.hasChanged = hasChanged;
        this.message = message;
        retrofit = RetrofitClient.getRetrofit();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
        token = UserInfoManager.getToken();
    }


    public void add(String username, String firstname, String lastname, String profile, String pic, String content, String date) {
        Call<Post> call = webServiceAPI.createPost(username, username, firstname, lastname, pic, profile, content, date , token);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        //postDao.insert(response.body());
                        hasChanged.postValue(true);
                        //posts.postValue(postDao.getPostsByUser(username));
                        message.postValue("Posts created successfully");
                    }).start();
                } else {
                    new Thread(() -> {
                        message.postValue("Couldn't create this post");
                        hasChanged.postValue(false);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                new Thread(() -> {
                    message.postValue(t.getLocalizedMessage());
                    hasChanged.postValue(false);
                }).start();
            }
        });
    }

    public void delete(String postId) {
        Call<DefaultResponse> call = webServiceAPI.deletePost(current, postId, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        hasChanged.postValue(true);
                        message.postValue("Post deleted successfully");
                    }).start();
                } else {
                    new Thread(() -> {
                        hasChanged.postValue(false);
                        message.postValue(response.body().getError());
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> {
                    hasChanged.postValue(false);
                    message.postValue(t.getLocalizedMessage());
                }).start();
            }
        });

    }

    public void get(MutableLiveData<List<Post>> posts) {
        Call<List<Post>> call = webServiceAPI.getUsersPosts(username, username, token);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        postDao.insertList(response.body());
                        Log.d("DEBUG", String.valueOf(response.body().size()));
                        posts.postValue(response.body());
                        Log.d("DEBUG", String.valueOf(postDao.getPostsByUser(username).size()));
                        message.postValue("Refreshed profile");
                    }).start();
                } else {
                    new Thread(() -> message.postValue("Couldn't refresh profile")).start();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }

    public void update(String postId, String content, String pic) {
        Call<Post> call = webServiceAPI.editPost(UserInfoManager.getUsername(), postId, content, pic, token);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        hasChanged.postValue(true);
                        message.postValue("Post updated successfully");
                    }).start();
                } else {
                    new Thread(() -> {
                        hasChanged.postValue(false);
                        message.postValue("Couldn't update post");
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                new Thread(() -> {
                    hasChanged.postValue(false);
                    message.postValue(t.getLocalizedMessage());
                }).start();
            }
        });

    }
}