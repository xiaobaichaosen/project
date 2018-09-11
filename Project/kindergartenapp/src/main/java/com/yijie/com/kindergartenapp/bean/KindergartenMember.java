package com.yijie.com.kindergartenapp.bean;

import java.io.Serializable;
import java.util.Objects;

public class KindergartenMember implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 园所id
     */
    private Integer kinderId;

    /**
     * 园所成员手机号
     */
    private String cellphone;

    /**
     * 园所成员密码
     */
    private String password;

    /**
     * 园所成员密码确认
     */
    private String isPassword;

    /**
     * 短信验证码
     *
     */
    private String verifyCode;

    /**
     * 园所名字
     */
    private String kindName;

    /**
     * 成员名字
     */
    private String memerName;

    /**
     * 园所成员审核状态:0:审核未通过 1：审核已通过
     */
    private Integer auditStatus;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemerName() {
        return memerName;
    }

    public void setMemerName(String memerName) {
        this.memerName = memerName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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

    public String getIsPassword() {
        return isPassword;
    }

    public void setIsPassword(String isPassword) {
        this.isPassword = isPassword;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }




}
