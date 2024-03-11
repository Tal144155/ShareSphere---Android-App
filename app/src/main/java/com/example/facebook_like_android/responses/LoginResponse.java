package com.example.facebook_like_android.responses;

public class LoginResponse {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public interface ILoginResponse {
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable t);
    }
}
