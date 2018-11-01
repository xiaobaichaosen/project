package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/6/6.
 */

public class StayBean implements Serializable {
    private String upString="";
    private String downString="";
    private String outString="";
    private String otherString="";
    private String foureightString="";
    private String eighttwelveString="";
    private String twoString="";
    private String threeString="";
    private String wuString="";
    public String getFoureightString() {
        return foureightString;
    }

    public void setFoureightString(String foureightString) {
        this.foureightString = foureightString;
    }

    public String getEighttwelveString() {
        return eighttwelveString;
    }

    public String getWuString() {
        return wuString;
    }

    public void setWuString(String wuString) {
        this.wuString = wuString;
    }

    public void setEighttwelveString(String eighttwelveString) {
        this.eighttwelveString = eighttwelveString;
    }

    public String getTwoString() {
        return twoString;
    }

    public void setTwoString(String twoString) {
        this.twoString = twoString;
    }

    public String getThreeString() {
        return threeString;
    }

    public void setThreeString(String threeString) {
        this.threeString = threeString;
    }



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
        return upString +"、" + downString+"、" + outString+"、" + otherString+"、" + foureightString+"、" + eighttwelveString+"、" + twoString+"、" + threeString+"、" + wuString;

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
