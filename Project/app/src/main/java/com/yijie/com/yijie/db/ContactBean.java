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
