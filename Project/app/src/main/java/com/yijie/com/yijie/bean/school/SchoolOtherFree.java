package com.yijie.com.yijie.bean.school;

import com.yijie.com.yijie.bean.CommonBean;

import java.util.List;

/**
 * Created by 奕杰平台 on 2018/5/7.
 * 包含学校金额，奕杰金额，多个个人费用
 */

public class SchoolOtherFree {
    private String schoolFree;

    public String getSchoolFree() {
        return schoolFree;
    }

    public void setSchoolFree(String schoolFree) {
        this.schoolFree = schoolFree;
    }

    public List<CommonBean> getCommonBeans() {
        return commonBeans;
    }

    public void setCommonBeans(List<CommonBean> commonBeans) {
        this.commonBeans = commonBeans;
    }

    @Override
    public String toString() {
        return "SchoolOtherFree{" +
                "schoolFree='" + schoolFree + '\'' +
                ", commonBeans=" + commonBeans +
                ", yijieFree='" + yijieFree + '\'' +
                '}';
    }

    public String getYijieFree() {
        return yijieFree;
    }

    public void setYijieFree(String yijieFree) {
        this.yijieFree = yijieFree;
    }

    private List<CommonBean> commonBeans;
    private String yijieFree;
}
