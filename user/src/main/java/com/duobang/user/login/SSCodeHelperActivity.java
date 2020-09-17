package com.duobang.user.login;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.duobang.user.R;

import androidx.appcompat.app.AppCompatActivity;

public class SSCodeHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 更改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.user_activity_sscode_helper);
        findViewById(R.id.close_sscode_helper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
