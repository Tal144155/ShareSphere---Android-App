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
import com.example.facebook_like_android.entities.post.Comment;
import com.example.facebook_like_android.entities.post.PostManager;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
import com.example.facebook_like_android.utils.UserInfoManager;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageButton profile;
        private TextView author;
        private TextView content;
        private EditText etContent;
        private ImageButton edit;
        private ImageButton delete;
        private ImageButton accept;
        private LinearLayout actionBtns;
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

    private LayoutInflater inflater;
    private PostManager postManager = PostManager.getInstance();
    private List<Comment> comments;
    private Activity context;

    public CommentsListAdapter(Context context, int position) {
        inflater = LayoutInflater.from(context);
        comments = postManager.getComments(position);
        this.context = (Activity) context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.comment_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (comments != null) {
            Comment current = comments.get(position);
            holder.profile.setImageBitmap(current.getProfile());
            holder.content.setText(current.getContent());
            holder.author.setText(current.getAuthor());

            CircularOutlineUtil.applyCircularOutline(holder.profile);

            if (UserInfoManager.getUsername(context).equals(current.getUsername()))
                holder.actionBtns.setVisibility(View.VISIBLE);

            holder.delete.setOnClickListener(v -> deleteComment(position));

            editComment(holder, position);

        }
    }
    private void editComment(PostViewHolder holder, int position) {
        holder.edit.setOnClickListener(v -> {
            holder.edit.setVisibility(View.GONE);
            holder.accept.setVisibility(View.VISIBLE);
            holder.etContent.setText(holder.content.getText());
            holder.content.setVisibility(View.GONE);
            holder.etContent.setVisibility(View.VISIBLE);
        });

        holder.accept.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(holder.etContent.getText())) {
                String newContent = holder.etContent.getText().toString();
                updateComment(position, newContent);
                holder.accept.setVisibility(View.GONE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.etContent.setVisibility(View.GONE);
                holder.content.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateComment(int position, String content) {
        comments.get(position).setContent(content);
        notifyItemChanged(position); // Notify adapter we edited a comment
    }

    private void deleteComment(int position) {
        comments.remove(position);
        notifyItemRemoved(position); // Notify adapter we deleted a comment
    }

    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size();
        return 0;
    }
    public void addComment(Comment comment) {
        if (!comment.getContent().equals("")) {
            comments.add(comment);
            notifyItemChanged(comments.indexOf(comment)); // Notify adapter we added a comment
        }
    }

}
