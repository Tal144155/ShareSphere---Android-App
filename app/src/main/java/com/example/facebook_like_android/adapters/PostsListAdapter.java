package com.example.facebook_like_android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.PostManager;
import com.example.facebook_like_android.entities.post.buttons.LikeButton;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.utils.CircularOutlineUtil;

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
    private PostManager postManager = PostManager.getInstance();
    private List<Post> posts = postManager.getPosts();  // List to store posts
    private int visibility = View.GONE;
    private OnEditClickListener editClickListener;
    private OnEditClickListener.OnDeleteClickListener deleteClickListener;
    private boolean isProfile = false;
    private String username;

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
            holder.ivPic.setImageURI(current.getPic());
            holder.ivProfile.setImageURI(current.getProfile());
            holder.tvLikes.setText(String.valueOf(current.getLikes()));

            // Apply circular outline to profile image using the utility class
            CircularOutlineUtil.applyCircularOutline(holder.itemView.findViewById(R.id.iv_profile));

            // Set onClick listener for like button
            setupLikeButtonClickListener(holder, position);

            setVisibility(holder);

            holder.itemView.findViewById(R.id.btn_edit).setOnClickListener(v -> {
                if (editClickListener != null) {
                    holder.itemView.findViewById(R.id.btn_update).setVisibility(View.VISIBLE);
                    holder.itemView.findViewById(R.id.btn_changeImg).setVisibility(View.VISIBLE);
                    editClickListener.onEditClick(position);
                }
            });

            holder.itemView.findViewById(R.id.btn_delete).setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            });

            getMyPosts(holder, position);
        }
    }

    private void setVisibility(PostViewHolder holder) {
        // Set the visibility of edit and delete
        ImageButton edit = holder.itemView.findViewById(R.id.btn_edit);
        edit.setVisibility(visibility);
        ImageButton delete = holder.itemView.findViewById(R.id.btn_delete);
        delete.setVisibility(visibility);
        holder.itemView.findViewById(R.id.et_content).setVisibility(View.GONE);
        holder.itemView.findViewById(R.id.btn_update).setVisibility(View.GONE);
        holder.itemView.findViewById(R.id.btn_changeImg).setVisibility(View.GONE);
    }

    // setupLikeButtonClickListener: Sets onClick listener for the like button
    private void setupLikeButtonClickListener(@NonNull PostViewHolder holder, int position) {
        LikeButton like = new LikeButton(holder.itemView.findViewById(R.id.btn_like));
        like.setOnClickListener(v -> {
            posts.get(position).like();
            holder.tvLikes.setText(String.valueOf(posts.get(position).getLikes()));
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

    public void setFeedVisibility() {
        this.visibility = View.GONE;
        notifyDataSetChanged(); // Notify the adapter to update the views
    }
    public void setProfileVisibility() {
        this.visibility = View.VISIBLE;
        this.isProfile = true;
        notifyDataSetChanged(); // Notify the adapter to update the views
    }
    // Method to update post content
    public void updatePost(int position, String newContent, Uri newPic) {
        if (posts != null && position >= 0 && position < posts.size()) {
            Post post = postManager.getPosts().get(position);
            if (!newContent.isEmpty())
                post.setContent(newContent); // Update the content
            if (newPic != null)
                post.setPic(Uri.parse(newPic.toString()));
            postManager.updatePost(position, post);
            notifyItemChanged(position);// Notify adapter of the change at this position
        }
    }
    public void deletePost(int position) {
        if (posts != null && position >= 0 && position < posts.size()) {
            postManager.removePost(postManager.getPosts().get(position));
            notifyItemRemoved(position); // Notify adapter this post was removed
        }
    }

    // Method to set the click listener
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnEditClickListener.OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public void getMyPosts(PostViewHolder holder, int position) {
        if (!isProfile)
            return;
        if (!posts.get(position).getUsername().equals(this.username))
            holder.itemView.findViewById(R.id.post).setVisibility(View.GONE);
        else
            holder.itemView.findViewById(R.id.post).setVisibility(View.VISIBLE);
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void refreshFeed() {
        notifyDataSetChanged();
    }



}
