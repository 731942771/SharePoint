package com.cuiweiyou.sharepoint.adpt;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 首页vp的适配器
 */
public class CenterPageadapter extends PagerAdapter {

    private List<View> list;

    public CenterPageadapter(List<View> list) {
        this.list = list;
    }
    /**
     * 页面总数
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 1. 初次显示页面0时，执行2次
     * 当前显示页索引为0时没有上一页。经系统传入，创建页面0，然后又执行一次出入1，创建页面1.
     * instantiateItem. container：ViewPager, position:0 当前页
     * instantiateItem. container：ViewPager, position:1 下一页
     * 如果初始setCurrentItem页不是第1页（索引0），那么这个方法执行3次
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 1.在mViewPager里添加页面
        container.addView(list.get(position));
        // 将页面的索引赋给页面做tag，以备不时之需
        list.get(position).setTag(position);
        // 2.返回这个页面
        return list.get(position);
    }
    /**
     * 2. 确定哪个页面View和instantiateitem(ViewGroup,int)返回的指定key对象object关联
     * @param view 数据源中加载的页面
     * @param object 执行instantiateItem时缓存的页面
     * @return 当加载的页面和已经缓存的为同一个时，直接使用缓存的
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 3.清理过多加重的页面，清理内存
     * container:mViewPager
     * position:移出屏幕2个位置，须要移除的页面索引
     * object:被移除的页面View
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
