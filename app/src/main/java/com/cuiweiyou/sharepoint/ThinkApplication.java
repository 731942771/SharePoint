package com.cuiweiyou.sharepoint;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/06/27,027.
 */
public class ThinkApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
