package com.yijie.com.yijie.bean;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/5/2.
 * 实习详情bean
 */

public class InternshipDetail implements Serializable {
    private String education;
    private String mouth;
    private String internshipType;
    private String internshipDate;
    private String mode;
    private String appontTime;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getInternshipType() {
        return internshipType;
    }

    public void setInternshipType(String internshipType) {
        this.internshipType = internshipType;
    }

    public String getInternshipDate() {
        return internshipDate;
    }

    public void setInternshipDate(String internshipDate) {
        this.internshipDate = internshipDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAppontTime() {
        return appontTime;
    }

    public void setAppontTime(String appontTime) {
        this.appontTime = appontTime;
    }

    public IntershipMoney getIntershipMoney() {
        return intershipMoney;
    }

    public void setIntershipMoney(IntershipMoney intershipMoney) {
        this.intershipMoney = intershipMoney;
    }

    private IntershipMoney intershipMoney;
}
