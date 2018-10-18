package com.yijie.com.studentapp.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 学校实习项目
 *
 * @author
 */
public class SchoolPractice implements Serializable {
    /**
     * 实习详情id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 学校id
     */
    private Integer schoolId;

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
    private BigDecimal salary;

    /**
     * 到京时间
     */
    private String toBeijingTime;

    /**
     * 费用合计
     */
    private BigDecimal manageFee;

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

    private String orderTime;

    /**
     * 实习项目名称
     */
    private String projectName;

    /**
     * 实习项目状态
     */
    private Integer status;

    /**
     * 实习年份
     */
    private String year;

    /**
     * 学校头像
     */
    private String litimg;
    /**
     * 手机号
     */
    private String cellphone;
    /**
     * 座机号
     */
    private String telephone;
    /**
     * 联系人名字
     */
    private String user_name;


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
    private Integer schoolContactId;
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
    private Integer isRead;
    /**
     *  接收项目人id
     */
    private Integer acceptId;
    /**
     * 学校名称
     */
    private String name;
    /**
     * 院校logo图片
     */
    private String logo;

    /**
     * 园所提需求人数设定
     * @return
     */
    private Integer kindpeoNumSet;

    public Integer getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Integer acceptId) {
        this.acceptId = acceptId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getHeadPic() {

        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getRealName() {

        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSchoolContactId() {

        return schoolContactId;
    }

    public void setSchoolContactId(Integer schoolContactId) {
        this.schoolContactId = schoolContactId;
    }
    public String getTrainTime() {

        return trainTime;
    }
    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    public String getMemoContent() {

        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getKindpeoNumSet() {
        return kindpeoNumSet;
    }

    public void setKindpeoNumSet(Integer kindpeoNumSet) {
        this.kindpeoNumSet = kindpeoNumSet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getToBeijingTime() {
        return toBeijingTime;
    }

    public void setToBeijingTime(String toBeijingTime) {
        this.toBeijingTime = toBeijingTime;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public String getLitimg() {
        return litimg;
    }

    public void setLitimg(String litimg) {
        this.litimg = litimg;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}