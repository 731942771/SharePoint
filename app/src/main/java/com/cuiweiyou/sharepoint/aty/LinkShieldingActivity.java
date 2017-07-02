package com.cuiweiyou.sharepoint.aty;

import android.os.Bundle;
import android.view.View;

import com.cuiweiyou.sharepoint.Constant;
import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.util.ScreenUtil;
import com.cuiweiyou.sharepoint.util.SharedPrefUtil;
import com.cuiweiyou.thinkcheckwidget.ThinkSwitchWidget;

public class LinkShieldingActivity extends ThinkActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_shielding);
        flushStatusBarHeight(R.id.status_bar);
        flushViewHeight(findViewById(R.id.titlebar), ScreenUtil.getStatusBarHeight(this) * 2);

        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ThinkSwitchWidget tsw = (ThinkSwitchWidget) findViewById(R.id.thinkswitch);
        tsw.setThinkCheckedListener(new ThinkSwitchWidget.ThinkCheckedListener() {
            @Override
            public void isChecked(boolean isChecked) {
                SharedPrefUtil.setBooleanValue(LinkShieldingActivity.this, Constant.KEY_SETTING_WEBVIEW_LINK_SHIELDING, isChecked);
            }
        });

        tsw.setChecked(SharedPrefUtil.getBooleanValue(LinkShieldingActivity.this, Constant.KEY_SETTING_WEBVIEW_LINK_SHIELDING));
    }
}
