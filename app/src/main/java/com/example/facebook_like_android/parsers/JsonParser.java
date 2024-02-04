package com.example.facebook_like_android.parsers;

import android.content.Context;

import com.example.facebook_like_android.entities.post.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<Post> parsePosts(Context context,  InputStream inputStream) {
        List<Post> posts = new ArrayList<>();
        try {
            // Read the JSON file from the assets folder
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }

            String json = stringBuilder.toString();

            // Parse the data
            JSONObject jsonData = new JSONObject(json);
            JSONArray postsArray = jsonData.getJSONArray("posts");

            // Parse each post in the array
            for (int i = 0; i < postsArray.length(); i++) {
                JSONObject postObject = postsArray.getJSONObject(i);

                String author = postObject.getString("author");
                String content = postObject.getString("content");
                // Pass the context to getResourceId method
                int pictureResourceId = getResourceId(context, postObject.getString("picture"), "drawable");
                int profilePhotoResourceId = getResourceId(context, postObject.getString("authorProfilePhoto"), "drawable");

                // Create a Post object and add it to the list
                Post post = new Post(author, content, pictureResourceId, profilePhotoResourceId);
                posts.add(post);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }


    // Method to get the resource ID based on the resource name and type
    private static int getResourceId(Context context, String resourceName, String resourceType) {
        return context.getResources().getIdentifier(resourceName, resourceType, context.getPackageName());
    }
}
