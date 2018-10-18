package com.yijie.com.studentapp.bean;


import java.util.Date;
import java.util.Objects;

/**
 * 奕杰app 学生首页 已分配列表中 简历详情中 学生简历匹配详情
 */
public class StudentResumeMatchDetail {

    /**
     * 匹配园所
     */
     private String matchKinder;
    /**
     * 匹配时间
     */

     private String matchTime;
    /**
     * 同伴
     */
    private String companion;

    public String getMatchKinder() {
        return matchKinder;
    }

    public void setMatchKinder(String matchKinder) {
        this.matchKinder = matchKinder;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }


}
