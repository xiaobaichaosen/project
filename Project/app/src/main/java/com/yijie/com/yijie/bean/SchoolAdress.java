package com.yijie.com.yijie.bean;

/**
 * Created by 奕杰平台 on 2018/4/19.
 */

public class SchoolAdress {
    //adapter用来判断是加载更多还是要展示得数据
    private int type;
    private String name;
    private String detailAdress;
    public int getType() {
        return type;
    }

    public String getDetailAdress() {
        return detailAdress;
    }

    public void setDetailAdress(String detailAdress) {
        this.detailAdress = detailAdress;
    }

    public void setType(int type) {
        this.type = type;
    }

    //纬度
    private  String lat;
    //经度
    private String lon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
