package com.example.facebook_like_android.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.responses.PostResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.ArrayList;
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


    public void get(MutableLiveData<List<PostResponse>> posts, MutableLiveData<String> message) {
        Call<List<PostResponse>> call = webServiceAPI.getFeed(username, token);

        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        Log.d("DEBUG", "got posts");
                        Log.d("DEBUG", String.valueOf(response.body().size()));
                        List<PostResponse> list = response.body();
                        for (PostResponse res : list) {
                            postDao.insert(new Post(res.getUser_name(), res.getFirst_name(),
                                    res.getLast_name(), res.getContent(), res.getPic(),
                                    res.getProfile(), res.getLikes(), new ArrayList<>(),
                                    res.getPublish_date(), res.get_id()));
                        }
                        posts.postValue(response.body());
                    }).start();
                } else {
                    Log.d("DEBUG", "didn't get posts");
                    new Thread(() -> message.postValue("Couldn't refresh feed")).start();
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                Log.d("DEBUG", t.getLocalizedMessage());
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }
}
