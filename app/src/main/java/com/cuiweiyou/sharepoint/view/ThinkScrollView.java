package com.cuiweiyou.sharepoint.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * 可滚动监听的SV
 * @author：gaohaiyan.com
 * @version：1.0.0
 */
public class ThinkScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;
    private boolean isGesturePulldown = false;

    public ThinkScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThinkScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            isGesturePulldown = y > oldy ? false : true;

            if(0 == y && -1 == oldy)
                isGesturePulldown = true;

            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy, isGesturePulldown);
        }
    }

    public interface ScrollViewListener {
        /**
         * 函数功能：监听功能
         * @param scrollView 监听目标
         * @param x 滚动后的x
         * @param y 滚动后的y
         * @param oldx 滚动前的x
         * @param oldy 滚动前的y
         * @param isGesturePulldown true手势下拉，FALSE上滑
         *
         * @author：gaohaiyan.com
         * @version：1.0.0
         * @time：018,16/9/18_11:43
         */
        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy, boolean isGesturePulldown);
    }
}
