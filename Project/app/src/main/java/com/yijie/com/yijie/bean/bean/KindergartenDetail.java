package com.yijie.com.yijie.bean.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * kindergarten_detail
 * @author 
 */
public class KindergartenDetail implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 园所全称
     */
    private String kindName;

    /**
     * 园所联系人
     */
    private String kindContact;

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信号
     */
    private String wechart;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 园所地址
     */
    private String kindAddress;

    /**
     * 占地面积
     */
    private String area;

    /**
     * 园所类别
     */
    private String kindType;

    /**
     * 园所级别
     */
    private String kindLevel;

    /**
     * 幼儿人数
     */
    private Integer childrenNum;

    /**
     * 班级数量
     */
    private Integer classNum;

    /**
     * 班级配置
     */
    private String classSet;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 特色课程
     */
    private String featureCourse;

    /**
     * 住宿安排
     */
    private String stay;

    /**
     * 三餐安排
     */
    private String eat;

    /**
     * 工服押金
     */
    private BigDecimal clothesDeposit;

    /**
     * 首年体检费
     */
    private BigDecimal firstTestFee;

    /**
     * 荣誉证书
     */
    private String certificate;

    /**
     * 营业执照
     */
    private String businessLicence;

    /**
     * 园所照片
     */
    private String environment;

    /**
     * 宿舍环境
     */
    private String attachment;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 园所
     */
    private String gardenPic;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 学校简介
     */
    private String summary;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechart() {
        return wechart;
    }

    public void setWechart(String wechart) {
        this.wechart = wechart;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getKindAddress() {
        return kindAddress;
    }

    public void setKindAddress(String kindAddress) {
        this.kindAddress = kindAddress;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKindType() {
        return kindType;
    }

    public void setKindType(String kindType) {
        this.kindType = kindType;
    }

    public String getKindLevel() {
        return kindLevel;
    }

    public void setKindLevel(String kindLevel) {
        this.kindLevel = kindLevel;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public String getClassSet() {
        return classSet;
    }

    public void setClassSet(String classSet) {
        this.classSet = classSet;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFeatureCourse() {
        return featureCourse;
    }

    public void setFeatureCourse(String featureCourse) {
        this.featureCourse = featureCourse;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }

    public String getEat() {
        return eat;
    }

    public void setEat(String eat) {
        this.eat = eat;
    }

    public BigDecimal getClothesDeposit() {
        return clothesDeposit;
    }

    public void setClothesDeposit(BigDecimal clothesDeposit) {
        this.clothesDeposit = clothesDeposit;
    }

    public BigDecimal getFirstTestFee() {
        return firstTestFee;
    }

    public void setFirstTestFee(BigDecimal firstTestFee) {
        this.firstTestFee = firstTestFee;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getGardenPic() {
        return gardenPic;
    }

    public void setGardenPic(String gardenPic) {
        this.gardenPic = gardenPic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
