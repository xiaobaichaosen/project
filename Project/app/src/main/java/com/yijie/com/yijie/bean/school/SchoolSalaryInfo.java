package com.yijie.com.yijie.bean.school;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * school_salary_info
 * @author 
 */
public class SchoolSalaryInfo implements Serializable {
    /**
     * 实习薪资
     */
    private Integer id;

    /**
     * 实习详情_id
     */
    private Integer schoolPracticeId;

    /**
     * 期间（到京-*月*日）
     */
    private String period;

    /**
     * 薪资
     */
    private BigDecimal salary;

    /**
     * yijie费用;学校费用;个人费用(用分号（英文）;隔开)
     */
    private String otherFee;

    /**
     * 费用合计
     */
    private BigDecimal manageFee;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolPracticeId() {
        return schoolPracticeId;
    }

    public void setSchoolPracticeId(int schoolPracticeId) {
        this.schoolPracticeId = schoolPracticeId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}