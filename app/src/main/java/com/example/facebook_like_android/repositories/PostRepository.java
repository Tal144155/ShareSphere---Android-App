package com.example.facebook_like_android.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.facebook_like_android.ShareSphere;
import com.example.facebook_like_android.entities.post.AppDB;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.PostAPI;
import com.example.facebook_like_android.entities.post.PostDao;

import java.util.LinkedList;
import java.util.List;

public class PostRepository {
    private PostDao postDao;
    private PostListData postListData;
    private PostAPI postAPI;

    public PostRepository() {
        AppDB db = Room.databaseBuilder(ShareSphere.context, AppDB.class, "local-database").build();
        postDao = db.postDao();
        postListData = new PostListData();
        postAPI = new PostAPI(postListData, postDao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                postListData.postValue(postDao.index());
            }).start();
        }
    }

    public LiveData<List<Post>> getAll() { return postListData; }

    public void add(final Post post) { postAPI.add(post); }

    public void delete(final Post post) { postAPI.delete(post); }

    public void reload() {postAPI.get();}

}
