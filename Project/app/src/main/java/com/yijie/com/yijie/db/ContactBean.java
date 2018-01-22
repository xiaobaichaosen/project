package com.yijie.com.yijie.db;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/1/2.
 */

public class ContactBean implements Serializable{
    private String id;
    private String name;
    private  String phoneNumber;
    private  String zjNubmer;
    private String wxNubmer;
    private String qqNubmer;

    private String schoolSample;
    private String schoolEduction;
    private String schoolMonth;
    private String schoolType;
    private String schoolLine;
    private String schoolMode;
    private String schoolTime;
    public String getSchoolSample() {
        return schoolSample;
    }

    public void setSchoolSample(String schoolSample) {
        this.schoolSample = schoolSample;
    }

    public String getSchoolEduction() {
        return schoolEduction;
    }

    public void setSchoolEduction(String schoolEduction) {
        this.schoolEduction = schoolEduction;
    }

    public String getSchoolMonth() {
        return schoolMonth;
    }

    public void setSchoolMonth(String schoolMonth) {
        this.schoolMonth = schoolMonth;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchoolLine() {
        return schoolLine;
    }

    public void setSchoolLine(String schoolLine) {
        this.schoolLine = schoolLine;
    }

    public String getSchoolMode() {
        return schoolMode;
    }

    public void setSchoolMode(String schoolMode) {
        this.schoolMode = schoolMode;
    }

    public String getSchoolTime() {
        return schoolTime;
    }

    public void setSchoolTime(String schoolTime) {
        this.schoolTime = schoolTime;
    }




    public String getZjNubmer() {
        return zjNubmer;
    }

    public void setZjNubmer(String zjNubmer) {
        this.zjNubmer = zjNubmer;
    }

    public String getWxNubmer() {
        return wxNubmer;
    }

    public void setWxNubmer(String wxNubmer) {
        this.wxNubmer = wxNubmer;
    }

    public String getQqNubmer() {
        return qqNubmer;
    }

    public void setQqNubmer(String qqNubmer) {
        this.qqNubmer = qqNubmer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
