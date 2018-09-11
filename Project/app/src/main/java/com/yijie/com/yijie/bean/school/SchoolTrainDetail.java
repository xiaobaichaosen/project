package com.yijie.com.yijie.bean.school;

import java.io.Serializable;
import java.util.Date;

/**
 * school_train_detail
 * @author 
 */
public class SchoolTrainDetail implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 院校负责人联系人id
     */
    private Integer schoolContactId;

    /**
     * 实习详情id
     */
    private Integer schoolPracticeId;

    /**
     * 培训老师
     */
    private Integer teacherId;

    /**
     * 院校联系人电话
     */
    private String schoolContactCelphone;

    /**
     * 院校联系人名称
     */
    private String schoolContactName;

    /**
     * 培训时间
     */
    private String trainTime;

    /**
     * 培训状态
     */
    private String trainStatus;

    /**
     * 地址
     */
    private String trainAddress;

    /**
     * 到京时间
     */
    private String toBeijingTime;

    /**
     * 到京方式
     */
    private String toBeijingMethod;

    /**
     * 报名人数
     */
    private Integer enrollNum;

    /**
     * 报销路费
     */
    private String wipeUpFee;

    /**
     * 实习自我提升
     */
    private String promote;

    /**
     * 实习结束时间
     */
    private String endTime;

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

    private String longitude;
    private String latitude;

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    private String detailAddress;
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 更新人
     */
    private Integer updateBy;

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

    public Integer getSchoolContactId() {
        return schoolContactId;
    }

    public void setSchoolContactId(Integer schoolContactId) {
        this.schoolContactId = schoolContactId;
    }

    public Integer getSchoolPracticeId() {
        return schoolPracticeId;
    }

    public void setSchoolPracticeId(Integer schoolPracticeId) {
        this.schoolPracticeId = schoolPracticeId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getSchoolContactCelphone() {
        return schoolContactCelphone;
    }

    public void setSchoolContactCelphone(String schoolContactCelphone) {
        this.schoolContactCelphone = schoolContactCelphone;
    }

    public String getSchoolContactName() {
        return schoolContactName;
    }

    public void setSchoolContactName(String schoolContactName) {
        this.schoolContactName = schoolContactName;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }

    public String getTrainAddress() {
        return trainAddress;
    }

    public void setTrainAddress(String trainAddress) {
        this.trainAddress = trainAddress;
    }

    public String getToBeijingTime() {
        return toBeijingTime;
    }

    public void setToBeijingTime(String toBeijingTime) {
        this.toBeijingTime = toBeijingTime;
    }

    public String getToBeijingMethod() {
        return toBeijingMethod;
    }

    public void setToBeijingMethod(String toBeijingMethod) {
        this.toBeijingMethod = toBeijingMethod;
    }

    public Integer getEnrollNum() {
        return enrollNum;
    }

    public void setEnrollNum(Integer enrollNum) {
        this.enrollNum = enrollNum;
    }

    public String getWipeUpFee() {
        return wipeUpFee;
    }

    public void setWipeUpFee(String wipeUpFee) {
        this.wipeUpFee = wipeUpFee;
    }

    public String getPromote() {
        return promote;
    }

    public void setPromote(String promote) {
        this.promote = promote;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }




}