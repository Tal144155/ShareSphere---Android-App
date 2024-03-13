package com.example.facebook_like_android.api;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.retrofit.RetrofitClient;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedAPI {
    private PostDao postDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    String token;
    private String username;

    public FeedAPI(PostDao postDao) {
        this.postDao = postDao;

        retrofit = RetrofitClient.getRetrofit();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
        token = UserInfoManager.getToken();
        username = UserInfoManager.getUsername();
    }


    public void get(MutableLiveData<List<Post>> posts, MutableLiveData<String> message) {
        Call<List<Post>> call = webServiceAPI.getFeed(username, token);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        postDao.insertList(response.body());
                        posts.postValue(response.body());
                    }).start();
                } else {
                    new Thread(() -> message.postValue("Couldn't refresh feed")).start();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }
}
