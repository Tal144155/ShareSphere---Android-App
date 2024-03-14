package com.example.facebook_like_android.api;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.responses.DefaultResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpAPI {
    private WebServiceAPI webServiceAPI;
    private Retrofit retrofit;

    public SignUpAPI() {
        retrofit = RetrofitClient.getRetrofit();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void isUnique(String username, MutableLiveData<Boolean> unique) {
        Call<DefaultResponse> call = webServiceAPI.doesExistUserName(username);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> unique.postValue(true)).start();
                } else {
                    new Thread(() -> unique.postValue(false)).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> unique.postValue(false)).start();
            }
        });
    }

    public void signup(String username, String password, String firstname, String lastname, String profile, MutableLiveData<Boolean> isAdded) {
        Call<DefaultResponse> signup = webServiceAPI.createUser(username, password, firstname, lastname, profile);

        signup.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> isAdded.postValue(true)).start();
                } else {
                    new Thread(() -> isAdded.postValue(false)).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> isAdded.postValue(false)).start();
            }
        });
    }
}
