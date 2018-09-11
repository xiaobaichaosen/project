package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 学生简历详情
 */
public class StudentResumeDetail implements Serializable{
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




}
