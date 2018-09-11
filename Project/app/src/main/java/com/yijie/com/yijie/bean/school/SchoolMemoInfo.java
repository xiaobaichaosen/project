package com.yijie.com.yijie.bean.school;

import java.io.Serializable;
import java.util.Date;

public class SchoolMemoInfo implements Serializable {
    /**
     * 谁发送的0是自己发送，1是其他人发送的
     */
    private int sendType;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 学校实习详情id
     */
    private int schoolPracticeId;

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
     * 是否已删除（0未删除，1已删除）
     */
    private int isDel;

    /**
     * 备忘录内容
     */
    private String memoContent;
    /**
     * 实习项目名称
     */
    private String projectName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getSchoolPracticeId() {
        return schoolPracticeId;
    }

    public void setSchoolPracticeId(Integer schoolPracticeId) {
        this.schoolPracticeId = schoolPracticeId;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
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

    public int  getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }





}