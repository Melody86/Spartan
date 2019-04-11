package com.Spartan;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.Spartan.ui.activity.BaseActivity;
import com.Spartan.ui.activity.BaseWebViewActivity;
import com.Spartan.ui.activity.WebPools;
import com.Spartan.ui.common.CommonActionBar;

public class MainActivity extends BaseActivity {
    private String TAG = "MainActivity";
    private TextView hello_text;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Webview
        long s = System.currentTimeMillis();
        mWebView = WebPools.getInstance().acquireWebView(this);
        long e = System.currentTimeMillis();
        Log.i(TAG, "initWebView time cost:" + (e - s)+"ms");
        mWebView = WebPools.getInstance().acquireWebView(this);
        initWebViewSettings(mWebView);
    }

    @Override
    public void initView() {
        hello_text = findViewById(R.id.hello_text);
        hello_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BaseWebViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        CommonActionBar commonActionBar = getCommonActionBar();
        commonActionBar.setTitle("首页");
    }

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }


    //初始化webviewSettings
    private void initWebViewSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        //支持获取手势焦点
        webView.requestFocusFromTouch();

        // 不使用缓存
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 启用js交互
        settings.setJavaScriptEnabled(true);
        //注入js调用代码
        //       webView.addJavascriptInterface(new AndroidJavaScriptObject(this),
        //    "growingjs");
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
    }

    //销毁 避免内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebPools.getInstance().recycle(mWebView);
    }
}
