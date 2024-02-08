package com.example.facebook_like_android.parsers;

import android.net.Uri;

import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.PostManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonParser {
    static PostManager postManager = PostManager.getInstance();

    public static void parsePosts(InputStream inputStream) {

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

                String username = postObject.getString("username");
                String author = postObject.getString("author");
                String content = postObject.getString("content");
                String picture = postObject.getString("picture");
                String profile = postObject.getString("authorProfilePhoto");

                // Create a Post object and add it to the list
                Post post = new Post(username, author, content, Uri.parse(picture), Uri.parse(profile));
                postManager.addPost(post);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
