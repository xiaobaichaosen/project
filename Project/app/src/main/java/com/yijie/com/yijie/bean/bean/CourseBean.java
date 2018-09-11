package com.yijie.com.yijie.bean.bean;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/6/6.
 */

public class CourseBean implements Serializable {
    private String danceString;
    private String artString;
    private String englishString;
    private String rollString;
    private String painoString;
    private String thoughtString;
    private String otherString;

    public String getDanceString() {
        return danceString;
    }

    public void setDanceString(String danceString) {
        this.danceString = danceString;
    }

    public String getArtString() {
        return artString;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "danceString='" + danceString + '\'' +
                ", artString='" + artString + '\'' +
                ", englishString='" + englishString + '\'' +
                ", rollString='" + rollString + '\'' +
                ", painoString='" + painoString + '\'' +
                ", thoughtString='" + thoughtString + '\'' +
                ", otherString='" + otherString + '\'' +
                '}';
    }

    public void setArtString(String artString) {
        this.artString = artString;
    }

    public String getEnglishString() {
        return englishString;
    }

    public void setEnglishString(String englishString) {
        this.englishString = englishString;
    }

    public String getRollString() {
        return rollString;
    }

    public void setRollString(String rollString) {
        this.rollString = rollString;
    }

    public String getPainoString() {
        return painoString;
    }

    public void setPainoString(String painoString) {
        this.painoString = painoString;
    }

    public String getThoughtString() {
        return thoughtString;
    }

    public void setThoughtString(String thoughtString) {
        this.thoughtString = thoughtString;
    }

    public String getOtherString() {
        return otherString;
    }

    public void setOtherString(String otherString) {
        this.otherString = otherString;
    }
}
