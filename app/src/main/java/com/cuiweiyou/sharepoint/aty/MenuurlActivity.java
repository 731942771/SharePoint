package com.cuiweiyou.sharepoint.aty;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.cuiweiyou.sharepoint.Constant;
import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.adpt.NetpalAdapter;
import com.cuiweiyou.sharepoint.bean.NetpalBean;
import com.cuiweiyou.sharepoint.util.ScreenUtil;
import com.cuiweiyou.sharepoint.util.SharedPrefUtil;
import com.cuiweiyou.sharepoint.util.TaskUtil;
import com.cuiweiyou.thinkcheckwidget.ThinkSwitchWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuurlActivity extends ThinkActivity implements NetpalAdapter.IMenuUrlBack {

    private EditText mUdfUrl;
    private RecyclerView mRcyvNeturl;
    private CheckBox mCheckUdfurl;
    private ThinkSwitchWidget mThinkSwitch;
    private List mNeturlList;
    private NetpalAdapter mNetpalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuurl);
        //new ThinkKeyboardUtil(this);

        flushStatusBarHeight(R.id.status_bar);
        flushViewHeight(findViewById(R.id.titlebar), ScreenUtil.getStatusBarHeight(this) * 2);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.upurl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUrl();
            }
        });
        findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentFocus();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMenuUrl();
            }
        });

        mUdfUrl = (EditText) findViewById(R.id.udfurl);
        mUdfUrl.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);//将输入法切换到英文
        mUdfUrl.setImeOptions(EditorInfo.IME_ACTION_DONE);//将输入法弹出的右下角的按钮改为完成，不改的话会是下一步。

        mCheckUdfurl = (CheckBox) findViewById(R.id.cburl);
        mCheckUdfurl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNetpalAdapter.clearSelected();
                }
            }
        });

        mRcyvNeturl = (RecyclerView) findViewById(R.id.rv_neturl);
        mRcyvNeturl.setLayoutManager(new LinearLayoutManager(this));

        mNetpalAdapter = new NetpalAdapter(MenuurlActivity.this, mRcyvNeturl, mNeturlList, MenuurlActivity.this);

        mThinkSwitch = (ThinkSwitchWidget) findViewById(R.id.thinkswitch);
        mThinkSwitch.setThinkCheckedListener(new ThinkSwitchWidget.ThinkCheckedListener() {
            @Override
            public void isChecked(boolean isChecked) {
                if (isChecked) {
                    mCheckUdfurl.setChecked(false);
                    mNetpalAdapter.clearSelected();

                    SharedPrefUtil.setStringValue(MenuurlActivity.this, Constant.KEY_URL_MENU_DEFINE, Constant.URL_DEFAULT_MENU_CONSTANT);

                    findViewById(R.id.lyt_udfurl).setVisibility(View.INVISIBLE);
                    findViewById(R.id.lyt_neturl).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.lyt_udfurl).setVisibility(View.VISIBLE);
                    findViewById(R.id.lyt_neturl).setVisibility(View.VISIBLE);
                }

                SharedPrefUtil.setBooleanValue(MenuurlActivity.this, Constant.KEY_SETTING_MENU_DEFINE_URL, isChecked);
            }
        });

        Boolean is_udfurl = SharedPrefUtil.getBooleanValue(this, Constant.KEY_SETTING_MENU_DEFINE_URL);
        if (is_udfurl) {
            mUdfUrl.setText(Constant.URL_DEFAULT_MENU_CONSTANT);

            mThinkSwitch.setChecked(is_udfurl);

            mCheckUdfurl.setChecked(false);
            mNetpalAdapter.clearSelected();

            findViewById(R.id.lyt_udfurl).setVisibility(View.INVISIBLE);
            findViewById(R.id.lyt_neturl).setVisibility(View.INVISIBLE);

        } else {
            String udfmenuurl = SharedPrefUtil.getStringValue(this, Constant.KEY_URL_MENU_DEFINE);
            if ("".equals(udfmenuurl))
                mUdfUrl.setText("http://www.");
            else
                mUdfUrl.setText(udfmenuurl);
            //mUdfUrl.setSelection(mUdfUrl.getText().length());
        }

    }

    private void postUrl() {
        String s = mUdfUrl.getText().toString();
        if (null == s || "".equals(s)) {
            Toast.makeText(this, "网址无效", Toast.LENGTH_SHORT).show();
            return;
        }

        TaskUtil.startPostDataToUrlTask(
                Constant.URL_POST_NETPAL_MENUURL,
                "url=" + s, // url=s
                new TaskUtil.ITaskStringBack() {
                    @Override
                    public void back(String result) {
                        if("1".equals(result.replace(" ", ""))){
                            Toast.makeText(MenuurlActivity.this, "已经提交，静候审核", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MenuurlActivity.this, "提交有误，延后提交", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkMenuUrl() {
        String url = "";

        if (mThinkSwitch.isChecked()) {
            url = Constant.URL_DEFAULT_MENU_CONSTANT;
        }

        if (mCheckUdfurl.isChecked()) {
            url = mUdfUrl.getText().toString();
            // TODO 验证url有效性

        }

        if (null != mNetpalAdapter.getSelected()) {
            url = mNetpalAdapter.getSelected().getUrl();
        }

        if (!mThinkSwitch.isChecked() && !mCheckUdfurl.isChecked() && null == mNetpalAdapter.getSelected()) {
            url = Constant.URL_DEFAULT_MENU_CONSTANT;

            Toast.makeText(this, "使用默认目录URL", Toast.LENGTH_LONG).show();
        }

        SharedPrefUtil.setStringValue(this, Constant.KEY_URL_MENU_DEFINE, url);
        finish();
    }

    private void initData() {
        mNeturlList = new ArrayList();

        TaskUtil.startGetJsonFromUrlTask(
                Constant.URL_NETPAL_URL,
                new TaskUtil.ITaskStringBack() {
                    @Override
                    public void back(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            int v = jo.getInt("v");
                            //if(v != SharedPrefUtil.getIntValue(MenuurlActivity.this, Constant.KEY_INT_LOCAL_URL_VERSION)){
                            SharedPrefUtil.setIntegerValue(MenuurlActivity.this, Constant.KEY_INT_LOCAL_URL_VERSION, v);

                            String desc = jo.getString("d");
                            String urls = jo.getString("urls");

                            getUrls(urls);
                            //} TODO
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getUrls(String urls) {
        TaskUtil.startTransformJsonArrayTask(
                urls,
                new TaskUtil.ITaskListBack() {
                    @Override
                    public void back(List list) {
                        mNeturlList = list;
                        mNetpalAdapter = new NetpalAdapter(MenuurlActivity.this, mRcyvNeturl, list, MenuurlActivity.this);
                        Log.e("ard", "数量：" + list.size());
                        mRcyvNeturl.setAdapter(mNetpalAdapter);
                    }
                },
                NetpalBean.class
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkMenuUrl();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void flushMenuUrlSelected() {
        mCheckUdfurl.setChecked(false);
    }
}