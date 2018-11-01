package com.yijie.com.yijie.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生教育背景(wechat)
 * @author 
 */
public class StudentEducation implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 简历id
     */
    private int resumeId;
    /**
     * 学生用户id
     */
    private int studentUserId;

    /**
     * 毕业院校
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 学历
     */
    private String education;

    /**
     * 入学时间
     */
    private String startTime;

    /**
     * 毕业时间
     */
    private String graduateTime;

    /**
     * 爱好/特长
     */
    private String hobby;

    /**
     * 创建人
     */
    private int createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }


}