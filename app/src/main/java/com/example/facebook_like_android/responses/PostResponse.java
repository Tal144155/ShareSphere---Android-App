package com.example.facebook_like_android.responses;

public class PostResponse {
    private String _id;
    private String user_name;
    private String publish_date;
    private String first_name;
    private String last_name;
    private String content;
    private String profile;
    private String pic;
    private int likes;
    private int comments;

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getUser_name() {
        return user_name;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public PostResponse(String _id, String user_name, String first_name, String last_name, String content, String profile, String pic, int likes, int comments, String publish_date) {
        this._id = _id;
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.content = content;
        this.profile = profile;
        this.pic = pic;
        this.likes = likes;
        this.comments = comments;
        this.publish_date = publish_date;
    }


    public void addlike() {
        this.likes++;
    }

    public void unlike() {
        this.likes--;
    }
}
