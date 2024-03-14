package com.example.facebook_like_android.utils.converters;

import androidx.room.TypeConverter;

import com.example.facebook_like_android.entities.Comment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CommentConverter {
    @TypeConverter
    public static List<Comment> fromString(String value) {
        Type listType = new TypeToken<List<Comment>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Comment> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
