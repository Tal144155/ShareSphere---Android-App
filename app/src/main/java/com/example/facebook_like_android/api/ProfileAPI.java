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
    String token;

    public ProfileAPI(PostDao postDao, String username) {
        this.postDao = postDao;
        this.username = username;

        retrofit = RetrofitClient.getRetrofit();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
        token = UserInfoManager.getToken();
    }


    public void add(String username, String firstname, String lastname, String profile, String pic, String content, String date, MutableLiveData<List<Post>> posts, MutableLiveData<String> message) {
        Call<Post> call = webServiceAPI.createPost(username, username, firstname, lastname, pic, profile, content, date , token);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        postDao.insert(response.body());
                        posts.postValue(postDao.getPostsByUser(username));
                        message.postValue("Posts updated successfully");
                    }).start();
                } else {
                    new Thread(() -> message.postValue("Couldn't create this post")).start();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }

    public void delete(Post post, MutableLiveData<List<Post>> posts, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.deletePost(post.getUsername(), post.getPostId(), token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        postDao.delete(post);
                        posts.postValue(postDao.getPostsByUser(post.getUsername()));
                        message.postValue("Post deleted successfully");
                    }).start();
                } else {
                    new Thread(() -> message.postValue(response.body().getError())).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });

    }

    public void get(MutableLiveData<List<Post>> posts, MutableLiveData<String> message) {
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

    public void update(String postId, String content, String pic, MutableLiveData<List<Post>> posts, MutableLiveData<String> message) {
        Call<Post> call = webServiceAPI.editPost(UserInfoManager.getUsername(), postId, content, pic, token);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        postDao.insert(response.body());
                        posts.postValue(postDao.getPostsByUser(UserInfoManager.getUsername()));
                    }).start();
                } else {
                    new Thread(() -> message.postValue("Couldn't update post")).start();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });

    }
}
