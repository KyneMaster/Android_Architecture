package com.duobang.middleware.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.duobang.middleware.R;
import com.duobang.middleware.weight.RaeWebView;
import com.duobang.pms_lib.utils.DuobangUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * 简单WebView使用Fragment形式添加
 * Use the {@link WebViewFragment#newInstance} factory method to
 */
public class WebViewFragment extends Fragment {

    private static final String URL = "url";

    private String url;
    private RaeWebView mWebView;
    private ProgressBar progressBar;

    public WebViewFragment() {

    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mWebView = view.findViewById(R.id.web_view);
        progressBar = view.findViewById(R.id.progress_bar_web);
    }

    private void initData() {
        setWebView(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String url) {
        /*
          支持js
         */
        WebSettings settings = mWebView.getSettings();

        /*
          支持javascript
         */
        settings.setJavaScriptEnabled(true);

        /*
          设置可以支持缩放
         */
        settings.setSupportZoom(true);

        /*
          设置出现缩放工具
         */
        settings.setBuiltInZoomControls(true);

        /*
          扩大比例的缩放
         */
        settings.setUseWideViewPort(true);

        /*
          隐藏缩放工具
         */
        settings.setDisplayZoomControls(false);

        /*
          自适应屏幕
         */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 80) {
                    if (DuobangUtils.isActivityRunning(getActivity())) {
                        progressBar.setProgress(newProgress);
                        progressBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                }else {
                    if (DuobangUtils.isActivityRunning(getActivity())) {
                        progressBar.setProgress(newProgress);
                        progressBar.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
    }

}
