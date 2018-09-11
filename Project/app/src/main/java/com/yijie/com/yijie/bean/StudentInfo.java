package com.yijie.com.yijie.bean;


import java.io.Serializable;
import java.util.Date;

/**
 * 学生个人信息-wechat
 * @author 
 */
public class StudentInfo implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 简历id
     */
    private Integer resumeId;

    /**
     * 姓名
     */
    private String stuName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    private String place;

    /**
     * 出生日期
     */

    private String birth;

    /**
     * 个人图片
     */
    private String pic;

    /**
     * 户口所在地
     */
    private String address;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 身份证
     */
    private String idCard;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

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

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
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

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }


}