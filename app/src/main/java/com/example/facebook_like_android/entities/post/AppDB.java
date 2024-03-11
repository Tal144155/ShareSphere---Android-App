package com.example.facebook_like_android.entities.post;

import static com.example.facebook_like_android.ShareSphere.context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.entities.UserDao;

@Database(entities = {Post.class, Comment.class, User.class}, version = 9)

public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
    public abstract UserDao userDao();

    private static volatile AppDB instance;

    public static AppDB getDatabase() {
        if (instance == null) {
            synchronized (AppDB.class) {
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