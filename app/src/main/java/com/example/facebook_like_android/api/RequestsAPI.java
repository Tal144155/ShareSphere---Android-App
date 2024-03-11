package com.example.facebook_like_android.api;

import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.daos.UserDao;
import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.responses.DefaultResponse;
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


    public void addFriendRequest(String requestUsername, MutableLiveData<List<User>> requests, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.friendRequest(username, requestUsername, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    // update list of requests
                    new Thread(() -> {
                        userDao.addFriendRequest(username, requestUsername);
                        requests.postValue(userDao.getFriendRequests(username));
                        message.postValue("request added successfully");
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



    public void deleteFriendRequest(String requestUsername, MutableLiveData<List<User>> requests, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.deleteFriend(username, requestUsername, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        // update list of requests
                        userDao.deleteFriendRequest(username, requestUsername);
                        requests.postValue(userDao.getFriendRequests(username));
                        message.postValue("friend request deleted successfully");
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


    public void getFriendRequests(MutableLiveData<List<User>> requests, MutableLiveData<String> message) {
        Call<List<User>> call = webServiceAPI.getFriendsRequests(username, username, token);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        userDao.insertFriendRequests(response.body());
                        requests.postValue(userDao.getFriendRequests(username));
                        message.postValue("Fetched all requests");
                    }).start();
                } else {
                    new Thread(() -> message.postValue("An error occurred while fetching requests")).start();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }
}
