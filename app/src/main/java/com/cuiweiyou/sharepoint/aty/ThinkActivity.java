package com.cuiweiyou.sharepoint.aty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cuiweiyou.sharepoint.util.ScreenUtil;
import com.jaeger.library.StatusBarUtil;

/**
 * 沉浸式状态栏<br/>
 * nextActivity 跳转到下个aty
 */
public class ThinkActivity extends AppCompatActivity {

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideNavigationBar();
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
    }

    /**
     * 跳转到下一个Aty
     *
     * @param clazz 下一个Aty的class
     */
    protected void nextActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 更新控件高度
     */
    public void flushViewHeight(View v, int height) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = height;
        v.setLayoutParams(layoutParams);
    }

    /**
     * 更新控件宽度
     */
    public void flushViewWidth(View v, int width){
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = width;
        v.setLayoutParams(layoutParams);
    }

    /**
     * 更新状态栏高度
     */
    public void flushStatusBarHeight(int id) {
        View statusbar = findViewById(id);
        ViewGroup.LayoutParams layoutParams = statusbar.getLayoutParams();
        layoutParams.height = ScreenUtil.getStatusBarHeight(this);
        statusbar.setLayoutParams(layoutParams);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigationBar();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            this.hideNavigationBar();
        }
        return false;
    }

    // 参考 http://www.cnblogs.com/crazii/p/4238820.html
    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;    //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }

        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    public void showProgress(){
        pd = new ProgressDialog(this);
        pd.setMessage("加载中");
        pd.show();
    }

    public void hideProgress(){
        if(null != pd)
            pd.dismiss();

        pd = null;
    }
}
