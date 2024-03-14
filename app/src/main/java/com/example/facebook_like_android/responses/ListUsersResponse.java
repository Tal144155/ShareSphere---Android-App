package com.example.facebook_like_android.responses;

public class ListUsersResponse {
    private String user_name;
    private String pic;
    private String first_name;
    private String last_name;

    public String getUser_name() {
        return user_name;
    }


    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public ListUsersResponse(String user_name, String pic, String first_name, String last_name) {
        this.user_name = user_name;
        this.pic = pic;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
