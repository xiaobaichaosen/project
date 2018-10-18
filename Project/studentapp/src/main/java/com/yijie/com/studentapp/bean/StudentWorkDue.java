package com.yijie.com.studentapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 学生工作经历 wechat
 * @author 
 */
public class StudentWorkDue implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 学生信息id
     */
    private Integer studentInfoId;

    /**
     * 学生用户
     */
     private Integer studentUserId;

    /**
     * 简历id
     */
    private Integer resumeId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 工作的时间
     */
    private String workDue;

    /**
     * 职务
     */
    private String post;

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

    /**
     * 工作描述
     */
    private String description;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkDue() {
        return workDue;
    }

    public void setWorkDue(String workDue) {
        this.workDue = workDue;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(Integer studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }


}