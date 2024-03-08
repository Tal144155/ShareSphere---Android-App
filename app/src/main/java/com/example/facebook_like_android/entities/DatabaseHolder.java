package com.example.facebook_like_android.entities;

import static com.example.facebook_like_android.ShareSphere.context;

import androidx.room.Room;

import com.example.facebook_like_android.entities.post.AppDB;

public class DatabaseHolder {
    private static volatile AppDB instance;

    public static AppDB getDatabase() {
        if (instance == null) {
            synchronized (DatabaseHolder.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDB.class, "PostsDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
