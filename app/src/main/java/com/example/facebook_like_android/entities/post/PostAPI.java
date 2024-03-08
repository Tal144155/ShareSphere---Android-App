package com.example.facebook_like_android.entities.post;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.ShareSphere;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAPI {
    private MutableLiveData<List<Post>> postListData;
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
        this.postListData = postListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(ShareSphere.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<Post>> call = webServiceAPI.getFeed(UserInfoManager.getUsername());
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                new Thread(() -> {
                    dao.clear();
                    dao.insertList(response.body());
                    postListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    public void add(final Post post) {
        Call<Post> call = webServiceAPI.createPost(UserInfoManager.getUsername(), post.getUsername(),
                UserInfoManager.getFirstName(), UserInfoManager.getLastName(), post.getPic(),
                post.getProfile(), post.getContent(), post.getPublishDate());

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                new Thread(() -> {
                    dao.insert(response.body());
                    postListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


    public void delete(Post post) {
        Call<Void> call = webServiceAPI.deletePost(post.getUsername(), post.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Thread(() -> {
                    dao.delete(post);
                    postListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
