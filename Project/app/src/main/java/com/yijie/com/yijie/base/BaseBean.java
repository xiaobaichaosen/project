package com.yijie.com.yijie.base;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/5/7.
 */

public class BaseBean implements Serializable{
    private String success;
    private String code;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
