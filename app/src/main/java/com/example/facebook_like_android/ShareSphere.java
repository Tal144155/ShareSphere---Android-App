package com.example.facebook_like_android;

import android.app.Application;
import android.content.Context;

public class ShareSphere extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
