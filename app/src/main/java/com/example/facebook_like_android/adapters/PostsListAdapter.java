package com.example.facebook_like_android.adapters;

import android.content.Context;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.entities.post.buttons.LikeButton;
import com.example.facebook_like_android.entities.post.Post;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    // ViewHolder for holding views of individual posts
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvcontent;
        private final ImageView ivPic;
        private final ImageView ivProfile;
        private final TextView tvLikes;

        // Constructor to initialize views
        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvcontent = itemView.findViewById(R.id.tv_content);
            ivPic = itemView.findViewById(R.id.iv_pic);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvLikes = itemView.findViewById(R.id.tv_likes);
        }
    }

    private final LayoutInflater mInflater;  // LayoutInflater to inflate views
    private List<Post> posts;  // List to store posts

    // Constructor to initialize the LayoutInflater
    public PostsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // onCreateViewHolder: Inflates the layout for individual posts and creates a ViewHolder
    @NonNull
    @Override
    public PostsListAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    // onBindViewHolder: Binds data to the views in each ViewHolder
    @Override
    public void onBindViewHolder(@NonNull PostsListAdapter.PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvcontent.setText(current.getContent());
            holder.ivPic.setImageResource(current.getPic());
            holder.ivProfile.setImageResource(current.getProfile());
            holder.tvLikes.setText(String.valueOf(current.getLikes()));

            // Apply circular outline to profile image
            setupCircularOutline(holder);

            // Set onClick listener for like button
            setupLikeButtonClickListener(holder, current);
        }
    }

    // setupCircularOutline: Applies circular outline to the profile image
    private void setupCircularOutline(@NonNull PostViewHolder holder) {
        ImageView circularProfile = holder.itemView.findViewById(R.id.iv_profile);
        circularProfile.setClipToOutline(true);
        circularProfile.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 100f);
            }
        });
    }

    // setupLikeButtonClickListener: Sets onClick listener for the like button
    private void setupLikeButtonClickListener(@NonNull PostViewHolder holder, final Post current) {
        LikeButton like = new LikeButton(holder.itemView.findViewById(R.id.btn_like));
        like.setOnClickListener(v -> {
            current.like();
            holder.tvLikes.setText(String.valueOf(current.getLikes()));
        });
    }

    // getItemCount: Returns the number of items in the dataset
    @Override
    public int getItemCount() {
        if (posts != null) {
            return posts.size();
        }
        return 0;
    }

    // setPosts: Updates the dataset and notifies the adapter of the change
    public void setPosts(List<Post> list) {
        posts = list;
        notifyDataSetChanged();
    }

    // getPosts: Returns the current list of posts
    public List<Post> getPosts() {
        return posts;
    }
}
