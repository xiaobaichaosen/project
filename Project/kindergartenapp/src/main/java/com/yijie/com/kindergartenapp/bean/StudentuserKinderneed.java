package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * studentuser_kinderneed
 * @author 
 */
public class StudentuserKinderneed extends StudentuserKinderneedKey implements Serializable {
    /**
     * 简历状态(0：学生自己投的状态为 待选 1：培训老师推荐的状态为待选状态 2：园所同意接收简历 3：园所放弃接收)
     */
    private Integer status;
    /**
     * 学生头像
     */
    private String headPic;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 学生年龄
     */
    private Integer stuAge;
    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Double weight;
    /**
     * 学生特长
     */
    private String hobby;

    private static final long serialVersionUID = 1L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }




}