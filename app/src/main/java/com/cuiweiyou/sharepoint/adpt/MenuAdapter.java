package com.cuiweiyou.sharepoint.adpt;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.bean.ChapterBean;
import com.cuiweiyou.sharepoint.bean.ClassBean;

import java.util.List;

/**
 * 目录分组列表适配器
 */
public class MenuAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ChapterBean> list;

    public MenuAdapter(Context context, List<ChapterBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {    // 外层父条目总数
        return list == null || list.size() < 1 ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {    // 内层子条目总数
        List<ClassBean> listChild = list.get(groupPosition).getCs();
        return listChild == null || listChild.size() < 1 ? 0 : listChild.size();
    }

    @Override
    public Object getGroup(int groupPosition) {    // 外层父条目bean
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {    // 内层子条目bean
        return list.get(groupPosition).getCs().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {    // 外层父条目id
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {    // 内层子条目id
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {    // 显示时复用已有对象
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_group_menu, null);
        }

        TextView mGroup = (TextView) convertView.findViewById(R.id.title);    // 组名

        mGroup.setText(list.get(groupPosition).getT());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_child_menu, null);
        }

        TextView mChild = (TextView) convertView.findViewById(R.id.title);

        mChild.setText(list.get(groupPosition).getCs().get(childPosition).getT());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {    // 内层子条目是否接收点击事件
        return true;
    }
}
