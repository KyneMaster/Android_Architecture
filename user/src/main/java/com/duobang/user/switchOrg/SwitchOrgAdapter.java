package com.duobang.user.switchOrg;

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

public class SwitchOrgAdapter extends BaseLibAdapter<OrganizationInfo, SwitchOrgAdapter.SwitchOrgViewHolder> {

    public SwitchOrgAdapter(List<OrganizationInfo> dataList) {
        super(dataList);
    }

    @Override
    public SwitchOrgAdapter.SwitchOrgViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new SwitchOrgViewHolder(inflater.inflate(R.layout.switch_org_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SwitchOrgAdapter.SwitchOrgViewHolder holder, int position, OrganizationInfo item) {
        holder.name.setText(item.getName());
        if (item.isCheck()) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.INVISIBLE);
        }
    }

    class SwitchOrgViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView check;

        SwitchOrgViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_switch_org_item);
            check = itemView.findViewById(R.id.check_switch_org_item);
        }
    }
}
