package com.yijie.com.yijie.bean;

import com.yijie.com.yijie.base.BaseBean;
import com.yijie.com.yijie.bean.school.SchoolContact;

import java.util.List;

/**
 * Created by 奕杰平台 on 2018/5/7.
 */

public class ContactReceiveBean extends BaseBean {
    private List<SchoolContact> schoolContacts;

    public List<SchoolContact> getSchoolContacts() {
        return schoolContacts;
    }

    public void setSchoolContacts(List<SchoolContact> schoolContacts) {
        this.schoolContacts = schoolContacts;
    }
}
