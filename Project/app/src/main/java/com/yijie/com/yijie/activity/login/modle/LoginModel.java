package com.yijie.com.yijie.activity.login.modle;

import com.yijie.com.yijie.Constant;

import itheima.okhttp3utils.ItHeiMaHttp;
import itheima.okhttp3utils.WSCallBack;
import okhttp3.Call;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void login(String username, String password, final LoginCallBack callBack) {
        //请求网络
//        ItHeiMaHttp instance = ItHeiMaHttp.getInstance();
//        instance.addParam("userName",username);
//        instance.addParam("passWord",password);
//        instance.get(Constant.loginUrl, new WSCallBack<String>() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//                callBack.onLoginFail();
//            }
//
//            @Override
//            public void onSuccess(String o) {
//                callBack.onLoginSuccess(o);
//            }
//        });

        if (username.equals("123") && password.equals("123")){
            callBack.onLoginSuccess("登陆成功");
        }else{
            callBack.onLoginFail("账号或密码不正确");
        }

    }
}
