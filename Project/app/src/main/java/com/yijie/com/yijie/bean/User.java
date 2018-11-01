package com.yijie.com.yijie.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * user
 * @author 
 */
public class User implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String headPic;

    /**
     * 真实名字
     */
    private String realName;

    /**
     * 我的二维码
     */
    private String selfOrCode;

    /**
     * 我的地址
     */
    private String selfAddress;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 添加该用户的用户id
     */
    private int insertUid;

    /**
     * 注册时间
     */
    private Date insertTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除（0：正常；1：已删）
     */
    private Boolean isDel;

    /**
     * 是否在职（0：正常；1，离职）
     */
    private Boolean isJob;

    /**
     * 短信验证码
     */
    private String mcode;

    /**
     * 短信发送时间
     */
    private Date sendTime;

    /**
     * 创建人
     */
    private int createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 部门id
     */
    private int orgid;

    /**
     * cookie
     */
    private String cookieString;


    /**
     * 部门名称
     */
    private String orgname;
    /**
     * 角色名称
     */
    private String roleName;

    private static final long serialVersionUID = 1L;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCookieString() {
        return cookieString;
    }

    public void setCookieString(String cookieString) {
        this.cookieString = cookieString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSelfOrCode() {
        return selfOrCode;
    }

    public void setSelfOrCode(String selfOrCode) {
        this.selfOrCode = selfOrCode;
    }

    public String getSelfAddress() {
        return selfAddress;
    }

    public void setSelfAddress(String selfAddress) {
        this.selfAddress = selfAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInsertUid() {
        return insertUid;
    }

    public void setInsertUid(int insertUid) {
        this.insertUid = insertUid;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Boolean getIsJob() {
        return isJob;
    }

    public void setIsJob(Boolean isJob) {
        this.isJob = isJob;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(headPic, user.headPic) &&
                Objects.equals(realName, user.realName) &&
                Objects.equals(selfOrCode, user.selfOrCode) &&
                Objects.equals(selfAddress, user.selfAddress) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(insertUid, user.insertUid) &&
                Objects.equals(insertTime, user.insertTime) &&
                Objects.equals(updateTime, user.updateTime) &&
                Objects.equals(isDel, user.isDel) &&
                Objects.equals(isJob, user.isJob) &&
                Objects.equals(mcode, user.mcode) &&
                Objects.equals(sendTime, user.sendTime) &&
                Objects.equals(createBy, user.createBy) &&
                Objects.equals(createTime, user.createTime) &&
                Objects.equals(orgid, user.orgid) &&
                Objects.equals(cookieString, user.cookieString) &&
                Objects.equals(orgname, user.orgname) &&
                Objects.equals(roleName, user.roleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, headPic, realName, selfOrCode, selfAddress, mobile, email, password, insertUid, insertTime, updateTime, isDel, isJob, mcode, sendTime, createBy, createTime, orgid, cookieString, orgname, roleName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", headPic='" + headPic + '\'' +
                ", realName='" + realName + '\'' +
                ", selfOrCode='" + selfOrCode + '\'' +
                ", selfAddress='" + selfAddress + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", insertUid=" + insertUid +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                ", isDel=" + isDel +
                ", isJob=" + isJob +
                ", mcode='" + mcode + '\'' +
                ", sendTime=" + sendTime +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", orgid=" + orgid +
                ", cookieString='" + cookieString + '\'' +
                ", orgname='" + orgname + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    //    /**
//     * 主键id
//     */
//    private int id;
//
//    /**
//     * 用户名
//     */
//    private String username;
//
//    /**
//     * 用户头像
//     */
//    private String headPic;
//
//    /**
//     * 真实名字
//     */
//    private String realName;
//
//    /**
//     * 我的二维码
//     */
//    private String selfOrCode;
//
//    /**
//     * 我的地址
//     */
//    private String selfAddress;
//
//    /**
//     * 手机号
//     */
//    private String mobile;
//
//    /**
//     * 邮箱
//     */
//    private String email;
//
//    /**
//     * 密码
//     */
//    private String password;
//
//    /**
//     * 添加该用户的用户id
//     */
//    private int insertUid;
//
//    /**
//     * 注册时间
//     */
//    private Date insertTime;
//
//    /**
//     * 修改时间
//     */
//    private Date updateTime;
//
//    /**
//     * 是否删除（0：正常；1：已删）
//     */
//    private Boolean isDel;
//
//    /**
//     * 是否在职（0：正常；1，离职）
//     */
//    private Boolean isJob;
//
//    /**
//     * 短信验证码
//     */
//    private String mcode;
//
//    /**
//     * 短信发送时间
//     */
//    private Date sendTime;
//
//    /**
//     * 创建人
//     */
//    private int createBy;
//
//    /**
//     * 创建时间
//     */
//    private Date createTime;
//
//    /**
//     * 部门id
//     */
//    private int orgid;
//
//    /**
//     * 部门名称
//     */
//    private String orgname;
//
//    private static final long serialVersionUID = 1L;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getHeadPic() {
//        return headPic;
//    }
//
//    public void setHeadPic(String headPic) {
//        this.headPic = headPic;
//    }
//
//    public String getRealName() {
//        return realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
//    }
//
//    public String getSelfOrCode() {
//        return selfOrCode;
//    }
//
//    public void setSelfOrCode(String selfOrCode) {
//        this.selfOrCode = selfOrCode;
//    }
//
//    public String getSelfAddress() {
//        return selfAddress;
//    }
//
//    public void setSelfAddress(String selfAddress) {
//        this.selfAddress = selfAddress;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getInsertUid() {
//        return insertUid;
//    }
//
//    public void setInsertUid(int insertUid) {
//        this.insertUid = insertUid;
//    }
//
//    public Date getInsertTime() {
//        return insertTime;
//    }
//
//    public void setInsertTime(Date insertTime) {
//        this.insertTime = insertTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public Boolean getIsDel() {
//        return isDel;
//    }
//
//    public void setIsDel(Boolean isDel) {
//        this.isDel = isDel;
//    }
//
//    public Boolean getIsJob() {
//        return isJob;
//    }
//
//    public void setIsJob(Boolean isJob) {
//        this.isJob = isJob;
//    }
//
//    public String getMcode() {
//        return mcode;
//    }
//
//    public void setMcode(String mcode) {
//        this.mcode = mcode;
//    }
//
//    public Date getSendTime() {
//        return sendTime;
//    }
//
//    public void setSendTime(Date sendTime) {
//        this.sendTime = sendTime;
//    }
//
//    public int getCreateBy() {
//        return createBy;
//    }
//
//    public void setCreateBy(int createBy) {
//        this.createBy = createBy;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public int getOrgid() {
//        return orgid;
//    }
//
//    public void setOrgid(int orgid) {
//        this.orgid = orgid;
//    }
//
//    public String getOrgname() {
//        return orgname;
//    }
//
//    public void setOrgname(String orgname) {
//        this.orgname = orgname;
//    }
//
//    @Override
//    public boolean equals(Object that) {
//        if (this == that) {
//            return true;
//        }
//        if (that == null) {
//            return false;
//        }
//        if (getClass() != that.getClass()) {
//            return false;
//        }
//        User other = (User) that;
//        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
//            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
//            && (this.getHeadPic() == null ? other.getHeadPic() == null : this.getHeadPic().equals(other.getHeadPic()))
//            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
//            && (this.getSelfOrCode() == null ? other.getSelfOrCode() == null : this.getSelfOrCode().equals(other.getSelfOrCode()))
//            && (this.getSelfAddress() == null ? other.getSelfAddress() == null : this.getSelfAddress().equals(other.getSelfAddress()))
//            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
//            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
//            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
//            && (this.getInsertUid() == null ? other.getInsertUid() == null : this.getInsertUid().equals(other.getInsertUid()))
//            && (this.getInsertTime() == null ? other.getInsertTime() == null : this.getInsertTime().equals(other.getInsertTime()))
//            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
//            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
//            && (this.getIsJob() == null ? other.getIsJob() == null : this.getIsJob().equals(other.getIsJob()))
//            && (this.getMcode() == null ? other.getMcode() == null : this.getMcode().equals(other.getMcode()))
//            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
//            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
//            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
//            && (this.getOrgid() == null ? other.getOrgid() == null : this.getOrgid().equals(other.getOrgid()))
//            && (this.getOrgname() == null ? other.getOrgname() == null : this.getOrgname().equals(other.getOrgname()));
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
//        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
//        result = prime * result + ((getHeadPic() == null) ? 0 : getHeadPic().hashCode());
//        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
//        result = prime * result + ((getSelfOrCode() == null) ? 0 : getSelfOrCode().hashCode());
//        result = prime * result + ((getSelfAddress() == null) ? 0 : getSelfAddress().hashCode());
//        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
//        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
//        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
//        result = prime * result + ((getInsertUid() == null) ? 0 : getInsertUid().hashCode());
//        result = prime * result + ((getInsertTime() == null) ? 0 : getInsertTime().hashCode());
//        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
//        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
//        result = prime * result + ((getIsJob() == null) ? 0 : getIsJob().hashCode());
//        result = prime * result + ((getMcode() == null) ? 0 : getMcode().hashCode());
//        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
//        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
//        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
//        result = prime * result + ((getOrgid() == null) ? 0 : getOrgid().hashCode());
//        result = prime * result + ((getOrgname() == null) ? 0 : getOrgname().hashCode());
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getSimpleName());
//        sb.append(" [");
//        sb.append("Hash = ").append(hashCode());
//        sb.append(", id=").append(id);
//        sb.append(", username=").append(username);
//        sb.append(", headPic=").append(headPic);
//        sb.append(", realName=").append(realName);
//        sb.append(", selfOrCode=").append(selfOrCode);
//        sb.append(", selfAddress=").append(selfAddress);
//        sb.append(", mobile=").append(mobile);
//        sb.append(", email=").append(email);
//        sb.append(", password=").append(password);
//        sb.append(", insertUid=").append(insertUid);
//        sb.append(", insertTime=").append(insertTime);
//        sb.append(", updateTime=").append(updateTime);
//        sb.append(", isDel=").append(isDel);
//        sb.append(", isJob=").append(isJob);
//        sb.append(", mcode=").append(mcode);
//        sb.append(", sendTime=").append(sendTime);
//        sb.append(", createBy=").append(createBy);
//        sb.append(", createTime=").append(createTime);
//        sb.append(", orgid=").append(orgid);
//        sb.append(", orgname=").append(orgname);
//        sb.append(", serialVersionUID=").append(serialVersionUID);
//        sb.append("]");
//        return sb.toString();
//    }
}