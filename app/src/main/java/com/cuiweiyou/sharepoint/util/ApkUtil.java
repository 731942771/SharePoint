package com.cuiweiyou.sharepoint.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.List;

/**
 * <b>类名</b>: ApkUtil.java，apk文件工具类 <br/>
 * <b>说明</b>: <br/>
 * &emsp;&emsp; getVersionName()：获取版本<br/>
 *
 * @author cuiweiyou.com <br/>
 * @version <br/>
 */
public class ApkUtil {

    private static PackageManager packageManager;
    private static int versionCode = 0;
    private static String versionName = "0";
    private static String packageName = "0";

    private ApkUtil() {
    }

    ;

    /**
     * <b>功能</b>：getVersionCode，获取app的版本号 <br/>
     * <b>说明</b>: 使用全局appctx的静态方法拿到ctx。<br/>
     * &emsp;&emsp; 如果获取错误，返回 0<br/>
     *
     * @return int 版本号
     * @author cuiweiyou.com
     */
    public static int getVersionCode(Context ctx) {
        if (0 == versionCode) {
            if (null == packageManager)
                packageManager = ctx.getPackageManager();

            try {
                // 2.应用包信息，获取AndroidManifest.xml中的配置信息
                PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);

                // 3.版本名
                // String versionName = packageInfo.versionName;
                versionCode = packageInfo.versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return versionCode;
    }

    /**
     * <b>功能</b>：getVersionName，获取app的版本名称 <br/>
     * <b>说明</b>: 使用全局appctx的静态方法拿到ctx。<br/>
     * &emsp;&emsp; 如果获取错误，返回“0”<br/>
     *
     * @return int 版本号
     * @author cuiweiyou.com
     */
    public static String getVersionName(Context ctx) {
        if ("0".equals(versionName)) {
            if (null == packageManager)
                packageManager = ctx.getPackageManager();

            try {
                // 2.应用包信息，获取AndroidManifest.xml中的配置信息
                PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);

                // 3.版本名
                versionName = packageInfo.versionName;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return versionName;
    }

    /**
     * 获取包名
     *
     * @return
     */
    public static String getPackageName(Context ctx) {
        if ("0".equals(packageName)) {

            packageName = ctx.getPackageName();

            if(!"0".equals(packageName)) {
                return packageName;
            } else {
                //当前应用pid
                int pid = android.os.Process.myPid();
                //任务管理类
                ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
                //遍历所有应用
                List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo info : infos) {
                    if (info.pid == pid) {
                        packageName = info.processName;//返回包名
                        break;
                    }
                }
            }
        }

        return packageName;
    }
}
