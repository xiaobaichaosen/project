package com.yijie.com.yijie.activity.login.modle;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public interface ILoginModel {
    void login(String username, String password, LoginCallBack callBack);
}
