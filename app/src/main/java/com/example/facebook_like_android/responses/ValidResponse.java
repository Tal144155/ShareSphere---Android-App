package com.example.facebook_like_android.responses;

public class ValidResponse {

    private boolean isValid;
    private String error;

    public ValidResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public ValidResponse(boolean isValid, String error) {
        this.isValid = isValid;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
