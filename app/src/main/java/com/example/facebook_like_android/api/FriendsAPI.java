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

public class FriendsAPI {

    private UserDao userDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    String username;
    String token;

    public FriendsAPI(UserDao userDao) {
        this.userDao = userDao;

        retrofit = RetrofitClient.getRetrofit();

        webServiceAPI = retrofit.create(WebServiceAPI.class);

        username = UserInfoManager.getUsername();
        token = UserInfoManager.getToken();
    }

//    // Method to get users from the server
//    public void getUsers() {
//        Call<List<User>> call = webServiceAPI.getUsers();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                new Thread(() -> {
//                    userDao.clear();
//                    userDao.insertList(response.body());
//                    userListData.postValue(userDao.index());
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                // Handle failure
//            }
//        });
//    }


    // Method to update user information
//    public void updateUser(User user) {
//        Call<DefaultResponse> call = webServiceAPI.updateUser(user.getUsername(), user.getFirstname(),
//                user.getLastname(), user.getProfile());
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                new Thread(() -> {
//                    //userDao.update(response.body());
//                    userListData.postValue(userDao.index());
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                // Handle failure
//            }
//        });
//    }

    public void addFriend(String friend, MutableLiveData<List<ListUsersResponse>> friends, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.approveFriendRequest(username, friend, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG", "friend added successfully");
                    // update list of friends
                    new Thread(() -> {
                        userDao.addFriend(username, friend);
                        userDao.addFriend(friend, username);
                        //friends.postValue(userDao.getFriends(username));
                        message.postValue("friend added successfully");
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



    public void deleteFriend(String friend, MutableLiveData<List<ListUsersResponse>> friends, MutableLiveData<String> message) {
        Call<DefaultResponse> call = webServiceAPI.deleteFriend(username, friend, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG", "friend deleted successfully");
                    new Thread(() -> {
                        // update list of friends
                        userDao.deleteFriend(username, friend);
                        userDao.deleteFriend(friend, username);
                        //friends.postValue(userDao.getFriends(username));
                        message.postValue("friend deleted successfully");
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


    public void getFriends(MutableLiveData<List<ListUsersResponse>> friends, MutableLiveData<String> message, String username) {
        Call<List<ListUsersResponse>> call = webServiceAPI.getFriends(username, token, UserInfoManager.getUsername());

        call.enqueue(new Callback<List<ListUsersResponse>>() {
            @Override
            public void onResponse(Call<List<ListUsersResponse>> call, Response<List<ListUsersResponse>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        Log.d("DEBUG", "got friends");
                        Log.d("DEBUG", String.valueOf(response.body().size()));
                        //userDao.insertFriends(response.body());
                        friends.postValue(response.body());
                        message.postValue("Fetched all friends");
                    }).start();
                } else {
                    Log.d("DEBUG", "server failed");
                    new Thread(() -> message.postValue("An error occurred while fetching friends")).start();
                }
            }

            @Override
            public void onFailure(Call<List<ListUsersResponse>> call, Throwable t) {
                Log.d("DEBUG", t.getLocalizedMessage());
                new Thread(() -> message.postValue(t.getLocalizedMessage())).start();
            }
        });
    }

    public void areFriends(String user1, String user2, MutableLiveData<Boolean> areFriends) {
        Call<DefaultResponse> call = webServiceAPI.areFriends(user1, user2, token);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                new Thread(() -> {
                    String s = response.body().getMessage();
                    boolean b = s.equals("true");
                    areFriends.postValue(b);
                }).start();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                new Thread(() -> {
                    Log.d("DEBUG", t.getLocalizedMessage());
                    areFriends.postValue(false);
                }).start();

            }
        });
    }
}

