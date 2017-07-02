package com.cuiweiyou.sharepoint.aty;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.util.ScreenUtil;
import com.cuiweiyou.sharepoint.view.ThinkScrollView;
import com.jaeger.library.StatusBarUtil;

public class SettingActivity extends ThinkActivity {

    private View mTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 30);
        flushStatusBarHeight(R.id.status_bar);
        flushViewHeight(findViewById(R.id.titlebar), ScreenUtil.getStatusBarHeight(this) * 2);
        flushViewHeight(findViewById(R.id.topbar), ScreenUtil.getStatusBarHeight(this) * 3);

        initView();
        initEvent();
    }

    private void initView() {
        mTopbar = findViewById(R.id.topbar);

        ThinkScrollView mScrollview = (ThinkScrollView) findViewById(R.id.scrollview);
        mScrollview.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        mScrollview.setScrollViewListener(new ThinkScrollView.ScrollViewListener() {
            public boolean isTouchTop = true;
            public boolean isTouchBottom = false;

            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy, boolean isGesturePulldown) {
                View btm = scrollView.getChildAt(0);
                int bottom = btm.getBottom() - y - scrollView.getMeasuredHeight();

                if (0 == y && 0 != bottom) {
                    //// Log.e("ard", "到顶端了");
                    isTouchTop = true;
                    if (isTouchBottom) {
                        isTouchBottom = false;
                        switchTitleBarStyle(0);
                    }
                } else if (0 != y && 0 == bottom) {
                    //// Log.e("ard", "到底部了");
                    isTouchBottom = true;
                    if (isTouchTop) {
                        isTouchTop = false;
                        switchTitleBarStyle(1);
                    }
                }
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView website = (TextView) findViewById(R.id.website);
        SpannableStringBuilder ssb = new SpannableStringBuilder("www.gaohaiyan.com");
        ssb.setSpan(new URLSpan("http://www.gaohaiyan.com"), 0, ssb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(Color.WHITE), 0, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        website.setText(ssb);
        website.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 显示或隐藏标题栏
     *
     * @param i 0=隐藏之，1=显示之
     **/
    private void switchTitleBarStyle(int i) {
        switch (i) {
            case 0: {
                ObjectAnimator oa = ObjectAnimator.ofFloat(
                        mTopbar, // 控件
                        "alpha",// 属性
                        new float[]{1, 0});// 取值始终
                oa.setDuration(500);// 时长
                oa.start();

                break;
            }
            case 1: {
                ObjectAnimator oa = ObjectAnimator.ofFloat(
                        mTopbar,
                        "alpha",
                        new float[]{0, 1});
                oa.setDuration(500);
                oa.start();

                break;
            }
        }
    }

    private void initEvent() {
        // 返回
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 设置目录url
        findViewById(R.id.setudfurl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(MenuurlActivity.class);
            }
        });

        // 是否启动打开上次页面
        findViewById(R.id.setopenhistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(HistoryActivity.class);
            }
        });

        // 页面内超级链接可点击
        findViewById(R.id.setlinkable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(LinkShieldingActivity.class);
            }
        });

        // 关于
        findViewById(R.id.setabout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(AboutActivity.class);
            }
        });
    }
}
