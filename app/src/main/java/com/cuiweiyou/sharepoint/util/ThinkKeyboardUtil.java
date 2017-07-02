package com.cuiweiyou.sharepoint.util;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;

import com.cuiweiyou.sharepoint.R;

/**
 * http://blog.csdn.net/yaosongqwe/article/details/50199951
 * http://www.jianshu.com/p/5964c09a8579
 * 或自定义组合控件，屏蔽系统软键盘
 */
public class ThinkKeyboardUtil {
    private Context mContext;//上下文对象
    private KeyboardView mKeyboardView;//这个主角怎么能丢？
    private Keyboard mKeyboard;//好吧，其实他也是主角

    public ThinkKeyboardUtil(Context context) {
        mContext = context;
        //初始化键盘布局，下面在放进 KeyBoardView里面去。
        mKeyboard = new Keyboard(mContext, R.xml.think_keyboard);
        //配置keyBoardView
        try {
            mKeyboardView = (KeyboardView) ((Activity) context).findViewById(R.id.keyboardview);
            mKeyboardView.setKeyboard(mKeyboard); //装甲激活~ 咳咳…
            mKeyboardView.setPreviewEnabled(false);   //这个是，效果图按住是出来的预览图。

            //设置监听，不设置的话会报错。监听放下面了。
            mKeyboardView.setOnKeyboardActionListener(mListener);
        } catch (Exception e) {
            Log.e("sun", "keyview初始化失败");
        }
    }

    //监听
    private KeyboardView.OnKeyboardActionListener mListener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void onPress(int primaryCode) {
            Log.e("ard", "onPress:" + primaryCode);
        }

        @Override
        public void onRelease(int primaryCode) {
            Log.e("ard", "onRelease:" + primaryCode);
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Log.e("ard", "onkey:" + primaryCode + "");
        }

        @Override
        public void onText(CharSequence text) {
            Log.e("ard", "onText:" + text);
        }

        @Override
        public void swipeLeft() {
            Log.e("ard", "swipeLeft");
        }

        @Override
        public void swipeRight() {
            Log.e("ard", "swipeRight");
        }

        @Override
        public void swipeDown() {
            Log.e("ard", "swipeDown");
        }

        @Override
        public void swipeUp() {
            Log.e("ard", "swipeUp");
        }
    };
}
