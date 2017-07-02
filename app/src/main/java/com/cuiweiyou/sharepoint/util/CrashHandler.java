package com.cuiweiyou.sharepoint.util;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URLEncoder;
import java.util.Arrays;

public class CrashHandler implements UncaughtExceptionHandler {
    private static Context ctx;
    public static String starttime;
    private static final String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/crashlogs/";
    private static final String FILE_NAME = "spitslot_crash_";
    private static final String FILE_NAME_SUFFIX = ".txt";
    private static CrashHandler sInstance = new CrashHandler();

    // 系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private UncaughtExceptionHandler mDefaultCrashHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance(Context ctx) {
        CrashHandler.ctx = ctx;
        return sInstance;
    }

    // 这里主要完成初始化工作
    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this); // 将当前实例设为系统默认的异常处理器
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String exp = "";
        try {
            // 导出异常信息到SD卡中
            exp = dumpExceptionToSDCard(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 打印出当前调用栈信息
        ex.printStackTrace();

        // 如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private String dumpExceptionToSDCard(Throwable ex) throws IOException {
        String error = ex.getMessage();
        String time = DatetimeUtil.getNowTime();
        String baseinfo = PhoneinfoUtil.getBaseinfo(CrashHandler.ctx);
        String totalMemory = PhoneinfoUtil.getTotalMemory(CrashHandler.ctx);
        String availMemory = PhoneinfoUtil.getAvailMemory(CrashHandler.ctx);
        String cpuInfo = Arrays.toString(PhoneinfoUtil.getCpuInfo());
        String otherInfo = PhoneinfoUtil.getOtherInfo(CrashHandler.ctx);

        StringBuilder bug = new StringBuilder();
        bug.append("error=" + URLEncoder.encode(error, "utf-8"));
        bug.append("&baseinfo=" + URLEncoder.encode(baseinfo, "utf-8"));
        bug.append("&time=" + time);
        bug.append("&totalmemory=" + totalMemory);
        bug.append("&availmemory=" + availMemory);
        bug.append("&cpuinfo=" + cpuInfo);
        bug.append("&otherinfo=" + URLEncoder.encode(otherInfo, "utf-8"));
        bug.append("&description=");
        bug.append("&note=");

        Log.e("ard", "异常：" + bug.toString());

        //new BugPostTask().execute(bug.toString());

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);// 以当前时间创建log文件

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(starttime);// 程序开始时间
            pw.println("奔溃时刻：" + time);// 导出发生异常的时间
            pw.println();
            ex.printStackTrace(pw);// 导出异常的调用栈信息

            pw.close();

            Toast.makeText(ctx, "请到下面路径查看异常：\n" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }

        return file.getAbsolutePath();
    }

}
