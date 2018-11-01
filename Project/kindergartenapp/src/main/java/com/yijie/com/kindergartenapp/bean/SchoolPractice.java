package com.yijie.com.kindergartenapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * school_practice
 *
 * @author
 */
public class SchoolPractice implements Serializable {


    /**
     * 实习详情id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 学校id
     */
    private int schoolId;

    /**
     * 学历
     */
    private String education;

    /**
     * 学校地址
     */
    private String location;

    /**
     * 实习薪资
     */
    private String salary;

    /**
     * 到京时间
     */
    private String toBeijingTime;

    /**
     * 费用合计
     */
    private String manageFee;

    /**
     * 实习月份
     */
    private String practiceMonth;

    /**
     * 实习类型
     */
    private String practiceType;

    /**
     * 实习期限
     */
    private String timelimit;

    /**
     * 管理模式
     */
    private String manageModel;

    /**
     * 预约时间
     */
    private Date orderTime;

    /**
     * 实习项目名称
     */
    private String projectName;

    /**
     * 实习项目状态
     */
    private int status;

    /**
     * 实习年份
     */
    private String year;

    /**
     * 创建时间
     */

    private String createTime;

    /**
     * 更新时间
     */

    private String updateTime;

    /**
     * 备忘录
     */
    private String memoContent;
    /**
     * 培训时间
     */

    private String trainTime;
    /**
     * 负责人id
     */
    private int schoolContactId;
    /**
     * 负责人姓名
     */
    private String realName;
    /**
     * 负责人头像
     */
    private String headPic;
    /**
     * 实习项目是否读
     */
    private int isRead;
    /**
     *  接收项目人id
     */
    private int acceptId;
    /**
     * 学校名称
     */
    private String name;
    /**
     * 院校logo图片
     */
    private String logo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getToBeijingTime() {
        return toBeijingTime;
    }

    public void setToBeijingTime(String toBeijingTime) {
        this.toBeijingTime = toBeijingTime;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getPracticeMonth() {
        return practiceMonth;
    }

    public void setPracticeMonth(String practiceMonth) {
        this.practiceMonth = practiceMonth;
    }

    public String getPracticeType() {
        return practiceType;
    }

    public void setPracticeType(String practiceType) {
        this.practiceType = practiceType;
    }

    public String getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(String timelimit) {
        this.timelimit = timelimit;
    }

    public String getManageModel() {
        return manageModel;
    }

    public void setManageModel(String manageModel) {
        this.manageModel = manageModel;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    public int getSchoolContactId() {
        return schoolContactId;
    }

    public void setSchoolContactId(int schoolContactId) {
        this.schoolContactId = schoolContactId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(int acceptId) {
        this.acceptId = acceptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getKindpeoNumSet() {
        return kindpeoNumSet;
    }

    public void setKindpeoNumSet(int kindpeoNumSet) {
        this.kindpeoNumSet = kindpeoNumSet;
    }

    /**
     * 园所提需求人数设定
     * @return
     */
    private int kindpeoNumSet;


}