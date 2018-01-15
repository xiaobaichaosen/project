package com.yijie.com.yijie.activity.login.modle;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void login(String username, String password, LoginCallBack callBack) {
        //请求网络
        if (username.equals("123") && password.equals("123")){
            callBack.onLoginSuccess();
        }else{
            callBack.onLoginFail();
        }

    }
}
