package com.example.facebook_like_android.api;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.responses.DefaultResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;
import com.example.facebook_like_android.utils.UserInfoManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAPI {

    private WebServiceAPI webServiceAPI;
    private Retrofit retrofit;
    private String token;

    public UserAPI() {
        retrofit = RetrofitClient.getRetrofit();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        token = UserInfoManager.getToken();
    }


    public void deleteUser(MutableLiveData<Boolean> hasRemoved, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.deleteUser(UserInfoManager.getUsername(), token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        hasRemoved.postValue(true);
                        message.postValue("User deleted successfully");
                    }).start();
                } else {
                    new Thread(() -> {
                        hasRemoved.postValue(false);
                        message.postValue("Failed to delete user");
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> {
                    hasRemoved.postValue(false);
                    message.postValue(t.getLocalizedMessage());
                }).start();
            }
        });
    }


    // Method to update user information
    public void updateUser(String firstname, String lastname, String profile, MutableLiveData<Boolean> hasChanged, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.updateUser(UserInfoManager.getUsername(), firstname, lastname, profile, token);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        message.postValue("Profile updated successfully");
                        hasChanged.postValue(true);
                    }).start();
                } else {
                    new Thread(() -> {
                        message.postValue("Couldn't update Profile");
                        hasChanged.postValue(false);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                // Handle failure
                new Thread(() -> {
                    message.postValue(t.getLocalizedMessage());
                    hasChanged.postValue(false);
                }).start();
            }
        });
    }
}
