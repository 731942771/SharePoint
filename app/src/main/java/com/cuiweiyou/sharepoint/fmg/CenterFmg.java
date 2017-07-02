package com.cuiweiyou.sharepoint.fmg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.cuiweiyou.sharepoint.Constant;
import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.ThinkApplication;
import com.cuiweiyou.sharepoint.adpt.CenterPageadapter;
import com.cuiweiyou.sharepoint.aty.SettingActivity;
import com.cuiweiyou.sharepoint.bean.ChapterBean;
import com.cuiweiyou.sharepoint.util.IntentStateUtil;
import com.cuiweiyou.sharepoint.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * 中心fmg
 */
public class CenterFmg extends ThinkFragment {

    private WebView mWebview;
    private TextView mTitle;
    private ViewPager mViewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_center, container, false);

        initView();
        initData();

        return mView;
    }

    private void initView() {

        mView.findViewById(R.id.slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideClickBack.onSlideClick();
            }
        });

        mView.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(SettingActivity.class);
            }
        });

        mTitle = (TextView) mView.findViewById(R.id.title);

        initViewpager();
        initWebview();
    }

    private void initData() {
        Boolean dup = SharedPrefUtil.getBooleanValue(getActivity(), Constant.KEY_SETTING_DEFAULT_URL_PAGE);
        if (dup) { // 历史页面
            mWebview.loadUrl(SharedPrefUtil.getStringValue(getActivity(), Constant.KEY_URL_PAGE_HISTORY));
        } else { // 自定义页面
            String page = SharedPrefUtil.getStringValue(getActivity(), Constant.KEY_URL_PAGE_USER_DEFAULT);
            if ("".equals(page))
                page = Constant.URL_PAGE_DEFAULT;

            mWebview.loadUrl(page);
        }
    }

    public void flushWebview(ChapterBean cb, int clazz) {
        String chapterTitle = cb.getT();
        String classTitle = cb.getCs().get(clazz).getT();
        String classUrl = cb.getCs().get(clazz).getU();
        mViewpager.setCurrentItem(0, false);
        mWebview.loadUrl(classUrl);
        mTitle.setText(chapterTitle + " - " + classTitle);

        SharedPrefUtil.setStringValue(getActivity(), Constant.KEY_URL_PAGE_HISTORY, classUrl);
    }

    private void initViewpager() {
        View view_web = View.inflate(getActivity(), R.layout.view_webview_center, null);
        View view_news = View.inflate(getActivity(), R.layout.view_news_center, null);

        List<View> pagelist = new ArrayList<View>();
        pagelist.add(view_web);
        pagelist.add(view_news);

        CenterPageadapter pageadapter = new CenterPageadapter(pagelist);

        mViewpager = (ViewPager) mView.findViewById(R.id.viewpager);
        mViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true; // TODO 禁止滑动切换
            }
        });
        mViewpager.setAdapter(pageadapter);
        mViewpager.setCurrentItem(mViewpager.getChildCount() - 1, false);

        mWebview = (WebView) view_web.findViewById(R.id.webview);
    }

    private void initWebview() {
        mWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebview.setBackgroundColor(0);
        mWebview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.getSettings().setAppCacheEnabled(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 16) {
            mWebview.getSettings().setAllowUniversalAccessFromFileURLs(true); // 页面切换致使 localStorage 失效
        }
        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            String dir = getActivity().getDir("database", MODE_PRIVATE).getPath();
            mWebview.getSettings().setDatabasePath(dir);
        }
        mWebview.getSettings().setAppCachePath(appCachePath);
        mWebview.getSettings().setDomStorageEnabled(true); // 地图
        mWebview.getSettings().setDatabaseEnabled(true);// 支持js
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不缓存
        mWebview.getSettings().setUseWideViewPort(true);      // 设置webView自适应手机屏幕
        mWebview.getSettings().setLoadWithOverviewMode(true); // 完全展示模式
        mWebview.getSettings().setSupportZoom(true);          // 支持缩放
        //mWebview.getSettings().setBlockNetworkImage(true);    // 图片在页面加载完毕后渲染

        mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                boolean mIsLinkShielding = SharedPrefUtil.getBooleanValue(getActivity(), Constant.KEY_SETTING_WEBVIEW_LINK_SHIELDING);
                Log.e("ard", mIsLinkShielding + "，网址：" + url);

                if (mIsLinkShielding)
                    return mIsLinkShielding;
                else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }

                return true;
            }

            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = "";
                if (Build.VERSION.SDK_INT >= 21)
                    url = request.getUrl().toString();

                boolean mIsLinkShielding = SharedPrefUtil.getBooleanValue(getActivity(), Constant.KEY_SETTING_WEBVIEW_LINK_SHIELDING);
                Log.e("ard", mIsLinkShielding + "，21网址：" + url);

                if (mIsLinkShielding)
                    return mIsLinkShielding;
                else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }

                return true;
            }

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

    }
}
