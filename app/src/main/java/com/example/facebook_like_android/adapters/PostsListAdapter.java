package com.example.facebook_like_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.entities.post.Post;
import com.example.facebook_like_android.daos.PostDao;
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
    static class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvcontent;
        private final ImageView ivPic;
        private final ImageView ivProfile;
        private final TextView tvLikes;
        private final ImageButton like;
        private final ImageButton comment;
        private final ImageButton share;
        private final LinearLayout shareOptions;
        private final ImageButton edit;
        private final ImageButton delete;
        private final TextView date;

        // Constructor to initialize views
        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvcontent = itemView.findViewById(R.id.tv_content);
            ivPic = itemView.findViewById(R.id.iv_pic);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvLikes = itemView.findViewById(R.id.tv_likes);
            like = itemView.findViewById(R.id.btn_like);
            comment = itemView.findViewById(R.id.btn_comment);
            share = itemView.findViewById(R.id.btn_share);
            shareOptions = itemView.findViewById(R.id.share_options);
            edit = itemView.findViewById(R.id.btn_edit);
            delete = itemView.findViewById(R.id.btn_delete);
            date = itemView.findViewById(R.id.tv_date);
        }
    }

    private final LayoutInflater mInflater;  // LayoutInflater to inflate views
    private final PostManager postManager = PostManager.getInstance();
    //private final List<Post> posts = postManager.getPosts();  // List to store posts
    private List<Post> posts;
    private int visibility = View.GONE;
    private OnEditClickListener editClickListener;
    private OnEditClickListener.OnDeleteClickListener deleteClickListener;
    private boolean isProfile = false;
    private final Activity activity;
    private String username;
    private LikeButton likeButton;
    private PostDao postDao;
    public final static int COMMENTS_REQUEST_CODE = 123;

    // Constructor to initialize the LayoutInflater
    public PostsListAdapter(Context context, PostDao postDao) {
        mInflater = LayoutInflater.from(context);
        this.activity = (Activity) context;
        this.postDao = postDao;
        this.posts = postManager.getPosts();
        //this.posts = postDao.index();
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
            holder.date.setText(current.getPublishDate());

            // Differentiating between hard-coded pics and user-uploaded pics
            if (current.getPicID() == Post.NOT_RES)
                holder.ivPic.setImageBitmap(current.getPicBitmap());
            else
                holder.ivPic.setImageResource(current.getPicID());
            if (current.getProfileID() == Post.NOT_RES)
                holder.ivProfile.setImageBitmap(current.getProfileBitmap());
            else
                holder.ivProfile.setImageResource(current.getProfileID());

            holder.tvLikes.setText(String.valueOf(current.getLikes()));

            // Apply circular outline to profile image using the utility class
            CircularOutlineUtil.applyCircularOutline(holder.ivProfile);

            // Set onClick listener for buttons
            setupLikeButtonClickListener(holder, position);
            setOnEditClickListener(holder, position);
            setOnShareClickListener(holder, position);
            setOnDeleteClickListener(holder, position);
            setOnCommentClickListener(holder, position);

            setVisibility(holder);
            getMyPosts(holder, position);
        }
    }

    // Sets onClick listener for the share button
    private void setOnShareClickListener(@NonNull PostViewHolder holder, int position) {
        holder.share.setOnClickListener(v ->
                holder.shareOptions.setVisibility(View.VISIBLE));
    }

    // Sets onClick listener for the delete button
    private void setOnDeleteClickListener(@NonNull PostViewHolder holder, int position) {
        holder.delete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(position);
            }
        });
    }

    // Sets onClick listener for the comment button
    private void setOnCommentClickListener(@NonNull PostViewHolder holder, int position) {
        holder.comment.setOnClickListener(v -> {
            Intent comment = new Intent(activity, Comments.class);
            comment.putExtra("position", position);
            activity.startActivityForResult(comment, COMMENTS_REQUEST_CODE);
        });
    }

    // Sets onClick listener for the edit button
    private void setOnEditClickListener(@NonNull PostViewHolder holder, int position) {
        holder.edit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position);
            }
        });
    }

    private void setVisibility(PostViewHolder holder) {
        // Set the visibility of edit and delete
        ImageButton edit = holder.edit;
        edit.setVisibility(visibility);
        ImageButton delete = holder.delete;
        delete.setVisibility(visibility);
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
            Post post = posts.get(position);
            if (!newContent.isEmpty())
                post.setContent(newContent); // Update the content
            if (newPic != null)
                post.setPic(newPic);
            postDao.update(post);
            postManager.updatePost(position, post);
            notifyItemChanged(position);// Notify adapter of the change at this position
        }
    }
    public void deletePost(int position) {
        if (posts != null && position >= 0 && position < posts.size()) {
            Post post = posts.remove(position);
            postDao.delete(post);
            //postManager.removePost(postManager.getPosts().get(position));
            notifyItemRemoved(position); // Notify adapter this post was removed
        }
    }
    public void addPost(Post post) {
        postManager.addPost(post);
        postDao.insert(post);
        //posts.add(post);
        refreshFeed();
        notifyItemInserted(posts.indexOf(post)); // Notify adapter this post was inserted
    }

    // Method to set the click listener
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnEditClickListener.OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    // Shows only the posts of a given user (used in Profile)
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
        //posts.clear();
        //posts.addAll(postDao.index());
        notifyDataSetChanged();
    }

    // Initialising the list of posts from the JSON file
    public void initPosts() {
        if (posts.isEmpty()) {
            // Call the method to read and parse the JSON file
            try {
                JsonParser.parsePosts(activity.getAssets().open("posts.json"), activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        postManager.setPosts(posts);
    }


}
