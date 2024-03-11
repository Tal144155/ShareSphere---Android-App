package com.example.facebook_like_android.utils.converters;

import androidx.room.TypeConverter;

import com.example.facebook_like_android.entities.post.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PostConverter {

    @TypeConverter
    public static List<Post> fromString(String value) {
        Type listType = new TypeToken<List<Post>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Post> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
