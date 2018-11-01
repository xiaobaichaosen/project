package com.yijie.com.kindergartenapp.bean;



import java.io.Serializable;
import java.util.Date;


/**
 * 学生工作经历 wechat
 * @author 
 */
public class StudentWorkDue implements Serializable {
    /**
     * 主键id
     */
    private int id;
    /**
     * 学生信息id
     */
    private int studentInfoId;

    /**
     * 学生用户
     */
     private int studentUserId;

    /**
     * 简历id
     */
    private int resumeId;

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
    private int createBy;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(int studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }




}