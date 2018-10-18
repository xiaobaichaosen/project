package com.yijie.com.studentapp.bean;


import java.util.Date;
import java.util.Objects;

/**
 * 奕杰app 学生首页 已分配列表 已匹配简历详情中 已投递
 */
public class StudentResumeAlreadyDelivery {

    /**
     * 投递园所
     */
    private String deliveryKinder;
    /**
     * 投递时间
     */
    private String deliveryTime;
    /**
     * 同伴
     */
    private String companion;

    public String getDeliveryKinder() {
        return deliveryKinder;
    }

    public void setDeliveryKinder(String deliveryKinder) {
        this.deliveryKinder = deliveryKinder;
    }
    public String getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }


}
