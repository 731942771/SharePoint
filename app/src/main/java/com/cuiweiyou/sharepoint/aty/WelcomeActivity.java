package com.cuiweiyou.sharepoint.aty;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.util.ApkUtil;
import com.cuiweiyou.sharepoint.util.DelayUtil;

import java.util.List;

public class WelcomeActivity extends ThinkActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DelayUtil.doDelay(3000, new DelayUtil.DelayBack() {
            @Override
            public void delayFinished() {
                nextActivity(MainActivity.class);
                finish();
            }
        });
    }

}
