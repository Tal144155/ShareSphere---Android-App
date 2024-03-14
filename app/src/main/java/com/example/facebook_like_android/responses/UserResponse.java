package com.example.facebook_like_android.responses;

public class UserResponse {
    String user_name;
    String first_name;
    String last_name;
    String pic;

    public String getUser_name() {
        return user_name;
    }

    public UserResponse(String user_name, String first_name, String last_name, String pic) {
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.pic = pic;
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

}
