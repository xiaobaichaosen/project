package com.yijie.com.yijie.bean.school;

import java.io.Serializable;
import java.util.List;

/**
 * 学校详情
 */
public class School implements Serializable{

    private SchoolMain schoolMain;

    private List<SchoolContact> schoolContact;

    private List<SchoolMemoInfo> schoolMemoInfo;

    private List<SchoolPractice> schoolPractice;

    private List<SchoolSalaryInfo> schoolSalaryInfo;

    public List<SchoolContact> getSchoolContact() {
        return schoolContact;
    }

    public void setSchoolContact(List<SchoolContact> schoolContact) {
        this.schoolContact = schoolContact;
    }

    public List<SchoolMemoInfo> getSchoolMemoInfo() {
        return schoolMemoInfo;
    }

    public void setSchoolMemoInfo(List<SchoolMemoInfo> schoolMemoInfo) {
        this.schoolMemoInfo = schoolMemoInfo;
    }



    public SchoolMain getSchoolMain() {
        return schoolMain;
    }

    public void setSchoolMain(SchoolMain schoolMain) {
        this.schoolMain = schoolMain;
    }

    public List<SchoolPractice> getSchoolPractice() {
        return schoolPractice;
    }

    public void setSchoolPractice(List<SchoolPractice> schoolPractice) {
        this.schoolPractice = schoolPractice;
    }

    public List<SchoolSalaryInfo> getSchoolSalaryInfo() {
        return schoolSalaryInfo;
    }

    public void setSchoolSalaryInfo(List<SchoolSalaryInfo> schoolSalaryInfo) {
        this.schoolSalaryInfo = schoolSalaryInfo;
    }
}
