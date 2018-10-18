package com.yijie.com.studentapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 学生app：浏览足迹
 * @author 
 */
public class StudentBrowseFootmark  implements Serializable {

    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 园所需求id
     */
    private Integer kinderNeedId;

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getKinderNeedId() {
        return kinderNeedId;
    }

    public void setKinderNeedId(Integer kinderNeedId) {
        this.kinderNeedId = kinderNeedId;
    }

    public Integer getKinderId() {
        return kinderId;
    }

    public void setKinderId(Integer kinderId) {
        this.kinderId = kinderId;
    }

    /**
     * 园所id
     */

    private Integer kinderId;
    /**
     * 浏览时间
     */
    private String browseTime;

    /**
     * 园所全称
     */
    private String kindName;

    /**
     * 园所地址
     */
    private String kindAddress;

    /**
     * 学生人数要求(招聘人数）
     */
    private Integer studentNum;

    /**
     * 园所薪资设定
     */
    private String kinderSalarySet;

    /**
     * 园所头像
     */
    private String headPic;

    /**
     * 统计学生投递园所数
     */
    private Integer countDelivery;
    /**
     * 统计园所接收人数
     */
    private Integer countReceive;

    /**
     * 统计园所剩余学生名额
     */
    private Integer countSurplus;


    private static final long serialVersionUID = 1L;
    public String getBrowseTime() {
        return browseTime;
    }
    public void setBrowseTime(String browseTime) {
        this.browseTime = browseTime;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKindAddress() {
        return kindAddress;
    }

    public void setKindAddress(String kindAddress) {
        this.kindAddress = kindAddress;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public String getKinderSalarySet() {
        return kinderSalarySet;
    }

    public void setKinderSalarySet(String kinderSalarySet) {
        this.kinderSalarySet = kinderSalarySet;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getCountDelivery() {
        return countDelivery;
    }

    public void setCountDelivery(Integer countDelivery) {
        this.countDelivery = countDelivery;
    }

    public Integer getCountReceive() {
        return countReceive;
    }

    public void setCountReceive(Integer countReceive) {
        this.countReceive = countReceive;
    }

    public Integer getCountSurplus() {
        return countSurplus;
    }

    public void setCountSurplus(Integer countSurplus) {
        this.countSurplus = countSurplus;
    }


}