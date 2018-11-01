package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * student_contact
 * @author 
 */
public class StudentContact implements Serializable {
    /**
     * 主键id
     */
    private int id;

    /**
     * 学生用户id
     */
    private int studentUserId;

    /**
     * 简历id
     */
    private int resumeId;

    /**
     * 学校id
     */
    private int schoolId;

    /**
     * 手机号
     */
    private String cellphone;

    /**
     * qq
     */
    private String qq;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 紧急联系人
     */
    private String urgentContact;

    /**
     * 紧急联系人手机号码
     */
    private String urgentCellphone;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private int createBy;

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

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getUrgentContact() {
        return urgentContact;
    }

    public void setUrgentContact(String urgentContact) {
        this.urgentContact = urgentContact;
    }

    public String getUrgentCellphone() {
        return urgentCellphone;
    }

    public void setUrgentCellphone(String urgentCellphone) {
        this.urgentCellphone = urgentCellphone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


}