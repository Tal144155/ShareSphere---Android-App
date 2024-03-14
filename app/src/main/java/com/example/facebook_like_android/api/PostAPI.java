package com.example.facebook_like_android.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.responses.BooleanResponse;
import com.example.facebook_like_android.responses.DefaultResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;
import com.example.facebook_like_android.utils.UserInfoManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostAPI {
    private PostDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private MutableLiveData<Boolean> hasChanged;
    private MutableLiveData<Boolean> isLiked;
    private String token;
    private String username;



    public PostAPI(MutableLiveData<Boolean> isLiked, MutableLiveData<Boolean> hasChanged) {
        this.isLiked = isLiked;
        this.hasChanged = hasChanged;

        dao = AppDB.getDatabase().postDao();

        retrofit = RetrofitClient.getRetrofit();

        webServiceAPI = retrofit.create(WebServiceAPI.class);

        token = UserInfoManager.getToken();
        username = UserInfoManager.getUsername();
    }

    public void isLiked(String postId) {
        Call<BooleanResponse> call = webServiceAPI.isLiked(username, postId, token);

        call.enqueue(new Callback<BooleanResponse>() {
            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                new Thread(() -> isLiked.postValue(response.body().isLiked())).start();
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                Log.d("DEBUG", "server failed to do isLiked");
            }
        });
    }

    public void like(String postId) {
        Call<DefaultResponse> call = webServiceAPI.like(username, postId, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        hasChanged.postValue(true);
                    }).start();
                } else {
                    new Thread(() -> hasChanged.postValue(false)).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> hasChanged.postValue(false)).start();
            }
        });
    }


//    public void delete(String postId) {
//        Call<DefaultResponse> call = webServiceAPI.deletePost(username, postId, token);
//
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                if (response.isSuccessful()) {
//                    new Thread(() -> {
//                        hasRemoved.postValue(true);
//                        message.postValue("Post deleted successfully");
//                        //postListData.postValue(dao.index());
//                    }).start();
//                } else {
//                    new Thread(() -> {
//                        hasRemoved.postValue(false);
//                        message.postValue("Couldn't delete post");
//                    }).start();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                new Thread(() -> {
//                    hasRemoved.postValue(false);
//                    message.postValue(t.getLocalizedMessage());
//                }).start();
//            }
//        });
//    }
//
//    public void update(String pic, String content, String postId) {
//        Call<Post> call = webServiceAPI.editPost(username, postId, content, pic, token);
//
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if (response.isSuccessful()) {
//                    new Thread(() -> {
//                        hasChanged.postValue(true);
//                        message.postValue("Post updated successfully");
//                    }).start();
//                } else {
//                    new Thread(() -> {
//                        hasChanged.postValue(false);
//                        message.postValue("Couldn't update post");
//                    }).start();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//                new Thread(() -> {
//                    hasChanged.postValue(false);
//                    message.postValue(t.getLocalizedMessage());
//                }).start();
//            }
//        });
//
//
//    }
}
