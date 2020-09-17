package com.duobang.user.contacts.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.user.R;
import com.duobang.user.core.org.OrganizationInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizationAdapter extends BaseLibAdapter<OrganizationInfo, OrganizationAdapter.OrganizationViewHolder> {

    public OrganizationAdapter(List<OrganizationInfo> dataList) {
        super(dataList);
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new OrganizationViewHolder(inflater.inflate(R.layout.org_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OrganizationViewHolder holder, int position, OrganizationInfo item) {
        holder.name.setText(item.getName());
        if (item.isCheck()){
            holder.name.setTextColor(Color.parseColor("#3f3f3f"));
        } else {
            holder.name.setTextColor(Color.parseColor("#8f8f8f"));
        }
        if (item.isEdit() && item.isCheck()){
            holder.sign.setImageResource(R.drawable.ic_ok_blue);
        } else if (!item.isEdit()){
            holder.sign.setImageResource(R.drawable.ic_arrow_right);
        } else {
            holder.sign.setImageDrawable(null);
        }
    }

    public class OrganizationViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView sign;

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_org_list_item);
            sign = itemView.findViewById(R.id.sign_org_list_item);
        }
    }
}
