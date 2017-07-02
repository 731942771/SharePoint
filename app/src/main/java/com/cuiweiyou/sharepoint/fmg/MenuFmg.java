package com.cuiweiyou.sharepoint.fmg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cuiweiyou.sharepoint.Constant;
import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.bean.ChapterBean;
import com.cuiweiyou.sharepoint.util.SharedPrefUtil;
import com.cuiweiyou.sharepoint.util.TaskUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 目录fmg
 */
public class MenuFmg extends ThinkFragment {

    private ExpandableListView mMenu;
    private List<ChapterBean> mChapters;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_menu, container, false);

        showProgress();

        initView();
        initData();

        return mView;
    }

    private void initView() {
        mMenu = (ExpandableListView) mView.findViewById(R.id.menu);

        // 子条目的点击事件
        mMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            // groupPosition：被点击子条目所在的组索引
            // childPosition：被点击子条目在本组中的索引
            // id：被点击的子条目在所属列表中的id，一般和childPosition相同
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                ChapterBean chapterBean = mChapters.get(groupPosition);

                mMenuSelectedBack.onMenuSelected(chapterBean, childPosition);

                return true;
            }
        });
    }

    private void initData() {
        String menuurl = SharedPrefUtil.getStringValue(getActivity(), Constant.URL_DEFAULT_MENU_CONSTANT);

        if("".equals(menuurl)){
            menuurl = Constant.URL_DEFAULT_MENU_CONSTANT;
        }

        TaskUtil.startGetJsonFromUrlTask(
                menuurl,
                new TaskUtil.ITaskStringBack() {
                    @Override
                    public void back(String json) {
                        try {
                            JSONObject jo = new JSONObject(json);
                            String title = jo.getString("title"); // 标题
                            String logo = jo.getString("logo"); // logo

                            String aoer = jo.getString("aoer"); // 作者
                            String desc = jo.getString("desc"); // 说明
                            String v = jo.getString("v"); // 版本
                            String menu = jo.getString("menu"); // 目录数据

                            flushTitle(title, logo, aoer, desc, v);

                            transformJson(menu);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgress();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            hideProgress();
                        }
                    }
                });
    }

    private void flushTitle(String title, String logo, String aoer, String desc, String v) {
        Glide.with(getActivity())
                .load(logo)
                .placeholder(R.mipmap.splogo_90)
                .crossFade()
                .into((ImageView)mView.findViewById(R.id.logo));

        ((TextView)mView.findViewById(R.id.title)).setText(title);
        ((TextView)mView.findViewById(R.id.author)).setText(aoer);
        ((TextView)mView.findViewById(R.id.desc)).setText(desc);
        ((TextView)mView.findViewById(R.id.v)).setText(v);
    }

    private void transformJson(String json) {
        TaskUtil.startTransformJsonArrayTask(
                json,
                new TaskUtil.ITaskListBack() {
                    @Override
                    public void back(List list) {
                        hideProgress();

                        mChapters = list;

                        flushMenu();
                    }
                });
    }

    private void flushMenu() {
        mMenu.setAdapter(new com.cuiweiyou.sharepoint.adpt.MenuAdapter(getActivity(), mChapters));

        for (int i = 0; i < mChapters.size(); i++) {
            mMenu.expandGroup(i);
        }

        mMenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }
}
