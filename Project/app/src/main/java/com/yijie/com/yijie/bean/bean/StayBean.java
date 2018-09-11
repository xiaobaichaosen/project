package com.yijie.com.yijie.bean.bean;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/6/6.
 */

public class StayBean implements Serializable {
    private String upString;
    private String downString;
    private String outString;
    private String otherString;

    public String getUpString() {
        return upString;
    }

    public void setUpString(String upString) {
        this.upString = upString;
    }

    public String getDownString() {
        return downString;
    }

    public void setDownString(String downString) {
        this.downString = downString;
    }

    public String getOutString() {
        return outString;
    }

    @Override
    public String toString() {
        return "StayBean{" +
                "upString='" + upString + '\'' +
                ", downString='" + downString + '\'' +
                ", outString='" + outString + '\'' +
                ", otherString='" + otherString + '\'' +
                '}';
    }

    public void setOutString(String outString) {
        this.outString = outString;
    }

    public String getOtherString() {
        return otherString;
    }

    public void setOtherString(String otherString) {
        this.otherString = otherString;
    }
}
