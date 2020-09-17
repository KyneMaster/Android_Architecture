package com.duobang.user.contacts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.user.R;
import com.duobang.user.core.org.OrgGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrgGroupAdapter extends BaseLibAdapter<OrgGroup, OrgGroupAdapter.OrgGroupViewHolder> {

    public OrgGroupAdapter(List<OrgGroup> dataList) {
        super(dataList);
    }

    @Override
    public OrgGroupViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new OrgGroupViewHolder(inflater.inflate(R.layout.org_group_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OrgGroupViewHolder holder, int position, OrgGroup item) {
        holder.name.setText(item.getName());
    }

    class OrgGroupViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        OrgGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_org_group_item);
        }
    }
}
