package com.yijie.com.studentapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    private Integer resumeId;
    /**
     * 学生用户id
     */
    private Integer studentUserId;

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
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
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

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }


}