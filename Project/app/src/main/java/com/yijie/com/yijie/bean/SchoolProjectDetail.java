package com.yijie.com.yijie.bean;

import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.bean.school.SchoolTrainDetail;

import java.util.Objects;

/**
 * 学校项目详情
 */
public class SchoolProjectDetail {

    /**
     * 实习详情
     */
    private SchoolPractice schoolPractice;
    /**
     * 实习薪资
     */
    private SchoolSalaryInfo schoolSalaryInfo;
    /**
     * 院校联络人及培训详情
     */
    private SchoolTrainDetail schoolTrainDetail;

    public SchoolPractice getSchoolPractice() {
        return schoolPractice;
    }

    public void setSchoolPractice(SchoolPractice schoolPractice) {
        this.schoolPractice = schoolPractice;
    }

    public SchoolSalaryInfo getSchoolSalaryInfo() {
        return schoolSalaryInfo;
    }

    public void setSchoolSalaryInfo(SchoolSalaryInfo schoolSalaryInfo) {
        this.schoolSalaryInfo = schoolSalaryInfo;
    }

    public SchoolTrainDetail getSchoolTrainDetail() {
        return schoolTrainDetail;
    }

    public void setSchoolTrainDetail(SchoolTrainDetail schoolTrainDetail) {
        this.schoolTrainDetail = schoolTrainDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolProjectDetail that = (SchoolProjectDetail) o;
        return Objects.equals(schoolPractice, that.schoolPractice) &&
                Objects.equals(schoolSalaryInfo, that.schoolSalaryInfo) &&
                Objects.equals(schoolTrainDetail, that.schoolTrainDetail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(schoolPractice, schoolSalaryInfo, schoolTrainDetail);
    }

    @Override
    public String toString() {
        return "SchoolProjectDetail{" +
                "schoolPractice=" + schoolPractice +
                ", schoolSalaryInfo=" + schoolSalaryInfo +
                ", schoolTrainDetail=" + schoolTrainDetail +
                '}';
    }
}
