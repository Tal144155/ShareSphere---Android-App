package com.example.facebook_like_android.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.entities.post.WebServiceAPI;
import com.example.facebook_like_android.responses.LoginResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginAPI {

    private WebServiceAPI webServiceAPI;
    private Retrofit retrofit;

    public LoginAPI() {
        retrofit = RetrofitClient.getRetrofit();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void login(String username, String password, MutableLiveData<Boolean> loginResponse, MutableLiveData<String> loginResult) {
        Call<LoginResponse> login = webServiceAPI.processLogin(username, password);

        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        loginResponse.postValue(true);
                        loginResult.postValue(response.body().getToken());
                        //Log.d("DEBUG", "token: " + response.body().getToken());
                    }).start();
                } else {
                    new Thread(() -> loginResponse.postValue(false)).start();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                new Thread(() -> loginResponse.postValue(false)).start();
            }
        });
    }

    public void getUser(String username, MutableLiveData<User> user) {
        Call<User> call = webServiceAPI.getUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        Log.d("DEBUG", "got user from server!");
                        user.postValue(new User(response.body().getUsername(), response.body().getFirstname(),
                                response.body().getLastname(), response.body().getPassword(),
                                response.body().getProfile(), response.body().getPosts(),
                                response.body().getFriends(), response.body().getFriendRequests()));
                    }).start();
                } else {
                    new Thread(() -> user.postValue(null)).start();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                new Thread(() -> user.postValue(null)).start();
            }
        });
    }
}
