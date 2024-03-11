//package com.example.facebook_like_android.entities;
//
//import static com.example.facebook_like_android.responses.LoginResponse.*;
//
//import android.util.Log;
//
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.facebook_like_android.R;
//import com.example.facebook_like_android.ShareSphere;
//import com.example.facebook_like_android.entities.post.WebServiceAPI;
//import com.example.facebook_like_android.repositories.UserRepository;
//import com.example.facebook_like_android.responses.DefaultResponse;
//import com.example.facebook_like_android.responses.LoginResponse;
//import com.example.facebook_like_android.retrofit.RetrofitClient;
//import com.example.facebook_like_android.utils.UserInfoManager;
//
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class UserAPI {
//    private MutableLiveData<List<User>> friends;
//    private MutableLiveData<List<User>> friendRequests;
//    private UserDao userDao;
//    Retrofit retrofit;
//    WebServiceAPI webServiceAPI;
//    private String username;
//
//    public UserAPI(MutableLiveData<List<User>> friends, MutableLiveData<List<User>> friendRequests, UserDao userDao) {
//        this.friends = friends;
//        this.friendRequests = friendRequests;
//        this.userDao = userDao;
//        this.username = UserInfoManager.getUsername();
//
//        retrofit = RetrofitClient.getRetrofit();
//
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//
////    // Method to get users from the server
////    public void getUsers() {
////        Call<List<User>> call = webServiceAPI.getUsers();
////        call.enqueue(new Callback<List<User>>() {
////            @Override
////            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
////                new Thread(() -> {
////                    userDao.clear();
////                    userDao.insertList(response.body());
////                    userListData.postValue(userDao.index());
////                }).start();
////            }
////
////            @Override
////            public void onFailure(Call<List<User>> call, Throwable t) {
////                // Handle failure
////            }
////        });
////    }
//
//
//    // Method to update user information
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
//
//    public void addFriend(User user) {
//        Call<DefaultResponse> call = webServiceAPI.approveFriendRequest(username, user.getUsername());
//
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                Log.d("DEBUG", "friend added successfully");
//                // update list of friends
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void addFriendRequest(String friend, User user) {
//        Call<DefaultResponse> call = webServiceAPI.friendRequest(friend, user.getUsername());
//
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                Log.d("DEBUG", "request sent successfully");
//                // update list of friend requests
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void deleteFriend(User user) {
//        Call<DefaultResponse> call = webServiceAPI.deleteFriend(username, user.getUsername());
//
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                Log.d("DEBUG", "friend deleted successfully");
//                new Thread(() -> {
//                    // update list of friends
//                    userDao.deleteFriend(username, user.getUsername());
//                    friends.postValue(userDao.getFriends(username));
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void deleteFriendRequest(User user) {
//        Call<DefaultResponse> call = webServiceAPI.deleteFriend(username, user.getUsername());
//
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                Log.d("DEBUG", "request deleted successfully");
//                new Thread(() -> {
//                    // update list of friend requests
//                    userDao.deleteFriendRequest(username, user.getUsername());
//                    friendRequests.postValue(userDao.getFriendRequests(username));
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void getFriends() {
//        Call<List<User>> call = webServiceAPI.getFriends(username);
//
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                new Thread(() -> {
//                    userDao.clearFriends();
//                    userDao.setFriends(response.body());
//                    friends.postValue(userDao.getFriends(username));
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void getFriendRequests() {
//        Call<List<User>> call = webServiceAPI.getFriends(username);
//
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                new Thread(() -> {
//                    userDao.clearFriendRequests();
//                    userDao.setFriendRequests(response.body());
//                    friends.postValue(userDao.getFriendRequests(username));
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//
//            }
//        });
//    }
//}
//
