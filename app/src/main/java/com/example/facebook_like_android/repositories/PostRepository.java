package com.example.facebook_like_android.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.facebook_like_android.db.AppDB;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.api.PostAPI;
import com.example.facebook_like_android.daos.PostDao;
import com.example.facebook_like_android.entities.post.PostManager;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;

public class PostRepository {
    private List<Post> posts = PostManager.getInstance().getPosts();
    private PostDao postDao;
    private PostListData postListData;
    private PostAPI postAPI;
    private String username;

    public PostRepository() {
        AppDB db = AppDB.getDatabase();
        postDao = db.postDao();
        this.username = UserInfoManager.getUsername();
        postListData = new PostListData();
        postAPI = new PostAPI(postListData, postDao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            // Get the posts from local database
            // setValue(postDao.index());

            // TODO: remove this line!!
            // Get static posts
            setValue(posts);


            // This shouldn't be here!!
            Log.d("REPO", "postAPI is null: " + (postAPI == null));
            postAPI = new PostAPI(postListData, postDao);

            // Send a request to server for new posts
            new Thread(() -> postAPI.get()).start();
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> postListData.postValue(postDao.index())).start();
        }
    }

    public LiveData<List<Post>> getAll() { return postListData; }

    public void add(final Post post) { postAPI.add(post); }

    public void delete(final Post post) { postAPI.delete(post); }

    public void reload() {postAPI.get();}

}
