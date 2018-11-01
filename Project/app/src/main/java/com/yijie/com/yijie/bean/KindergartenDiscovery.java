package com.yijie.com.yijie.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * kindergarten_discovery
 * @author 
 */
public class KindergartenDiscovery implements Serializable {


    /**
     * 主键
     */
    private Integer id;

    /**
     * 园所id
     */
    private Integer kinderId;

    /**
     * 园所发现类型：1：招聘发布 2：接受简历 3：放弃简历
     */
    private Integer discoveryType;

    /**
     * 发现图片
     */
    private String discoveryPic;

    /**
     * 发现标题
     */
    private String discoveryTitle;

    /**
     * 发现内容
     */
    private String discoveryContent;

    /**
     * 推送数量
     */
    private int pushCount;

    /**
     * 未读数量
     */
    private int unreadCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 园所需求id
     */
    private int kinderNeedId;
    /**
     * 实习项目名称
     */
    private String projectName;
    /**
     * 学校城市
     */
    private String location;

    /**
     * 学生人数要求(招聘人数）
     */
    private int studentNum;
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

    public int getDiscoveryType() {
        return discoveryType;
    }

    public void setDiscoveryType(int discoveryType) {
        this.discoveryType = discoveryType;
    }

    public String getDiscoveryPic() {
        return discoveryPic;
    }

    public void setDiscoveryPic(String discoveryPic) {
        this.discoveryPic = discoveryPic;
    }

    public String getDiscoveryTitle() {
        return discoveryTitle;
    }

    public void setDiscoveryTitle(String discoveryTitle) {
        this.discoveryTitle = discoveryTitle;
    }

    public String getDiscoveryContent() {
        return discoveryContent;
    }

    public void setDiscoveryContent(String discoveryContent) {
        this.discoveryContent = discoveryContent;
    }

    public int getPushCount() {
        return pushCount;
    }

    public void setPushCount(int pushCount) {
        this.pushCount = pushCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getKinderNeedId() {
        return kinderNeedId;
    }

    public void setKinderNeedId(int kinderNeedId) {
        this.kinderNeedId = kinderNeedId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindergartenDiscovery that = (KindergartenDiscovery) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(kinderId, that.kinderId) &&
                Objects.equals(discoveryType, that.discoveryType) &&
                Objects.equals(discoveryPic, that.discoveryPic) &&
                Objects.equals(discoveryTitle, that.discoveryTitle) &&
                Objects.equals(discoveryContent, that.discoveryContent) &&
                Objects.equals(pushCount, that.pushCount) &&
                Objects.equals(unreadCount, that.unreadCount) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(kinderNeedId, that.kinderNeedId) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(location, that.location) &&
                Objects.equals(studentNum, that.studentNum) &&
                Objects.equals(dance, that.dance) &&
                Objects.equals(arts, that.arts) &&
                Objects.equals(english, that.english) &&
                Objects.equals(skidding, that.skidding) &&
                Objects.equals(piano, that.piano) &&
                Objects.equals(electronicOrgan, that.electronicOrgan) &&
                Objects.equals(other, that.other);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, kinderId, discoveryType, discoveryPic, discoveryTitle, discoveryContent, pushCount, unreadCount, createTime, updateTime, kinderNeedId, projectName, location, studentNum, dance, arts, english, skidding, piano, electronicOrgan, other);
    }

    @Override
    public String toString() {
        return "KindergartenDiscovery{" +
                "id=" + id +
                ", kinderId=" + kinderId +
                ", discoveryType=" + discoveryType +
                ", discoveryPic='" + discoveryPic + '\'' +
                ", discoveryTitle='" + discoveryTitle + '\'' +
                ", discoveryContent='" + discoveryContent + '\'' +
                ", pushCount=" + pushCount +
                ", unreadCount=" + unreadCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", kinderNeedId=" + kinderNeedId +
                ", projectName='" + projectName + '\'' +
                ", location='" + location + '\'' +
                ", studentNum=" + studentNum +
                ", dance='" + dance + '\'' +
                ", arts='" + arts + '\'' +
                ", english='" + english + '\'' +
                ", skidding='" + skidding + '\'' +
                ", piano='" + piano + '\'' +
                ", electronicOrgan='" + electronicOrgan + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}