package com.cuiweiyou.sharepoint.adpt;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuiweiyou.sharepoint.R;
import com.cuiweiyou.sharepoint.bean.NetpalBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/07/01,001.
 */
public class NetpalAdapter extends RecyclerView.Adapter<NetpalAdapter.NetpalViewHolder> {

    private IMenuUrlBack back;
    private RecyclerView rcyv;
    private List<NetpalBean> selected;
    private List<NetpalBean> list;
    private Context ctx;

    public NetpalAdapter(Context ctx, RecyclerView rcyv, List<NetpalBean> list, IMenuUrlBack back) {
        this.ctx = ctx;
        this.rcyv = rcyv;
        this.list = list;
        this.back = back;
        selected = new ArrayList<NetpalBean>();
    }

    @Override
    public NetpalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NetpalViewHolder(this, rcyv, View.inflate(ctx, R.layout.item_netpal_url, null));
    }

    @Override
    public void onBindViewHolder(NetpalViewHolder holder, int position) {
        NetpalBean bean = list.get(position);

        holder.author.setText(bean.getAoer());
        holder.url.setText(bean.getUrl());
        holder.desc.setText(bean.getDesc());

        if (selected.contains(bean))
            holder.select.setImageResource(R.mipmap.cb_checked);
        else
            holder.select.setImageResource(R.mipmap.cb_normal);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public NetpalBean getSelected() {
        if (selected.size() > 0)
            return selected.get(0);
        else
            return null;
    }

    public void clearSelected() {
        selected.clear();
        notifyDataSetChanged();
    }

    /**
     * 自定义控制器 内部封装了Item布局中的控件
     *
     * @author cuiweiyou.com
     */
    class NetpalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerView rcyv;
        private RecyclerView.Adapter adapter;
        public View itemnetpal;
        public TextView author;
        public TextView url;
        public TextView desc;
        public ImageView select;

        /**
         * 自定义控制器
         *
         * @param rcyv
         * @param view Item布局实例
         */
        public NetpalViewHolder(RecyclerView.Adapter adapter, RecyclerView rcyv, View view) {
            super(view);

            this.adapter = adapter;
            this.rcyv = rcyv;

            this.itemnetpal = view.findViewById(R.id.itemnetpal);
            this.author = (TextView) view.findViewById(R.id.author);
            this.url = (TextView) view.findViewById(R.id.url);
            this.desc = (TextView) view.findViewById(R.id.desc);
            this.select = (ImageView) view.findViewById(R.id.selected);

            itemnetpal.setOnClickListener(this);
            select.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selected: {
                    selected.clear();
                    selected.add(list.get(getLayoutPosition()));

                    back.flushMenuUrlSelected();

                    postAndNotifyAdapter(rcyv, adapter);
                    break;
                }
                case R.id.itemnetpal: {
                    Log.e("ard",
                            "item的位置：" + getPosition()         // 可用。但deprecated
                                    + ",id:" + getItemId()                   // -1
                                    + ", oldpos:" + getOldPosition()         // -1
                                    + ", lytpos:" + getLayoutPosition());    // 可用
                }
            }
        }

        private void postAndNotifyAdapter(final RecyclerView recyclerView, final RecyclerView.Adapter adapter) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (!recyclerView.isComputingLayout()) {
                        adapter.notifyDataSetChanged();
                    } else {
                        postAndNotifyAdapter(recyclerView, adapter);
                    }
                }
            });
        }
    }

    public interface IMenuUrlBack {
        void flushMenuUrlSelected();
    }
}
