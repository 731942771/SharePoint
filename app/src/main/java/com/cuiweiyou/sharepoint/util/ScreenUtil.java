package com.cuiweiyou.sharepoint.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * 屏幕相关
 */
public class ScreenUtil {

    private ScreenUtil() {
    }

    private static int screen_width = 0;
    private static int screen_height = 0;
    private static int status_height = 0;
    private static int navigation_height = 0;

    /**
     * 屏幕宽，像素
     */
    public static int getScreenWidth(Context ctx) {
        if (0 == screen_width) {

            WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            screen_width = outMetrics.widthPixels;
            screen_height = outMetrics.heightPixels;
        }

        return screen_width;
    }

    /**
     * 屏幕高，像素
     */
    public static int getScreenHeight(Context ctx) {
        if (0 == screen_height) {

            WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            screen_width = outMetrics.widthPixels;
            screen_height = outMetrics.heightPixels;
        }

        return screen_height;
    }

    /**
     * 状态栏高度px<br/>
     * 无论状态栏是否隐藏
     */
    public static int getStatusBarHeight(Context ctx) {
        if (0 == status_height) {
            //获取status_bar_height资源的ID
            int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                status_height = ctx.getResources().getDimensionPixelSize(resourceId);
            } else {
                try {
                    Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                    Object object = clazz.newInstance();
                    int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                    status_height = ctx.getResources().getDimensionPixelSize(height);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return status_height;
    }

    /**
     * 状态栏高度px<br/>
     * 无论状态栏是否隐藏
     */
    public static int getNavigationBarHeight(Context ctx) {
        if (0 == navigation_height) {
            int resourceId = ctx.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                navigation_height = ctx.getResources().getDimensionPixelSize(resourceId);
            } else {
                try {
                    Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                    Object object = clazz.newInstance();
                    int height = Integer.parseInt(clazz.getField("navigation_bar_height").get(object).toString());
                    navigation_height = ctx.getResources().getDimensionPixelSize(height);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return navigation_height;
    }

    public static boolean isNavigationBarShow(Activity ctx){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = ctx.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y!=size.y;
        }else {
            boolean menu = ViewConfiguration.get(ctx).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if(menu || back) {
                return false;
            }else {
                return true;
            }
        }
    }
}
