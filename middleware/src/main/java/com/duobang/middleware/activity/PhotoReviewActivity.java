package com.duobang.middleware.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duobang.middleware.R;
import com.duobang.middleware.adapter.PhotoPagerAdapter;
import com.duobang.pms_lib.common.DuobangViewPager;
import com.duobang.pms_lib.immersionBar.ImmersionBar;
import com.duobang.pms_lib.utils.DuobangUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * 图片预览View
 *
 * intent : 1. position 当前位置 2. photos
 */
public class PhotoReviewActivity extends AppCompatActivity {
    // TODO 在动画基础上，全局配置

    private DuobangViewPager mViewPager;
    private int position;
    private List<String> photoUrls;
    private PhotoPagerAdapter pagerAdapter;
    private TextView positionPoint;
    private TextView amountPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        ImmersionBar.with(this).fullScreen(true).init();
        setContentView(R.layout.activity_photo_review);
        initContent();
        initData();
    }

    protected void initContent() {
        mViewPager = findViewById(R.id.viewpager_photo_review);
        positionPoint = findViewById(R.id.position_point_photo_review);
        amountPoint = findViewById(R.id.amount_point_photo_review);
    }

    protected void initData() {
        getDataByIntent();
        if (photoUrls != null) {
            pagerAdapter = new PhotoPagerAdapter(photoUrls);
            mViewPager.setAdapter(pagerAdapter);
            mViewPager.setCurrentItem(position);
            setupPoint(photoUrls, position);
            pagerAdapter.setOnItemClickListener(new PhotoPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    ActivityCompat.finishAfterTransition(PhotoReviewActivity.this);
                }
            });
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    position = i;
                    setupPoint(photoUrls, position);
                }

                @Override
                public void onPageSelected(int i) {

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }


    private void setupPoint(List<String> photoUrls, int position) {
        amountPoint.setText(photoUrls.size()+"");
        positionPoint.setText(position+1+"");
    }

    private void getDataByIntent() {
        position = getIntent().getIntExtra("position", -1);
        photoUrls = (List<String>) getIntent().getSerializableExtra("photos");
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.BLACK);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    @Override
    public void finish() {
        if (DuobangUtils.isActivityRunning(this)) {
            Glide.with(PhotoReviewActivity.this).pauseRequests();
        }
        super.finish();
    }
}
