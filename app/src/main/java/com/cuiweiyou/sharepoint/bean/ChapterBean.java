package com.cuiweiyou.sharepoint.bean;

import java.util.List;

/**
 * 章节bean
 */
public class ChapterBean {

    private String cpt;
    private String t;
    private String v;
    private List<ClassBean> cs;

    public ChapterBean(){}

    /**
     *
     * @param cpt 章节号
     * @param t 标题
     * @param v 版本
     * @param cs 下属课程集合
     */
    public ChapterBean(String cpt, String t, String v, List<ClassBean> cs){
        this.cpt = cpt;
        this.t = t;
        this.v = v;
        this.cs = cs;
    }

    public void setCpt(String cpt) {
        this.cpt = cpt;
    }
    public String getCpt() {
        return cpt;
    }

    public void setT(String t) {
        this.t = t;
    }
    public String getT() {
        return t;
    }

    public void setV(String v) {
        this.v = v;
    }
    public String getV() {
        return v;
    }

    public void setCs(List<ClassBean> cs) {
        this.cs = cs;
    }
    public List<ClassBean> getCs() {
        return cs;
    }

}