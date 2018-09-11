package com.yijie.com.yijie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 奕杰平台 on 2018/5/2.
 * 实习薪资
 */

public class IntershipMoney implements Serializable {
    private String startDate;
    private String endDate;
    private String pay;
    private String countMoney;
    private String schoolMoney;
    private String personMoney;

    public String getPersonMoney() {
        return personMoney;
    }

    public void setPersonMoney(String personMoney) {
        this.personMoney = personMoney;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public void setCountMoney(String countMoney) {
        this.countMoney = countMoney;
    }

    public void setSchoolMoney(String schoolMoney) {
        this.schoolMoney = schoolMoney;
    }

    public void setCommonBeans(List<CommonBean> commonBeans) {
        this.commonBeans = commonBeans;
    }

    public void setYijieMoney(String yijieMoney) {
        this.yijieMoney = yijieMoney;
    }

    private List<CommonBean> commonBeans;

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPay() {
        return pay;
    }

    public String getCountMoney() {
        return countMoney;
    }

    public String getSchoolMoney() {
        return schoolMoney;
    }

    public List<CommonBean> getCommonBeans() {
        return commonBeans;
    }

    public String getYijieMoney() {
        return yijieMoney;
    }

    private String yijieMoney;

}
