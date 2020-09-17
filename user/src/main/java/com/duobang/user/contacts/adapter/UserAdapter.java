package com.duobang.user.contacts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duobang.middleware.common.AppImageLoader;
import com.duobang.middleware.weight.AvatarView;
import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.user.R;
import com.duobang.user.core.login.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends BaseLibAdapter<User, UserAdapter.UserViewHolder> {

    public UserAdapter(List<User> dataList) {
        super(dataList);
    }

    @Override
    public UserViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new UserViewHolder(inflater.inflate(R.layout.user_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position, User item) {
        AppImageLoader.displayAvatar(item.getAvatar(), item.getNickname(), holder.avatar);
        holder.name.setText(item.getNickname());
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        AvatarView avatar;
        TextView name;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_user_item);
            name = itemView.findViewById(R.id.name_user_item);
        }
    }
}
