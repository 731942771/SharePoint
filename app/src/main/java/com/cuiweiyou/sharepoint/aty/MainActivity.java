package com.cuiweiyou.sharepoint.aty;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;

import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.bean.ChapterBean;
import com.cuiweiyou.sharepoint.bean.ClassBean;
import com.cuiweiyou.sharepoint.fmg.CenterFmg;
import com.cuiweiyou.sharepoint.fmg.MenuFmg;
import com.cuiweiyou.sharepoint.util.ScreenUtil;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends ThinkActivity {

    private DrawerLayout mDrawerlyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerlyt = (DrawerLayout) findViewById(R.id.container_drawerlty);
        StatusBarUtil.setTranslucentForDrawerLayout(this, mDrawerlyt, 30);

        initView();
    }

    private void initView() {
        FragmentManager mFmgMng = getSupportFragmentManager();
        final CenterFmg fmgCenter = (CenterFmg) mFmgMng.findFragmentById(R.id.center_fmg);
        final MenuFmg fmgMenu = (MenuFmg) mFmgMng.findFragmentById(R.id.menu_fmg);

        fmgCenter.flushStatusBarHeight();
        fmgCenter.flushTitleBarHeight();

        fmgMenu.flushViewWidth(R.id.logo, ScreenUtil.getStatusBarHeight(this) * 2);
        fmgMenu.flushViewHeight(R.id.logo, ScreenUtil.getStatusBarHeight(this) * 2);
        fmgMenu.flushStatusBarHeight();
        fmgMenu.flushTitleBarHeight();

        fmgCenter.setSlideClickBack(new SlideBtnClickBack() {
            @Override
            public void onSlideClick() {
                if(mDrawerlyt.isDrawerOpen(Gravity.LEFT)){
                    mDrawerlyt.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerlyt.openDrawer(Gravity.LEFT);
                }
            }
        });

        fmgMenu.setOnMenuSelectedBack(new MenuSelectedBack() {
            @Override
            public void onMenuSelected(ChapterBean chapterBean, int classPosition) {
                mDrawerlyt.closeDrawer(Gravity.LEFT);

                fmgCenter.flushWebview(chapterBean, classPosition);
            }
        });

        fmgMenu.flushViewWidth();
    }

    public interface SlideBtnClickBack {
        public void onSlideClick();
    }

    public interface MenuSelectedBack {
        public void onMenuSelected(ChapterBean chapterBean, int classPosition);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mDrawerlyt.isDrawerOpen(Gravity.LEFT)){
            mDrawerlyt.closeDrawer(Gravity.LEFT);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
