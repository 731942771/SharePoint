package com.cuiweiyou.thinkcheckwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/06/30,030.
 */
public class ThinkSwitchWidget extends RelativeLayout {

    /**
     * view_switch.xml自定义布局中的 标题控件
     **/
    private TextView mTitle;
    /**
     * 提示控件
     **/
    private TextView mInfo;
    /**
     * 复选框
     **/
    private CheckBox mChecked;

    /**
     * attr.xml自定义属性中的 控件标题
     **/
    private String title;
    /**
     * 控件开启时提示
     **/
    private String oninfo;
    /**
     * 控件关闭时提示
     **/
    private String offinfo;
    private ThinkCheckedListener listener;

    /**
     * @param context 上下文
     * @param attrs   xml里引用此组合控件时配置的属性的封装对象
     */
    public ThinkSwitchWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 1.从activity的xml里注册引用此自定义组合控件时配置的属性中读取配置值（xml里的自定义约束，属性名称）
        title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "think_switch_title");
        oninfo = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "think_switch_on");
        offinfo = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "think_switch_off");

        // 2.将自定义的布局作为本自定义控件的显示布局
        initView(context);

        // 默认没有勾选
        mInfo.setText(offinfo);
        mTitle.setText(title);
    }

    /**
     * xml转View
     */
    private void initView(Context context) {

        // 第3个参数 ！！！，见证奇迹的时刻 ！！！
        View.inflate(context, R.layout.view_think_switch, ThinkSwitchWidget.this);

        // 实例化自定义布局中的子控件
        mTitle = (TextView) findViewById(R.id.switch_title);     // 更新提示条目标题
        mInfo = (TextView) findViewById(R.id.switch_info);       // 更新提示
        mChecked = (CheckBox) findViewById(R.id.switch_checked); // 更新控制复选框

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(!isChecked());

                if(null != listener)
                    listener.isChecked(isChecked());
            }
        });
    }

    /**
     * 检测复选框是否勾选
     */
    public boolean isChecked() {
        // 根据复选框的属性
        return mChecked.isChecked();
    }

    /**
     * 设置勾选，更新提示
     */
    public void setChecked(boolean checked) {
        // 调用复选框的方法
        mChecked.setChecked(checked);

        if (checked) {
            mInfo.setText(oninfo);
        } else {
            mInfo.setText(offinfo);
        }
    }

    public void setThinkCheckedListener(ThinkCheckedListener listener) {
        this.listener = listener;
    }

    public interface ThinkCheckedListener {
        void isChecked(boolean isChecked);
    }
}
