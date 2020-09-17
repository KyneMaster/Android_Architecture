package com.duobang.middleware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.duobang.middleware.R;
import com.duobang.middleware.common.AppImageLoader;
import com.duobang.middleware.constant.IConstant;
import com.duobang.pms_lib.adapter.BaseLibAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends BaseLibAdapter<String, PhotoAdapter.PhotoViewHolder> {
    // 选择图片的尺寸风格
    //
    // - 一行单张图片，建议 LARGE
    // - 一行两张图片，建议 MEDIUM
    // - 一行多张图片，建议 DEFAULT
    public enum SizeType {
        SMALL, MEDIUM, LARGE
    }

    private OnItemDeleteClickListener onItemDeleteClickListener;
    private int showType;
    private SizeType sizeType;

    public PhotoAdapter(List<String> dataList, int showType) {
        super(dataList);
        this.showType = showType;
        this.sizeType = SizeType.SMALL;
    }

    public PhotoAdapter(List<String> dataList, int showType, SizeType sizeType) {
        super(dataList);
        this.showType = showType;
        this.sizeType = sizeType;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new PhotoViewHolder(inflater.inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, final int position, String item) {
        AppImageLoader.displayWithoutPlaceHolder(item, holder.iv);
        if (showType == IConstant.PHOTO.SHOW){
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.delete.setVisibility(View.VISIBLE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDeleteClickListener.onItemDeleteClick(v, position);
            }
        });

        ViewGroup.LayoutParams ivParams = holder.iv.getLayoutParams();
        switch (sizeType) {
            case MEDIUM:
                ivParams.height *= 1.5;
                break;
            case LARGE:
                ivParams.height *= 2;
            case SMALL:
            default:
                break;
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        ImageView delete;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img_photo_item);
            delete = itemView.findViewById(R.id.delete_photo_item);
        }
    }

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }

    public interface OnItemDeleteClickListener{
        void onItemDeleteClick(View v, int i);
    }
}
