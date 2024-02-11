package com.example.facebook_like_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.adapters.PostsListAdapter;
import com.example.facebook_like_android.databinding.ActivityFeedBinding;
import com.example.facebook_like_android.entities.Post;
import com.example.facebook_like_android.style.ThemeMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {
    private ActivityFeedBinding binding;
    private final ThemeMode mode = ThemeMode.getInstance();
    private List<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView lstPosts = binding.lstPosts;
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));


        // Call the method to read and parse the JSON file
        parseJsonFile();

        adapter.setPosts(posts);

        binding.btnMenu.setOnClickListener(v -> startActivity(new Intent(this, Menu.class)));
        binding.btnSearch.setOnClickListener(v -> startActivity(new Intent(this, Search.class)));
        binding.btnChangeMode.setOnClickListener(v -> mode.changeTheme(this));

    }

    private void parseJsonFile() {
        try (InputStream inputStream = getAssets().open("posts.json")) {
            // Read the JSON file from the assets folder
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

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

                // Create a Post object and add it to the list
                Post post = new Post(author, content,
                        getResourceId(postObject.getString("picture"), "drawable"),
                        getResourceId(postObject.getString("authorProfilePhoto"), "drawable"));
                posts.add(post);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private int getResourceId(String resourceName, String resourceType) {
        return getResources().getIdentifier(resourceName, resourceType, getPackageName());
    }
}