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
    private int studentUserId;

    /**
     * 园所id
     */
    private int kinderNeedId;

    private static final long serialVersionUID = 1L;

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public int getKinderNeedId() {
        return kinderNeedId;
    }

    public void setKinderNeedId(int kinderNeedId) {
        this.kinderNeedId = kinderNeedId;
    }


}