package com.yijie.com.yijie.bean.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * 学生用户和园所需求类（中间表po类）
 * @author 
 */
public class StudentuserKinderneed  implements Serializable {
    /**
     * 简历状态(0：学生自己投的状态为 待选 1：培训老师推荐的状态为待选状态 2：园所同意接收简历 3：园所放弃接收)
     */
    private int status;
    private int    studentUserId;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String  updateTime;
    private int id;

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

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
    private int stuAge;
    /**
     * 身高
     */
    private int height;

    /**
     * 体重
     */
    private Double weight;
    /**
     * 学生特长
     */
    private String hobby;
    /**
     * 园所需求人数
     */
    private int studentNum;
    /**
     * 学校名称
     */
    private String schoolName;


    /**
     * 园所地址
     */
    private String kindAddress;

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getKindAddress() {
        return kindAddress;
    }

    public void setKindAddress(String kindAddress) {
        this.kindAddress = kindAddress;
    }

    public String getKindDetailAddress() {
        return kindDetailAddress;
    }

    public void setKindDetailAddress(String kindDetailAddress) {
        this.kindDetailAddress = kindDetailAddress;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    /**

     * 园所详细地址
     */
    private String kindDetailAddress;

    /**
     * 园所简介
     */
    private String summary;

    /**
     * 园所名称
     */
    private String kindName;
    /**
     * 学校实习项目名称
     */
    private String projectName;


    private static final long serialVersionUID = 1L;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public int getHeight() {
        return height;
    }



    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public int getStuAge() {
        return stuAge;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }


}