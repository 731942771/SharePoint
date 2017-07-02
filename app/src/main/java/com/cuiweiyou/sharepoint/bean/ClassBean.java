package com.cuiweiyou.sharepoint.bean;

/**
 * 课程bean
 */
public class ClassBean {

    private String v;
    private String c;
    private String t;
    private String u;

    public ClassBean(){}

    /**
     *
     * @param v 版本
     * @param c 课程号
     * @param t 标题
     * @param u URL
     */
    public ClassBean(String v, String c, String t, String u){
        this.v = v;
        this.c = c;
        this.t = t;
        this.u = u;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getV() {
        return v;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getC() {
        return c;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getT() {
        return t;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getU() {
        return u;
    }
}
