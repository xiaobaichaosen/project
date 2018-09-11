package com.yijie.com.kindergartenapp.bean;


import java.io.Serializable;
import java.util.Objects;

/**
 * 园所发布需求及招聘发布详情
 *
 * @author
 */
public class KindergartenNeed implements Serializable {


    /**
     * 主键
     */
    private Integer id;

    /**
     * 园所id
     */
    private Integer kinderId;
    /**
     * 园所用户id
     */
    private Integer kindUserId;
    /**
     * 学校id
     */
    private Integer schoolId;
    /**
     * 实习项目
     */
    private Integer schoolPracticeId;


    /**
     * 实习薪资id
     */
    private Integer schoolSalaryInfoId;

    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    /**
     * 合计总额
     */
    private String amout;
    /**
     * 首次支付费月数
     */
    private Integer firstPayMonth;

    /**
     * 园所薪资设定
     */
    private String kinderSalarySet;

    /**
     * 实习类型
     */
    private String practiceType;

    /**
     * 学历
     */
    private String education;

    /**
     * 到京时间
     */
    private String toBeijingTime;

    /**
     * 管理模式
     */
    private String manageModel;
    /**
     * 薪资
     */
    private String salary;
    /**
     * 管理费用
     */
    private String manageFee;


    /**
     * 园所匹配状态 0：默认未匹配成功 1：匹配成功
     */
    private Integer status;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校实习项目
     */
    private String projectName;
    /**
     * 园所联系人名字
     */
    private String kindContact;
    /**
     * 园所联系人手机号
     */
    private String cellphone;
    /**
     * 学校地址
     */
    private String location;


    /**
     * 学生人数要求(招聘人数）
     */
    private Integer studentNum;
    /**
     * 园所接收人数
     */
    private Integer receiveNum;

    /**
     * 统计学生报名人数
     */
    private Integer countRecruit;
    /**
     * 统计园所接收人数
     */
    private Integer countReceive;
    /**
     * 统计园所剩余学生名额
     */
    private Integer countSurplus;
    /**
     * 园所收到简历
     */
    private Integer receiveResume;
    /**
     * 园所需求人数
     */
    private Integer demandNum;
    /**
     * 园所需求总和
     */
    private Integer demandTotal;
    /**
     * 园所报名总数
     */
    private Integer enrollTotal;

    /**
     * 园所名称
     */
    private String kindName;
    /**
     * 舞蹈
     */
    private String dance;

    /**
     * 美术
     */
    private String arts;

    /**
     * 英语
     */
    private String english;

    /**
     * 轮滑
     */
    private String skidding;

    /**
     * 钢琴
     */
    private String piano;

    /**
     * 电子琴
     */
    private String electronicOrgan;

    /**
     * 其它
     */
    private String other;
    /**
     * 园所发布需求是否读：0：默认未读 1：已读
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */

    private String updateTime;
    /**
     * 奕杰app 园所主页园所需求中已报名人数统计
     */
    private Integer count;
    /**
     * 奕杰app 园所主页园所需求中已接受人数统计
     */
    private Integer count2;
    /**
     * 奕杰app 园所主页园所需求中已放弃人数统计
     */
    private Integer count3;
    /**
     * 奕杰app中园所头像
     */
    private String headPic;
    /**
     * 园所需求id studentuser_kinderneed中间表中的
     */
    private Integer kindNeedId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSchoolPracticeId() {
        return schoolPracticeId;
    }

    public void setSchoolPracticeId(Integer schoolPracticeId) {
        this.schoolPracticeId = schoolPracticeId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getKindContact() {
        return kindContact;
    }

    public void setKindContact(String kindContact) {
        this.kindContact = kindContact;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public Integer getCountRecruit() {
        return countRecruit;
    }

    public void setCountRecruit(Integer countRecruit) {
        this.countRecruit = countRecruit;
    }

    public Integer getCountReceive() {
        return countReceive;
    }

    public void setCountReceive(Integer countReceive) {
        this.countReceive = countReceive;
    }

    public Integer getCountSurplus() {
        return countSurplus;
    }

    public void setCountSurplus(Integer countSurplus) {
        this.countSurplus = countSurplus;
    }

    public Integer getReceiveResume() {
        return receiveResume;
    }

    public void setReceiveResume(Integer receiveResume) {
        this.receiveResume = receiveResume;
    }

    public Integer getDemandNum() {
        return demandNum;
    }

    public void setDemandNum(Integer demandNum) {
        this.demandNum = demandNum;
    }

    public Integer getDemandTotal() {
        return demandTotal;
    }

    public void setDemandTotal(Integer demandTotal) {
        this.demandTotal = demandTotal;
    }


    public Integer getEnrollTotal() {
        return enrollTotal;
    }

    public void setEnrollTotal(Integer enrollTotal) {
        this.enrollTotal = enrollTotal;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getDance() {
        return dance;
    }

    public void setDance(String dance) {
        this.dance = dance;
    }

    public String getArts() {
        return arts;
    }

    public void setArts(String arts) {
        this.arts = arts;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getSkidding() {
        return skidding;
    }

    public void setSkidding(String skidding) {
        this.skidding = skidding;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public String getElectronicOrgan() {
        return electronicOrgan;
    }

    public void setElectronicOrgan(String electronicOrgan) {
        this.electronicOrgan = electronicOrgan;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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

    public Integer getKindUserId() {
        return kindUserId;
    }

    public void setKindUserId(Integer kindUserId) {
        this.kindUserId = kindUserId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount2() {
        return count2;
    }

    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    public Integer getCount3() {
        return count3;
    }

    public void setCount3(Integer count3) {
        this.count3 = count3;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getKindNeedId() {
        return kindNeedId;
    }

    public void setKindNeedId(Integer kindNeedId) {
        this.kindNeedId = kindNeedId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getSchoolSalaryInfoId() {
        return schoolSalaryInfoId;
    }

    public void setSchoolSalaryInfoId(Integer schoolSalaryInfoId) {
        this.schoolSalaryInfoId = schoolSalaryInfoId;
    }


    public Integer getFirstPayMonth() {
        return firstPayMonth;
    }

    public void setFirstPayMonth(Integer firstPayMonth) {
        this.firstPayMonth = firstPayMonth;
    }


    public String getKinderSalarySet() {
        return kinderSalarySet;
    }

    public void setKinderSalarySet(String kinderSalarySet) {
        this.kinderSalarySet = kinderSalarySet;
    }



    public String getPracticeType() {
        return practiceType;
    }

    public void setPracticeType(String practiceType) {
        this.practiceType = practiceType;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getToBeijingTime() {
        return toBeijingTime;
    }

    public void setToBeijingTime(String toBeijingTime) {
        this.toBeijingTime = toBeijingTime;
    }

    public String getManageModel() {
        return manageModel;
    }

    public void setManageModel(String manageModel) {
        this.manageModel = manageModel;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }




}