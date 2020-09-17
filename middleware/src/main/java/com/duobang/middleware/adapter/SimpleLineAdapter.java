package com.duobang.middleware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duobang.middleware.R;
import com.duobang.pms_lib.adapter.BaseLibAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleLineAdapter extends BaseLibAdapter<String, SimpleLineAdapter.SimpleLineViewHolder> {

    public SimpleLineAdapter(List<String> dataList) {
        super(dataList);
    }

    @Override
    public SimpleLineViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new SimpleLineViewHolder(inflater.inflate(R.layout.simple_line_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleLineViewHolder holder, int position, String item) {
        holder.label.setText(item);
    }

    public static class SimpleLineViewHolder extends RecyclerView.ViewHolder {
        TextView label;

        public SimpleLineViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.text_simple_dialog_item);
        }
    }
}
