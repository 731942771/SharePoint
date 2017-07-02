package com.cuiweiyou.sharepoint.util;

/**
 * 延时工具
 * @author www.gaohaiyan.com
 */
public class DelayUtil {

    private DelayUtil(){}

    /**
     * 执行延时
     * @param seconds 毫秒数
     */
    public static void doDelay(final long seconds){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 执行延时
     * @param seconds 毫秒数
     * @param back 回调
     */
    public static void doDelay(final long seconds, final DelayBack back){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                back.delayFinished();
            }
        }).start();
    }

    /**
     * 回掉器接口
     */
    public interface DelayBack{
        void delayFinished();
    }
}
