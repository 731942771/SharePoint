package com.cuiweiyou.sharepoint.util;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cuiweiyou.sharepoint.ThinkApplication;
import com.cuiweiyou.sharepoint.bean.ChapterBean;

import java.io.IOException;
import java.util.List;

/**
 * 异步任务工具类
 */
public class TaskUtil {
    private TaskUtil() {
    }

    /**
     * 从远程获取json
     *
     * @param url
     * @param back
     */
    public static void startGetJsonFromUrlTask(final String url, final ITaskStringBack back) {
        boolean netState = IntentStateUtil.getNetState(ThinkApplication.getContext());
        if(!netState){
            Toast.makeText(ThinkApplication.getContext(), "不可用的网络状态", Toast.LENGTH_LONG).show();
            return;
        }

        new AsyncTask<Void, String, String>() {
            String json = null;

            @Override
            protected String doInBackground(Void... params) {
                try {
                    json = HttpRequestAndPostUtil.getJsonObject(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);

                back.back(string);
            }
        }.execute();
    }

    public static void startTransformJsonArrayTask(final String json, final ITaskListBack back) {
        new AsyncTask<Void, List<ChapterBean>, List<ChapterBean>>() {

            @Override
            protected List<ChapterBean> doInBackground(Void... params) {
                return JSON.parseArray(json, ChapterBean.class); // 阿里巴巴的工具类
            }

            @Override
            protected void onPostExecute(List<ChapterBean> result) {
                super.onPostExecute(result);

                back.back(result);
            }
        }.execute();
    }

    public static void startTransformJsonArrayTask(final String json, final ITaskListBack back, final Class clazz) {
        new AsyncTask<Void, List, List>() {

            @Override
            protected List doInBackground(Void... params) {
                return JSON.parseArray(json, clazz); // 阿里巴巴的工具类
            }

            @Override
            protected void onPostExecute(List result) {
                super.onPostExecute(result);

                back.back(result);
            }
        }.execute();
    }

    public static void startPostDataToUrlTask(final String url, final String args, final ITaskStringBack back) {
        new AsyncTask<Void, String, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String result = "-1";
                try {
                    result = HttpRequestAndPostUtil.post(url, args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("ard", "post3回的结果：" + result);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                back.back(result);
            }
        }.execute();
    }

    public interface ITaskStringBack {
        public void back(String json);
    }

    public interface ITaskListBack {
        public void back(List list);
    }
}
