package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;

/**
 * studentuser_kinderneed
 * @author 
 */
public class StudentuserKinderneedKey implements Serializable {
    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 园所id
     */
    private Integer kinderNeedId;

    private static final long serialVersionUID = 1L;

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getKinderNeedId() {
        return kinderNeedId;
    }

    public void setKinderNeedId(Integer kinderNeedId) {
        this.kinderNeedId = kinderNeedId;
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
        StudentuserKinderneedKey other = (StudentuserKinderneedKey) that;
        return (this.getStudentUserId() == null ? other.getStudentUserId() == null : this.getStudentUserId().equals(other.getStudentUserId()))
            && (this.getKinderNeedId() == null ? other.getKinderNeedId() == null : this.getKinderNeedId().equals(other.getKinderNeedId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentUserId() == null) ? 0 : getStudentUserId().hashCode());
        result = prime * result + ((getKinderNeedId() == null) ? 0 : getKinderNeedId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentUserId=").append(studentUserId);
        sb.append(", kinderNeedId=").append(kinderNeedId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}