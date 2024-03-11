package com.example.facebook_like_android.responses;

import com.example.facebook_like_android.entities.User;

import java.util.List;

public class UserResponse {
    String user_name;
    String first_name;
    String last_name;
    String pic;
    List<User> friend_requests;

    public String getUser_name() {
        return user_name;
    }

    public UserResponse(String user_name, String first_name, String last_name, String pic, List<User> friend_requests) {
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.pic = pic;
        this.friend_requests = friend_requests;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }



    public List<User> getFriend_requests() {
        return friend_requests;
    }

    public void setFriend_requests(List<User> friend_requests) {
        this.friend_requests = friend_requests;
    }

}
