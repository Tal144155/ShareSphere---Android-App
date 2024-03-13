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
import com.example.facebook_like_android.responses.ListUsersResponse;
import com.example.facebook_like_android.utils.Base64Utils;
import com.example.facebook_like_android.utils.CircularOutlineUtil;
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
    private List<ListUsersResponse> requests;
    private RequestsViewModel viewModel;
    private boolean isMyProfile = false;

    public RequestsListAdapter(Context context, RequestsViewModel viewModel, String username) {
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
            final ListUsersResponse current = requests.get(position);
            holder.ivProfile.setImageBitmap(Base64Utils.decodeBase64ToBitmap(current.getPic()));
            String nickname = current.getFirst_name() + " " + current.getLast_name();
            holder.tvNickname.setText(nickname);
            holder.tvUsername.setText(current.getUser_name());
            setVisibility(holder);

            holder.btnAccept.setOnClickListener(v -> {
                viewModel.add(holder.tvUsername.getText().toString());
            });

            CircularOutlineUtil.applyCircularOutline(holder.ivProfile);

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

    public void setRequests(List<ListUsersResponse> requests) {
        this.requests = requests;
        notifyDataSetChanged();
    }
}
