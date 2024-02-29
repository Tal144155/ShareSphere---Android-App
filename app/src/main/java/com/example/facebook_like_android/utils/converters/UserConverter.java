package com.example.facebook_like_android.utils.converters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? null : new ArrayList<>(Arrays.asList(value.split(",")));
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item);
            sb.append(",");
        }
        return sb.toString();
    }
}
