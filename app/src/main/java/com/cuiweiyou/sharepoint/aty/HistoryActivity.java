package com.cuiweiyou.sharepoint.aty;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cuiweiyou.sharepoint.Constant;
import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.util.ScreenUtil;
import com.cuiweiyou.sharepoint.util.SharedPrefUtil;
import com.cuiweiyou.thinkcheckwidget.ThinkSwitchWidget;

public class HistoryActivity extends ThinkActivity {

    private EditText mUdfurl;
    private ThinkSwitchWidget mThinkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        flushStatusBarHeight(R.id.status_bar);
        flushViewHeight(findViewById(R.id.titlebar), ScreenUtil.getStatusBarHeight(this) * 2);

        initView();
        initEvent();
    }

    private void initView() {
        mUdfurl = (EditText) findViewById(R.id.udfurl);
        mThinkSwitch = (ThinkSwitchWidget) findViewById(R.id.thinkswitch);

        mThinkSwitch.setChecked(SharedPrefUtil.getBooleanValue(this, Constant.KEY_SETTING_DEFAULT_URL_PAGE));
        mUdfurl.setText(SharedPrefUtil.getStringValue(this, Constant.KEY_URL_PAGE_USER_DEFAULT));
    }

    private void initEvent(){
        mThinkSwitch.setThinkCheckedListener(new ThinkSwitchWidget.ThinkCheckedListener() {
            @Override
            public void isChecked(boolean isChecked) {
                SharedPrefUtil.setBooleanValue(HistoryActivity.this, Constant.KEY_SETTING_DEFAULT_URL_PAGE, isChecked);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUdfurl();
            }
        });
    }

    private void checkUdfurl() {
        if(!mThinkSwitch.isChecked()){ // 使用自定义
            String url = mUdfurl.getText().toString();
            if("".equals(url)){
                Toast.makeText(this, "设置无效，使用默认页面", Toast.LENGTH_LONG).show();
                SharedPrefUtil.setStringValue(this, Constant.KEY_URL_PAGE_USER_DEFAULT, Constant.URL_PAGE_DEFAULT);
            } else {
                SharedPrefUtil.setStringValue(this, Constant.KEY_URL_PAGE_USER_DEFAULT, url);
            }
        }

        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            checkUdfurl();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
