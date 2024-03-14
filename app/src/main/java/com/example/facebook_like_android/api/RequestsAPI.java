package com.example.facebook_like_android.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.responses.DefaultResponse;
import com.example.facebook_like_android.responses.ListUsersResponse;
import com.example.facebook_like_android.retrofit.RetrofitClient;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestsAPI {
    private UserDao userDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String username;
    String token;

    public RequestsAPI(UserDao userDao) {
        this.userDao = userDao;
        this.username = UserInfoManager.getUsername();

        retrofit = RetrofitClient.getRetrofit();

        webServiceAPI = retrofit.create(WebServiceAPI.class);
        token = UserInfoManager.getToken();
    }


    public void addFriendRequest(String requestUsername, MutableLiveData<Boolean> hasChanged, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.friendRequest(requestUsername, username, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    // update list of requests
                    new Thread(() -> {
                        userDao.addFriendRequest(username, requestUsername);
                        hasChanged.postValue(true);
                        message.postValue("request added successfully");
                    }).start();
                } else {
                    new Thread(() -> {
                        message.postValue(response.body().getError());
                        hasChanged.postValue(false);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> {
                    message.postValue(t.getLocalizedMessage());
                    hasChanged.postValue(false);
                }).start();
            }
        });
    }



    public void deleteFriendRequest(String requestUsername, MutableLiveData<Boolean> hasRemoved, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.deleteFriend(username, requestUsername, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        // update list of requests
                        userDao.deleteFriendRequest(username, requestUsername);
                        hasRemoved.postValue(true);
                        message.postValue("friend request deleted successfully");
                    }).start();
                } else {
                    new Thread(() -> {
                        message.postValue(response.body().getError());
                        hasRemoved.postValue(false);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() ->  {
                    message.postValue(t.getLocalizedMessage());
                    hasRemoved.postValue(false);
                }).start();
            }
        });
    }


    public void getFriendRequests(MutableLiveData<List<ListUsersResponse>> requests, MutableLiveData<String> message) {
        Call<List<ListUsersResponse>> call = webServiceAPI.getFriendsRequests(username, username, token);

        call.enqueue(new Callback<List<ListUsersResponse>>() {
            @Override
            public void onResponse(Call<List<ListUsersResponse>> call, Response<List<ListUsersResponse>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        Log.d("DEBUG", "got requests");
                        Log.d("DEBUG", String.valueOf(response.body().size()));
                        //userDao.insertFriendRequests(response.body());
                        requests.postValue(response.body());
                        message.postValue("Fetched all requests");
                    }).start();
                } else {
                    Log.d("DEBUG", "requests not get");
                    new Thread(() -> message.postValue("An error occurred while fetching requests")).start();
                }
            }

            @Override
            public void onFailure(Call<List<ListUsersResponse>> call, Throwable t) {
                Log.d("DEBUG", t.getLocalizedMessage());
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }
}
