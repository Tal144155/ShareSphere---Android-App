package com.example.facebook_like_android.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.responses.LoginResponse;
import com.example.facebook_like_android.responses.UserResponse;
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

    public void getUser(String username, MutableLiveData<UserResponse> user, String token) {
        Call<UserResponse> call = webServiceAPI.getUser(username, token);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        Log.d("DEBUG", "got user from server!");
                        Log.d("DEBUG", "username: " + response.body().getUser_name());

                        user.postValue(response.body());

                    }).start();
                } else {
                    Log.d("DEBUG", "server unsuccessful");
                    new Thread(() -> user.postValue(null)).start();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("DEBUG", t.getLocalizedMessage());
                new Thread(() -> user.postValue(null)).start();
            }
        });
    }
}
