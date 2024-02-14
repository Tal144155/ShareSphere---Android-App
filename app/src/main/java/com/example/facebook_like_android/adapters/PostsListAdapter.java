package com.example.facebook_like_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.entities.post.PostManager;
import com.example.facebook_like_android.entities.post.buttons.LikeButton;
import com.example.facebook_like_android.entities.post.buttons.OnEditClickListener;
import com.example.facebook_like_android.feed.Comments;
import com.example.facebook_like_android.parsers.JsonParser;
import com.example.facebook_like_android.utils.CircularOutlineUtil;

import java.io.IOException;
import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    // ViewHolder for holding views of individual posts
    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvcontent;
        private final ImageView ivPic;
        private final ImageView ivProfile;
        private final TextView tvLikes;
        private final EditText etContent;
        private final Button changeImg;
        private final Button update;
        private final ImageButton like;
        private final ImageButton comment;
        private final ImageButton share;
        private final LinearLayout shareOptions;
        private final ImageButton edit;
        private final ImageButton delete;

        // Constructor to initialize views
        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvcontent = itemView.findViewById(R.id.tv_content);
            ivPic = itemView.findViewById(R.id.iv_pic);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvLikes = itemView.findViewById(R.id.tv_likes);
            etContent = itemView.findViewById(R.id.et_content);
            changeImg = itemView.findViewById(R.id.btn_changeImg);
            update = itemView.findViewById(R.id.btn_update);
            like = itemView.findViewById(R.id.btn_like);
            comment = itemView.findViewById(R.id.btn_comment);
            share = itemView.findViewById(R.id.btn_share);
            shareOptions = itemView.findViewById(R.id.share_options);
            edit = itemView.findViewById(R.id.btn_edit);
            delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    private final LayoutInflater mInflater;  // LayoutInflater to inflate views
    private PostManager postManager = PostManager.getInstance();
    private List<Post> posts = postManager.getPosts();  // List to store posts
    private int visibility = View.GONE;
    private OnEditClickListener editClickListener;
    private OnEditClickListener.OnDeleteClickListener deleteClickListener;
    private boolean isProfile = false;
    private Activity activity;
    private String username;
    private LikeButton likeButton;
    public final static int COMMENTS_REQUEST_CODE = 123;

    // Constructor to initialize the LayoutInflater
    public PostsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.activity = (Activity) context;
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
            if (current.getPicID() == Post.NOT_RES)
                holder.ivPic.setImageBitmap(current.getPic());
            else
                holder.ivPic.setImageResource(current.getPicID());
            if (current.getProfileID() == Post.NOT_RES)
                holder.ivProfile.setImageBitmap(current.getProfile());
            else
                holder.ivProfile.setImageResource(current.getProfileID());
            holder.tvLikes.setText(String.valueOf(current.getLikes()));

            // Apply circular outline to profile image using the utility class
            CircularOutlineUtil.applyCircularOutline(holder.ivProfile);

            // Set onClick listener for like button
            setupLikeButtonClickListener(holder, position);

            setVisibility(holder);

            holder.edit.setOnClickListener(v -> {
                if (editClickListener != null) {
                    holder.update.setVisibility(View.VISIBLE);
                    holder.changeImg.setVisibility(View.VISIBLE);
                    editClickListener.onEditClick(position);
                }
            });

            holder.delete.setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            });

            holder.share.setOnClickListener(v ->
                    holder.shareOptions.setVisibility(View.VISIBLE));

            holder.comment.setOnClickListener(v -> {
                Intent comment = new Intent(activity, Comments.class);
                comment.putExtra("position", position);
                activity.startActivityForResult(comment, COMMENTS_REQUEST_CODE);
            });

            getMyPosts(holder, position);
        }
    }

    private void setVisibility(PostViewHolder holder) {
        // Set the visibility of edit and delete
        ImageButton edit = holder.edit;
        edit.setVisibility(visibility);
        ImageButton delete = holder.delete;
        delete.setVisibility(visibility);
        holder.etContent.setVisibility(View.GONE);
        holder.update.setVisibility(View.GONE);
        holder.changeImg.setVisibility(View.GONE);
    }

    // setupLikeButtonClickListener: Sets onClick listener for the like button
    private void setupLikeButtonClickListener(@NonNull PostViewHolder holder, int position) {
        likeButton = new LikeButton(posts.get(position).isLiked());
        likeButton.updateAppearance(holder.like);
        holder.like.setOnClickListener(v -> {
            likeButton.like(holder.like);
            posts.get(position).like();
            holder.tvLikes.setText(String.valueOf(posts.get(position).getLikes()));
            notifyItemChanged(position); // Notify adapter the button should change
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
    public void updatePost(int position, String newContent, Bitmap newPic) {
        if (posts != null && position >= 0 && position < posts.size()) {
            Post post = postManager.getPosts().get(position);
            if (!newContent.isEmpty())
                post.setContent(newContent); // Update the content
            if (newPic != null)
                post.setPic(newPic);
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
    public void addPost(Post post) {
        postManager.addPost(post);
        notifyItemInserted(postManager.getPosition(post)); // Notify adapter this post was inserted
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

    public void initPosts() {
        if (posts.size() == 0) {
            // Call the method to read and parse the JSON file
            try {
                JsonParser.parsePosts(activity.getAssets().open("posts.json"), activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
