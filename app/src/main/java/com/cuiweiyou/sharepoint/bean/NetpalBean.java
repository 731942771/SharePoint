package com.cuiweiyou.sharepoint.bean;

/**
 * Created by Administrator on 2017/07/01,001.
 */
public class NetpalBean {

    private String aoer;
    private String desc;
    private String v;
    private String url;

    public NetpalBean() {
    }

    public NetpalBean(String aoer, String desc, String v, String url) {
        this.aoer = aoer;
        this.desc = desc;
        this.v = v;
        this.url = url;
    }

    public void setAoer(String aoer) {
        this.aoer = aoer;
    }

    public String getAoer() {
        return aoer;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getV() {
        return v;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}