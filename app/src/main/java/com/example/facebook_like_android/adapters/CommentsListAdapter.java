package com.example.facebook_like_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.entities.Comment;
import com.example.facebook_like_android.entities.post.PostManager;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.PostViewHolder> {

    // Inner class representing each individual comment view
    static class PostViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton profile;
        private final TextView author;
        private final TextView content;
        private final EditText etContent;
        private final ImageButton edit;
        private final ImageButton delete;
        private final ImageButton accept;
        private final LinearLayout actionBtns;

        // Constructor to initialize views
        private PostViewHolder(View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.btn_profile);
            author = itemView.findViewById(R.id.tv_nickname);
            content = itemView.findViewById(R.id.tv_content);
            etContent = itemView.findViewById(R.id.et_content);
            edit = itemView.findViewById(R.id.btn_edit);
            delete = itemView.findViewById(R.id.btn_delete);
            accept = itemView.findViewById(R.id.btn_accept);
            actionBtns = itemView.findViewById(R.id.action_btns);
        }
    }

    private final LayoutInflater inflater; // Layout inflater to inflate layout XML
    private final List<Comment> comments; // List of comments
    private final Activity context; // Activity context

    // Constructor to initialize adapter with context and position
    public CommentsListAdapter(Context context, int position) {
        inflater = LayoutInflater.from(context);
        PostManager postManager = PostManager.getInstance();
        comments = postManager.getComments(position); // Get comments for the post
        this.context = (Activity) context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate comment layout XML
        View itemView = inflater.inflate(R.layout.comment_layout, parent, false);
        return new PostViewHolder(itemView); // Return new PostViewHolder with inflated view
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (comments != null) {
            Comment current = comments.get(position); // Get current comment
            holder.profile.setImageBitmap(current.getProfileBitmap()); // Set profile image
            holder.content.setText(current.getContent()); // Set content text
            holder.author.setText(current.getAuthor()); // Set author text

            CircularOutlineUtil.applyCircularOutline(holder.profile); // Apply circular outline to profile image

            // Show action buttons if current user is the author of the comment
            if (UserInfoManager.getUsername().equals(current.getUsername()))
                holder.actionBtns.setVisibility(View.VISIBLE);

            // Set onClickListener for delete button
            holder.delete.setOnClickListener(v -> deleteComment(position));

            // Call method to handle editing comment
            editComment(holder, position);
        }
    }

    // Method to handle editing a comment
    private void editComment(PostViewHolder holder, int position) {
        // Set onClickListener for edit button
        holder.edit.setOnClickListener(v -> {
            holder.edit.setVisibility(View.GONE); // Hide edit button
            holder.accept.setVisibility(View.VISIBLE); // Show accept button
            holder.etContent.setText(holder.content.getText()); // Set edit text content to current comment content
            holder.content.setVisibility(View.GONE); // Hide content text
            holder.etContent.setVisibility(View.VISIBLE); // Show edit text
        });

        // Set onClickListener for accept button
        holder.accept.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(holder.etContent.getText())) {
                String newContent = holder.etContent.getText().toString(); // Get edited content
                updateComment(position, newContent); // Call method to update comment
                holder.accept.setVisibility(View.GONE); // Hide accept button
                holder.edit.setVisibility(View.VISIBLE); // Show edit button
                holder.etContent.setVisibility(View.GONE); // Hide edit text
                holder.content.setVisibility(View.VISIBLE); // Show content text
            }
        });
    }

    // Method to update comment content
    private void updateComment(int position, String content) {
        comments.get(position).setContent(content); // Update content in list
        notifyItemChanged(position); // Notify adapter that comment at given position has changed
    }

    // Method to delete comment
    private void deleteComment(int position) {
        comments.remove(position); // Remove comment from list
        notifyItemRemoved(position); // Notify adapter that comment at given position has been removed
    }

    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size(); // Return number of comments
        return 0;
    }

    // Method to add a new comment to the list
    public void addComment(Comment comment) {
        if (!comment.getContent().equals("")) { // Check if comment content is not empty
            comments.add(comment); // Add comment to list
            notifyItemChanged(comments.indexOf(comment)); // Notify adapter that a new comment has been added
        }
    }

}
