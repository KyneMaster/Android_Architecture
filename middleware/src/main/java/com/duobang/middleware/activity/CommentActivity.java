package com.duobang.middleware.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.duobang.middleware.R;
import com.duobang.middleware.adapter.PhotoAdapter;
import com.duobang.middleware.common.AppPictureSelector;
import com.duobang.middleware.constant.IConstant;
import com.duobang.middleware.constant.REQUEST_CODE;
import com.duobang.middleware.environment.AppConfig;
import com.duobang.middleware.router.AppRoute;
import com.duobang.pms_lib.adapter.BaseLibAdapter;
import com.duobang.pms_lib.adapter.HeaderAndFooterWrapper;
import com.duobang.pms_lib.file.FileNetWork;
import com.duobang.pms_lib.file.IPhotoListListener;
import com.duobang.pms_lib.immersionBar.ImmersionBar;
import com.duobang.pms_lib.utils.IMEUtils;
import com.duobang.pms_lib.utils.LoadingUtils;
import com.duobang.pms_lib.utils.MessageUtils;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

@Route(path = AppRoute.COMMON_COMMENT)
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input;
    private RecyclerView photoView;
    private PhotoAdapter adapter;
    private HeaderAndFooterWrapper wrapper;
    private List<String> photoPaths = new ArrayList<>();
    private List<String> compressPaths;
    private List<String> ossPaths;
    private boolean isOriginalPhoto = false;
    private static final int UPLOAD_PHOTO = 1000;

    private Handler handler = new Handler(new WeakReference<Handler.Callback>(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_PHOTO:
                    uploadPhotoList();
                    return true;
                default:
                    return false;
            }
        }
    }).get());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initImmersionBar();
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.back_comment).setOnClickListener(this);
        findViewById(R.id.commit_comment).setOnClickListener(this);
        findViewById(R.id.input_ly_comment).setOnClickListener(this);
        input = findViewById(R.id.input_comment);
        photoView = findViewById(R.id.list_comment);
    }

    private void initData() {
        setupPhotoView();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.black).statusBarDarkFont(false).init();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_comment) {
            finish();
        } else if (v.getId() == R.id.commit_comment) {
            if (canCommit()) {
                handlePhoto();
            }
        } else if (v.getId() == R.id.input_ly_comment) {
            input.setFocusable(true);
            input.requestFocus();
            input.setFocusableInTouchMode(true);
            IMEUtils.showIME(this, input);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE.SELECT_PHOTO_CODE:
                if (resultCode != -1 || data == null) {
                    return;
                }
                photoPaths.addAll(Matisse.obtainPathResult(data));
                isOriginalPhoto = Matisse.obtainOriginalState(data);
                if (photoPaths == null || photoPaths.size() < 1) {
                    return;
                }
                setupPhotoView();
                break;
            default:
                break;
        }
    }

    private void setupPhotoView() {
        if (adapter == null) {
            adapter = new PhotoAdapter(photoPaths, IConstant.PHOTO.EDIT);
            wrapper = new HeaderAndFooterWrapper(adapter);
            wrapper.addFooterView(getFootView());
            photoView.setLayoutManager(new GridLayoutManager(this, 4));
            photoView.setAdapter(wrapper);
        } else {
            adapter.invalidate(photoPaths);
            wrapper.notifyDataSetChanged();
        }
        adapter.setOnItemDeleteClickListener(new PhotoAdapter.OnItemDeleteClickListener() {
            @Override
            public void onItemDeleteClick(View v, int i) {
                if (photoPaths.size() > i) {
                    photoPaths.remove(i);
                }
                setupPhotoView();
            }
        });
        adapter.setOnItemClickListener(new BaseLibAdapter.onItemClickListener<String>() {
            @Override
            public void onItemClick(Context context, int i, String item) {
                // TODO 使用ARouter优化代码
                Intent intent = new Intent(CommentActivity.this, PhotoReviewActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("photos", (Serializable) photoPaths);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(CommentActivity.this, photoView, "sharedView").toBundle());
                }
            }
        });
    }

    private void handlePhoto() {
        if (photoPaths.size() == 0) {
            forResult();
        } else {
            LoadingUtils.showLoadingDialog(this);
            compressPaths = new ArrayList<>();
            if (!isOriginalPhoto) {//图片不是原图，先压缩，再上传
                for (int i = 0; i < photoPaths.size(); i++) {
                    if (i == photoPaths.size() - 1) {
                        compressImage(photoPaths.get(i), true);
                    } else {
                        compressImage(photoPaths.get(i), false);
                    }
                }
            } else {//图片要求原图，直接上传
                compressPaths.addAll(photoPaths);
                handler.sendEmptyMessage(UPLOAD_PHOTO);
            }
        }
    }

    private void uploadPhotoList() {
        FileNetWork.getInstance().uploadPhotoList(compressPaths, new IPhotoListListener() {
            @Override
            public void onUploadListOk(List<String> ossPathList) {
                if (ossPaths == null) {
                    ossPaths = new ArrayList<>();
                }
                ossPaths.clear();
                ossPaths.addAll(ossPathList);
                LoadingUtils.dismissLoadingDialog();
                forResult();
            }

            @Override
            public void onFailure(String message) {
                MessageUtils.shortToast(message);
                LoadingUtils.dismissLoadingDialog();
            }
        });
    }

    private View getFootView() {
        View footView = View.inflate(this, R.layout.add_photo_footer, null);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IMEUtils.isIMEAlive(CommentActivity.this)) {
                    IMEUtils.hideIME(CommentActivity.this, input);
                }
                AppPictureSelector.takePhotoSelector(CommentActivity.this, 9 - photoPaths.size(), REQUEST_CODE.SELECT_PHOTO_CODE);
            }
        });
        return footView;
    }

    private boolean canCommit() {
        return input.getText() != null && !input.getText().toString().trim().equals("");
    }

    private void forResult() {
        IMEUtils.hideIME(CommentActivity.this, input);
        Intent intent = new Intent();
        String commentJson = getCommentJson();
        intent.putExtra(IConstant.COMMENT, commentJson);
        setResult(REQUEST_CODE.COMMENT, intent);
        finish();
    }

    /**
     * 压缩图片，最后一张压缩完成开始上传
     *
     * @param path   本地文件路径
     * @param isLast 是否为最后一张
     */
    private void compressImage(final String path, final boolean isLast) {
        Luban.with(this).load(new File(path)).ignoreBy(400).setTargetDir(AppConfig.getPathDir()).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                compressPaths.add(file.getPath());
                if (isLast) {
                    handler.sendEmptyMessage(UPLOAD_PHOTO);
                }
            }

            @Override
            public void onError(Throwable e) {
                compressPaths.add(path);
                if (isLast) {
                    handler.sendEmptyMessage(UPLOAD_PHOTO);
                }
            }
        }).launch();
    }

    /**
     * 将评论内容及图片打包成Json
     *
     * @return
     */
    private String getCommentJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("description", input.getText().toString().trim());
            jsonObject.put("imageList", ossPaths);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            return null;
        }
    }

}
