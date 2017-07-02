package com.cuiweiyou.sharepoint.aty;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cuiweiyou.sharepoint.Constant;
import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.ThinkApplication;
import com.cuiweiyou.sharepoint.util.IntentStateUtil;
import com.cuiweiyou.sharepoint.util.ScreenUtil;

public class AboutActivity extends ThinkActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        flushStatusBarHeight(R.id.status_bar);
        flushViewHeight(findViewById(R.id.titlebar), ScreenUtil.getStatusBarHeight(this) * 2);

        WebView mWebview = (WebView) findViewById(R.id.webview);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不缓存
        mWebview.getSettings().setUseWideViewPort(true);      // 设置webView自适应手机屏幕
        mWebview.getSettings().setLoadWithOverviewMode(true); // 完全展示模式
        mWebview.getSettings().setSupportZoom(true);          // 支持缩放
        mWebview.loadUrl(Constant.URL_ABOUT);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                boolean netState = IntentStateUtil.getNetState(ThinkApplication.getContext());
                if(!netState){
                    Toast.makeText(ThinkApplication.getContext(), "不可用的网络状态", Toast.LENGTH_LONG).show();
                    return;
                }

                showProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideProgress();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
