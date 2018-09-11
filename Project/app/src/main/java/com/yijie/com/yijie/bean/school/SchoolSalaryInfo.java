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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchoolPracticeId() {
        return schoolPracticeId;
    }

    public void setSchoolPracticeId(Integer schoolPracticeId) {
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SchoolSalaryInfo other = (SchoolSalaryInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSchoolPracticeId() == null ? other.getSchoolPracticeId() == null : this.getSchoolPracticeId().equals(other.getSchoolPracticeId()))
            && (this.getPeriod() == null ? other.getPeriod() == null : this.getPeriod().equals(other.getPeriod()))
            && (this.getSalary() == null ? other.getSalary() == null : this.getSalary().equals(other.getSalary()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getManageFee() == null ? other.getManageFee() == null : this.getManageFee().equals(other.getManageFee()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSchoolPracticeId() == null) ? 0 : getSchoolPracticeId().hashCode());
        result = prime * result + ((getPeriod() == null) ? 0 : getPeriod().hashCode());
        result = prime * result + ((getSalary() == null) ? 0 : getSalary().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getManageFee() == null) ? 0 : getManageFee().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", schoolPracticeId=").append(schoolPracticeId);
        sb.append(", period=").append(period);
        sb.append(", salary=").append(salary);
        sb.append(", otherFee=").append(otherFee);
        sb.append(", manageFee=").append(manageFee);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}