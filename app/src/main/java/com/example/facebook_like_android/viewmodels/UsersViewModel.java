//package com.example.facebook_like_android.viewmodels;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.facebook_like_android.entities.User;
//import com.example.facebook_like_android.repositories.UserRepository;
//
//import java.util.List;
//
//public class UsersViewModel {
//
//    private UserRepository mRepository;
//    private LiveData<List<User>> friends;
//    private LiveData<List<User>> friendRequests;
//
//    public UsersViewModel() {
//        mRepository = new UserRepository();
//        friends = mRepository.getFriends();
//        friendRequests = mRepository.getFriendRequests();
//    }
//
//    public LiveData<List<User>> getFriendRequests() {
//        return friendRequests;
//    }
//
//    public LiveData<List<User>> getFriends() {
//        return friends;
//    }
//    public void addFriend(User user) { mRepository.addFriend(user); }
//    public void addFriendRequest(String friend, User user) { mRepository.addFriendRequest(friend, user); }
//    public void deleteFriend(User user) { mRepository.deleteFriend(user); }
//    public void deleteFriendRequest(User user) { mRepository.deleteFriendRequest(user); }
//
//    public void reloadFriends() { mRepository.reloadFriends(); }
//    public void reloadFriendRequests() { mRepository.reloadFriendRequests(); }
//}
