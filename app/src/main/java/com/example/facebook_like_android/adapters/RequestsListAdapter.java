package com.example.facebook_like_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.entities.User;
import com.example.facebook_like_android.viewmodels.RequestsViewModel;

import java.util.List;

public class RequestsListAdapter extends RecyclerView.Adapter<RequestsListAdapter.RequestViewHolder> {

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNickname;
        private final TextView tvUsername;
        private final ImageView ivProfile;
        private final ImageButton btnAccept;
        private final ImageButton btnReject;

        private RequestViewHolder(View view) {
            super(view);
            tvNickname = view.findViewById(R.id.tv_nickname);
            tvUsername = view.findViewById(R.id.tv_username);
            ivProfile = view.findViewById(R.id.iv_profile);
            btnAccept = view.findViewById(R.id.btn_accept);
            btnReject = view.findViewById(R.id.btn_reject);
        }
    }

    private final LayoutInflater mInflater;
    private List<User> requests;
    private RequestsViewModel viewModel;
    private boolean isMyProfile = false;

    public RequestsListAdapter(Context context, RequestsViewModel viewModel) {
        mInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_layout, parent,false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        if (requests != null) {
            final User current = requests.get(position);
            holder.ivProfile.setImageBitmap(current.getProfileBitmap());
            String nickname = current.getFirstname() + " " + current.getLastname();
            holder.tvNickname.setText(nickname);
            holder.tvUsername.setText(current.getUsername());
            setVisibility(holder);

            holder.btnAccept.setOnClickListener(v -> {
                viewModel.add(holder.tvUsername.getText().toString());
            });

            holder.btnReject.setOnClickListener(v -> viewModel.delete(holder.tvUsername.getText().toString()));
        }
    }

    public void myProfile() {
        isMyProfile = true;
    }

    private void setVisibility(RequestViewHolder holder) {
        if (isMyProfile) {
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);
        } else {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        if (requests == null)
            return 0;
        return requests.size();
    }

    public void setRequests(List<User> requests) {
        this.requests = requests;
    }
}
