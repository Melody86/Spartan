package com.Spartan.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Spartan.R;

/**
 * Created by melody
 * 2019/3/26 8:29 PM
 **/
public class BaseWebViewActivity extends BaseActivity {
    private static Activity mActivity;
    private WebView mWebView;
    private String TAG = "BaseWebViewActivity";
    private ConstraintLayout cl_error_page;
    private TextView error_hint;
    private ProgressBar load_progress_bar;
    private long wvInit, wvSet, wvload;
    private String loadUrl;
    private ConstraintLayout.LayoutParams mWebViewParams;
    private ConstraintLayout.LayoutParams mProBarParams;


    @Override
    public int getLayoutById() {
        return R.layout.base_webview_activity;
    }

    @Override
    public void initView() {
        mWebViewParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mProBarParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);

        wvInit = System.currentTimeMillis();
        mWebView = WebPools.getInstance().acquireWebView(this);
        wvSet = System.currentTimeMillis();
        Log.i(TAG, "initWebView time cost:" + (wvSet - wvInit)+"ms");

        load_progress_bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        load_progress_bar.setProgress(0);
        load_progress_bar.setMax(100);
//        load_progress_bar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_states));

        addContentView(mWebView, mWebViewParams);
        addContentView(load_progress_bar, mProBarParams);

//        mWebView = (WebView) findViewById(R.id.base_webview);
        cl_error_page = (ConstraintLayout) findViewById(R.id.cl_error_page);
        error_hint = (TextView) findViewById(R.id.error_hint);

        mWebView.setWebViewClient(new MyWebViewClient());

//
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                load_progress_bar.setProgress(newProgress);
            }
        });

    }

    @Override
    public void initData() {
        mActivity = (Activity) this;
        loadUrl = "https://www.baidu.com/";
        load_progress_bar.setVisibility(View.VISIBLE);
        load_progress_bar.setProgress(0);
        mWebView.loadUrl(loadUrl);
        wvload = System.currentTimeMillis();
        Log.i(TAG, "LoadWebView total time cost:" + (wvload-wvInit)+"ms");
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        getCommonActionBar().setVisibility(View.GONE);
    }


    class MyWebViewClient extends WebViewClient{
        private String TAG = "MyWebViewClient";


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            load_progress_bar.setVisibility(View.GONE);
        }

        @Deprecated
        public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
//            throw new RuntimeException("Stub!");
            load_progress_bar.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
            cl_error_page.setVisibility(View.VISIBLE);
            error_hint.setText("Code:" + errorCode+"\n"+description);
            Log.d(TAG, "Code:" + errorCode+"\n"+description);
            cl_error_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cl_error_page.setVisibility(View.GONE);
                            view.loadUrl(loadUrl);
                        }
                    });
                }
            });
        }

        //API 大于23时
        @Override
        @TargetApi(Build.VERSION_CODES.M)
        public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {
            if(request.isForMainFrame()){
                Log.d(TAG, "onReceivedHttpError start...");
                load_progress_bar.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                cl_error_page.setVisibility(View.VISIBLE);
                error_hint.setText("Code:" + error.getErrorCode()+"\n"+error.getDescription());
                Log.d(TAG, "Code:" + error.getErrorCode()+"\n"+error.getDescription());

                cl_error_page.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cl_error_page.setVisibility(View.GONE);
                                view.loadUrl(loadUrl);
                            }
                        });
                    }
                });
            }else{
                super.onReceivedError(view, request, error);
            }

        }

        @Override
        public void onReceivedHttpError(final WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

}
