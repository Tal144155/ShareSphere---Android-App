package com.example.facebook_like_android.utils.converters;

import androidx.room.TypeConverter;

import com.example.facebook_like_android.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UserConverter {

    @TypeConverter
    public static List<User> fromString(String value) {
        Type listType = new TypeToken<List<User>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<User> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
