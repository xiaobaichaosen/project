package com.yijie.com.studentapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 学生用户和园所需求类（中间表po类）
 * @author 
 */
public class StudentuserKinderneed  implements Serializable {


    /**
     * 园所id
     */
    private Integer kinderId;
    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 简历状态(0：默认 学生投简历 待选 【//（不用 1：老师推荐的 待选）】 2：园所同意接收简历（已选)（学生app：我的投递：已接受），3：园所放弃（学生app：我的投递：不合适），4：简历自动退回 5.实习结束（历史学生)
     6：学生app:我的投递：被查看
     */
    private Integer status;
    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 园所id
     */
    private Integer kinderNeedId;
    /**
     * 园所用户id
     */
    private Integer kindUserId;
    /**
     * 学生头像
     */
    private String headPic;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 学生年龄
     */
    private Integer stuAge;
    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Double weight;
    /**
     * 学生特长
     */
    private String hobby;
    /**
     * 园所需求人数
     */
    private Integer studentNum;

    /**
     * 学校实习项目名称
     */
    private String projectName;
    /**
     * 学校名称
     */
    private String schoolName;


    /**
     * 园所地址
     */
    private String kindAddress;

    /**
     * 园所详细地址
     */
    private String kindDetailAddress;

    /**
     * 园所简介
     */
    private String summary;

    /**
     * 园所名称
     */
    private String kindName;

    /**
     * 园所薪资设定
     */
    private String kinderSalarySet;
    /**
     * 学生app：对园所整体评价
     */
    private String totalEvaluate;

    /**
     * 奕杰app 园所需求详情页里页面更新时间(园所需求表里的更新时间)
     */
    private String updateTime;
    /**
     * 学生报名创建时间
     */
    private String createTime;
    /**
     * 学生简历投递后园所剩余简历筛选的时间
     */
    private String resumeSurplusTime;

    private static final long serialVersionUID = 1L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResumeSurplusTime() {
        return resumeSurplusTime;
    }

    public void setResumeSurplusTime(String resumeSurplusTime) {
        this.resumeSurplusTime = resumeSurplusTime;
    }

    public String getKindAddress() {
        return kindAddress;
    }

    public void setKindAddress(String kindAddress) {
        this.kindAddress = kindAddress;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getKinderSalarySet() {
        return kinderSalarySet;
    }

    public void setKinderSalarySet(String kinderSalarySet) {
        this.kinderSalarySet = kinderSalarySet;
    }

    public Integer getKinderId() {
        return kinderId;
    }

    public void setKinderId(Integer kinderId) {
        this.kinderId = kinderId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }


    public String getTotalEvaluate() {
        return totalEvaluate;
    }

    public void setTotalEvaluate(String totalEvaluate) {
        this.totalEvaluate = totalEvaluate;
    }


}