package com.yijie.com.studentapp.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * student_sign_in
 * @author 
 */
public class StudentSignIn implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 园所id
     */
    private Integer kinderId;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 学生签到图片
     */
    private String signinPic;
    private String stuName;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    private String headPic;

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    /**
     * 考勤签到日期
     */
    private String signinDate;

    /**
     * 考勤签到时间
     */
    private String signinTime;
    /**
     * 考勤签到次数
     */
    private Integer signInTimes;

    /**
     * 园所名称
     */
    private String kindName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getKinderId() {
        return kinderId;
    }

    public void setKinderId(Integer kinderId) {
        this.kinderId = kinderId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSigninPic() {
        return signinPic;
    }

    public void setSigninPic(String signinPic) {
        this.signinPic = signinPic;
    }

    public String getSigninDate() {
        return signinDate;
    }

    public void setSigninDate(String signinDate) {
        this.signinDate = signinDate;
    }

    public String getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(String signinTime) {
        this.signinTime = signinTime;
    }

    public Integer getSignInTimes() {
        return signInTimes;
    }

    public void setSignInTimes(Integer signInTimes) {
        this.signInTimes = signInTimes;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }


}