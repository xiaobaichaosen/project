package com.yijie.com.studentapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 学生简历
 * @author 
 */
public class StudentResume  implements Serializable  {
    /**
     * 主键id
     */
    private Integer id;

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
    private Integer companionId;

    /**
     * 学生个人信息id
     */
    private Integer studentInfoId;

    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 审核人id（培训老师id)
     */
    private Integer auditorId;

    /**
     * 期望工作地点
     */
    private String expectWorkPlace;
    /**
     * 园所名称
     */
    private String kindName;

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
    private Integer status;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 更新时间
     */

    private String updateTime;

    /**
     * 简历投递次数
     */
    private Integer resumeCount;

    /**
     * 奕杰app 学生简历待审核简历统计数量
     */
    private Integer countResumeStayExamine;

    /**
     * 奕杰app 学生首页 学生简历待分配数量
     */
    private Integer countResumeStayAllotNum;

    /**
     * 奕杰app 学生首页 学生简历审核未通过数量
     */
    private Integer countResumeNotPassNum;
    /**
     * 奕杰app 学生首页 学生简历已分配数量
     */
    private Integer countResumeAlreadyAllotNum;

    /**
     * 简历退回原因
     */
    private String rejectReason;

    /**
     * 自我评价
     */
    private String selfEvaluate;
    /**
     * 爱好特长
     */
    private String hobby;

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
    /**
     * 未统计简历数量
     */
    private Integer unResumeCountNum;






    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCompanionId() {
        return companionId;
    }

    public void setCompanionId(Integer companionId) {
        this.companionId = companionId;
    }

    public Integer getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(Integer studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getResumeCount() {
        return resumeCount;
    }

    public void setResumeCount(Integer resumeCount) {
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

    public Integer getUnResumeCountNum() {
        return unResumeCountNum;
    }

    public void setUnResumeCountNum(Integer unResumeCountNum) {
        this.unResumeCountNum = unResumeCountNum;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public Integer getCountResumeStayExamine() {
        return countResumeStayExamine;
    }

    public void setCountResumeStayExamine(Integer countResumeStayExamine) {
        this.countResumeStayExamine = countResumeStayExamine;
    }

    public Integer getCountResumeStayAllotNum() {
        return countResumeStayAllotNum;
    }

    public void setCountResumeStayAllotNum(Integer countResumeStayAllotNum) {
        this.countResumeStayAllotNum = countResumeStayAllotNum;
    }

    public Integer getCountResumeNotPassNum() {
        return countResumeNotPassNum;
    }

    public void setCountResumeNotPassNum(Integer countResumeNotPassNum) {
        this.countResumeNotPassNum = countResumeNotPassNum;
    }

    public Integer getCountResumeAlreadyAllotNum() {
        return countResumeAlreadyAllotNum;
    }

    public void setCountResumeAlreadyAllotNum(Integer countResumeAlreadyAllotNum) {
        this.countResumeAlreadyAllotNum = countResumeAlreadyAllotNum;
    }



}