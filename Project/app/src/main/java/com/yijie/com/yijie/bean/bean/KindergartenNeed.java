package com.yijie.com.yijie.bean.bean;


import java.io.Serializable;

/**
 * 园所发布需求及招聘发布详情
 *
 * @author
 */
public class KindergartenNeed implements Serializable {




    /**
     * 主键
     */
    private int id;

    /**
     * 园所id
     */
    private int kinderId;
    /**
     * 园所用户id
     */
    private int kindUserId;
    /**
     * 学校id
     */
    private int schoolId;
    /**
     * 实习项目
     */
    private int schoolPracticeId;

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
    private int studentNum;
    /**
     * 园所接收人数
     */
    private int receiveNum;

    /**
     * 统计学生报名人数
     */
    private int countRecruit;
    /**
     * 统计园所接收人数
     */
    private int countReceive;
    /**
     * 统计园所剩余学生名额
     */
    private int countSurplus;
    /**
     * 园所收到简历
     */
    private int receiveResume;
    /**
     * 园所需求人数
     */
    private int demandNum;
    /**
     * 园所需求总和
     */
    private int demandTotal;
    /**
     * 园所报名总数
     */
    private int enrollTotal;

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
    private int isRead;

    /**
     * 创建时间
     */

    private String createTime;

    /**
     * 更新时间
     */

    private String upStringTime;
    /**
     * 奕杰app 园所主页园所需求中已报名人数统计
     */
    private int count;
    /**
     * 奕杰app 园所主页园所需求中已接受人数统计
     */
    private int count2;
    /**
     * 奕杰app 园所主页园所需求中已放弃人数统计
     */
    private int count3;
    /**
     * 奕杰app中园所头像
     */
    private String headPic;
    /**
     * 园所需求id studentuser_kinderneed中间表中的
     */
    private int kindNeedId;


    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKinderId() {
        return kinderId;
    }

    public void setKinderId(int kinderId) {
        this.kinderId = kinderId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getSchoolPracticeId() {
        return schoolPracticeId;
    }

    public void setSchoolPracticeId(int schoolPracticeId) {
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

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(int receiveNum) {
        this.receiveNum = receiveNum;
    }

    public int getCountRecruit() {
        return countRecruit;
    }

    public int getKindUserId() {
        return kindUserId;
    }

    public void setKindUserId(int kindUserId) {
        this.kindUserId = kindUserId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public int getCount3() {
        return count3;
    }

    public void setCount3(int count3) {
        this.count3 = count3;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getKindNeedId() {
        return kindNeedId;
    }

    public void setKindNeedId(int kindNeedId) {
        this.kindNeedId = kindNeedId;
    }

    public void setCountRecruit(int countRecruit) {

        this.countRecruit = countRecruit;
    }

    public int getCountReceive() {
        return countReceive;
    }

    public void setCountReceive(int countReceive) {
        this.countReceive = countReceive;
    }

    public int getCountSurplus() {
        return countSurplus;
    }

    public void setCountSurplus(int countSurplus) {
        this.countSurplus = countSurplus;
    }

    public int getReceiveResume() {
        return receiveResume;
    }

    public void setReceiveResume(int receiveResume) {
        this.receiveResume = receiveResume;
    }

    public int getDemandNum() {
        return demandNum;
    }

    public void setDemandNum(int demandNum) {
        this.demandNum = demandNum;
    }

    public int getDemandTotal() {
        return demandTotal;
    }

    public void setDemandTotal(int demandTotal) {
        this.demandTotal = demandTotal;
    }


    public int getEnrollTotal() {
        return enrollTotal;
    }

    public void setEnrollTotal(int enrollTotal) {
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

    public String getUpStringTime() {
        return upStringTime;
    }

    public void setUpStringTime(String upStringTime) {
        this.upStringTime = upStringTime;
    }



}