package com.cuiweiyou.sharepoint.fmg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.aty.MainActivity;
import com.cuiweiyou.sharepoint.util.ScreenUtil;

/**
 * 目录fmg
 */
public class ThinkFragment extends Fragment {

    public View mView;
    public MainActivity.SlideBtnClickBack mSlideClickBack;
    public MainActivity.MenuSelectedBack mMenuSelectedBack;
    private ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 更新控件高度
     */
    public void flushViewHeight(int id, int height) {
        View v = mView.findViewById(id);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = height;
        v.setLayoutParams(layoutParams);
    }

    /**
     * 更新控件宽度
     */
    public void flushViewWidth(int id, int width){
        View v = mView.findViewById(id);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = width;
        v.setLayoutParams(layoutParams);
    }

    /**
     * 更新控件高度
     */
    public void flushViewHeight(View v, int height) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = height;
        v.setLayoutParams(layoutParams);
    }

    /**
     * 更新控件宽度
     */
    public void flushViewWidth(View v, int width){
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = width;
        v.setLayoutParams(layoutParams);
    }

    /**
     * 更新状态栏高度
     */
    public void flushStatusBarHeight() {
        View statusbar = mView.findViewById(R.id.status_bar);
        ViewGroup.LayoutParams layoutParams = statusbar.getLayoutParams();
        layoutParams.height = ScreenUtil.getStatusBarHeight(getActivity());
        statusbar.setLayoutParams(layoutParams);
    }

    /**
     * 更新标题栏高度
     */
    public void flushTitleBarHeight() {
        View navigationbar = mView.findViewById(R.id.titlebar);
        ViewGroup.LayoutParams layoutParams = navigationbar.getLayoutParams();
        layoutParams.height = ScreenUtil.getStatusBarHeight(getActivity()) * 2;
        navigationbar.setLayoutParams(layoutParams);
    }

    /**
     * 更新fmg的页面宽度
     */
    public void flushViewWidth(){
        View dView = getView();
        ViewGroup.LayoutParams layoutParams = dView.getLayoutParams();
        layoutParams.width = ScreenUtil.getScreenWidth(getActivity()) / 5 * 4;
        dView.setLayoutParams(layoutParams);
    }

    /**
     * 主页的侧拉按钮点击回掉
     */
    public void setSlideClickBack(MainActivity.SlideBtnClickBack back) {
        this.mSlideClickBack = back;
    }

    /**
     * 抽屉的item点击回掉
     */
    public void setOnMenuSelectedBack(MainActivity.MenuSelectedBack back) {
        this.mMenuSelectedBack = back;
    }

    /**
     * 跳转aty
     */
    public void nextActivity(Class clazz){
        getActivity().startActivity(new Intent(getActivity(), clazz));
    }

    public void showProgress(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("加载中");
        pd.show();
    }

    public void hideProgress(){
        if(null != pd)
            pd.dismiss();
        pd = null;
    }
}
