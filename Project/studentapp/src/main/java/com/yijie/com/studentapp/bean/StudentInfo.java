package com.yijie.com.studentapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    //学号
    private  String stuNumber;

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    /**
     * 简历id
     */
    private int resumeId;

    /**
     * 姓名
     */
    private String stuName;
    /**
     * 学生用户头像
     */
    private String headPic;

    /**
     * 学生年龄
     */
    private int stuAge;
    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身高
     */
    private int height;

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
    private int createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
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

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
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

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getStuAge() {
        return stuAge;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }


}