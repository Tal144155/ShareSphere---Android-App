package com.example.facebook_like_android.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.facebook_like_android.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE user_name = :username")
    User get(String username);

    // Retrieve friends of a specific user by username
    @Query("SELECT user_name, friends FROM user WHERE user_name = :username")
    List<User> getFriends(String username);

    // Retrieve friend requests of a specific user by username
    @Query("SELECT user_name, friend_requests FROM user WHERE user_name = :username")
    List<User> getFriendRequests(String username);

    // Check if 2 users are friends
    @Query("SELECT COUNT(*) FROM user WHERE user_name = :user1 AND :user2 IN (SELECT friends FROM user WHERE user_name = :user1)")
    int areFriends(String user1, String user2);

    // Check if the second user has requested to befriend the first
    @Query("SELECT COUNT(*) FROM user WHERE user_name = :user1 AND :user2 IN (SELECT friend_requests FROM user WHERE user_name = :user1)")
    int areFriendRequests(String user1, String user2);

    // Adds the first user to the second user friends
    @Query("UPDATE user SET friends = friends || :user1 WHERE user_name = :user2")
    void addFriend(String user1, String user2);


    // Adds a friend request from user1 to user2
    @Query("UPDATE user SET friend_requests = friend_requests || :user1 WHERE user_name = :user2")
    void addFriendRequest(String user1, String user2);

    // Removes the specified friend from the friend list
    @Query("UPDATE user SET friends = REPLACE(friends, :friendToRemove, '') WHERE user_name = :currentUser")
    void deleteFriend(String currentUser, String friendToRemove);

    // Removes the specified friend-request from the friend requests list
    @Query("UPDATE user SET friend_requests = REPLACE(friend_requests, :friendToRemove, '') WHERE user_name = :currentUser")
    void deleteFriendRequest(String currentUser, String friendToRemove);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFriends(List<User> friends);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFriendRequests(List<User> friendRequests);


}
