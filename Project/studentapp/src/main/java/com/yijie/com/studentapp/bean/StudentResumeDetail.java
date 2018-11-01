package com.yijie.com.studentapp.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 学生简历详情
 */
public class StudentResumeDetail implements Serializable{

    /**
     * 奕杰app 学生首页-> 已分配列表中 ->已分配简历详情中 ->匹配详情
     */
    private StudentResumeMatchDetail studentResumeMatchDetail;

    /**
     * 奕杰app 学生首页-> 已分配列表中> 已分配简历详情中 学生已投递园所
     */
    private List<StudentResumeAlreadyDelivery> studentResumeAlreadyDelivery;

    /**
     * 学生个人信息
     */
    private StudentInfo studentInfo;
    /**
     * 学生联系方式
     */
    private StudentContact studentContact;
    /**
     *教育背景
     */
    private List<StudentEducation> studentEducation;
    /**
     * 工作经历
     */
    private List<StudentWorkDue> studentWorkDue;
    /**
     * 简历
     */
    private StudentResume studentResume;
    /**
     * 简历完整度
     */
    private String resumeIntegrity;

    private static final long serialVersionUID = 1L;

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public StudentContact getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(StudentContact studentContact) {
        this.studentContact = studentContact;
    }

    public List<StudentEducation> getStudentEducation() {
        return studentEducation;
    }

    public void setStudentEducation(List<StudentEducation> studentEducation) {
        this.studentEducation = studentEducation;
    }

    public List<StudentWorkDue> getStudentWorkDue() {
        return studentWorkDue;
    }

    public void setStudentWorkDue(List<StudentWorkDue> studentWorkDue) {
        this.studentWorkDue = studentWorkDue;
    }

    public StudentResume getStudentResume() {
        return studentResume;
    }

    public void setStudentResume(StudentResume studentResume) {
        this.studentResume = studentResume;
    }

    public String getResumeIntegrity() {
        return resumeIntegrity;
    }

    public void setResumeIntegrity(String resumeIntegrity) {
        this.resumeIntegrity = resumeIntegrity;
    }

    public StudentResumeMatchDetail getStudentResumeMatchDetail() {
        return studentResumeMatchDetail;
    }

    public List<StudentResumeAlreadyDelivery> getStudentResumeAlreadyDelivery() {
        return studentResumeAlreadyDelivery;
    }

    public void setStudentResumeAlreadyDelivery(List<StudentResumeAlreadyDelivery> studentResumeAlreadyDelivery) {
        this.studentResumeAlreadyDelivery = studentResumeAlreadyDelivery;
    }

    public void setStudentResumeMatchDetail(StudentResumeMatchDetail studentResumeMatchDetail) {
        this.studentResumeMatchDetail = studentResumeMatchDetail;
    }



}
