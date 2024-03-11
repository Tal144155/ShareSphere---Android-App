//package com.example.facebook_like_android.repositories;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.facebook_like_android.entities.User;
//import com.example.facebook_like_android.entities.UserAPI;
//import com.example.facebook_like_android.entities.UserDao;
//import com.example.facebook_like_android.entities.post.AppDB;
//import com.example.facebook_like_android.responses.LoginResponse;
//import com.example.facebook_like_android.utils.UserInfoManager;
//
//import java.util.List;
//
//public class UserRepository {
//    private UserDao userDao;
//    private UserListData friends;
//    private UserListData friendRequests;
//    private UserAPI userAPI;
//    private String username;
//
//    public UserRepository() {
//        AppDB db = AppDB.getDatabase();
//        userDao = db.userDao();
//        this.username = UserInfoManager.getUsername();
//        friends = new UserListData("friends");
//        friendRequests = new UserListData("friendRequests");
//        userAPI = new UserAPI(friends, friendRequests, userDao);
//    }
//
//
//    class UserListData extends MutableLiveData<List<User>> {
//        public UserListData(String type) {
//            super();
//            if (type.equals("friends"))
//                setValue(userDao.getFriends(username));
//            else
//                setValue(userDao.getFriendRequests(username));
//        }
//    }
//
//    public LiveData<List<User>> getFriends() {
//        return friends;
//    }
//    public LiveData<List<User>> getFriendRequests() {
//        return friendRequests;
//    }
//
//    public void addFriend(User user) {
//        userAPI.addFriend(user);
//    }
//
//    public void addFriendRequest(String friend, User user) {
//        userAPI.addFriendRequest(friend, user);
//    }
//
//    public void deleteFriend(User user) {
//        userAPI.deleteFriend(user);
//    }
//
//    public void deleteFriendRequest(User user) {
//        userAPI.deleteFriendRequest(user);
//    }
//    public void reloadFriends() { userAPI.getFriends(); }
//    public void reloadFriendRequests() { userAPI.getFriendRequests(); }
//
//}
