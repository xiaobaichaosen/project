package com.yijie.com.kindergartenapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * student_resume
 * @author 
 */
public class StudentResume implements Serializable {
    /**
     * 主键id
     */
    private int id;

    /**
     * 微信账号id
     */
    private String openId;

    /**
     * 表单id(点击发送审核产生的id)
     */
    private String formId;

    /**
     * 期望同伴id
     */
    private int companionId;

    /**
     * 学生个人信息id
     */
    private int studentInfoId;

    /**
     * 学生用户id
     */
    private int studentUserId;

    /**
     * 审核人id（培训老师id)
     */
    private int auditorId;

    /**
     * 期望工作地点
     */
    private String expectWorkPlace;

    /**
     * 期望同伴
     */
    private String expectPartener;

    /**
     * 荣誉证书
     */
    private String certificate;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 简历状态（0：待审核：通过1（待分配），2：未通过，3已分配）
     */
    private int status;

    /**
     * 创建人
     */
    private int createBy;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 简历投递次数
     */
    private int resumeCount;

    /**
     * 简历退回原因
     */
    private String rejectReason;

    /**
     * 自我评价
     */
    private String selfEvaluate;

    /**
     * 学生头像
     */
    private String headPic;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 学生手机号
     */
    private String cellphone;
    /**
     * 学校名称
     */
    private String name;

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * 未统计简历数量
     */

    private int unResumeCountNum;
    /**
     * 爱好特长
     */
    private String hobby;

    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getCompanionId() {
        return companionId;
    }

    public void setCompanionId(int companionId) {
        this.companionId = companionId;
    }

    public int getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(int studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public int getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(int auditorId) {
        this.auditorId = auditorId;
    }

    public String getExpectWorkPlace() {
        return expectWorkPlace;
    }

    public void setExpectWorkPlace(String expectWorkPlace) {
        this.expectWorkPlace = expectWorkPlace;
    }

    public String getExpectPartener() {
        return expectPartener;
    }

    public void setExpectPartener(String expectPartener) {
        this.expectPartener = expectPartener;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getResumeCount() {
        return resumeCount;
    }

    public void setResumeCount(int resumeCount) {
        this.resumeCount = resumeCount;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getSelfEvaluate() {
        return selfEvaluate;
    }

    public void setSelfEvaluate(String selfEvaluate) {
        this.selfEvaluate = selfEvaluate;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnResumeCountNum() {
        return unResumeCountNum;
    }

    public void setUnResumeCountNum(int unResumeCountNum) {
        this.unResumeCountNum = unResumeCountNum;
    }


}