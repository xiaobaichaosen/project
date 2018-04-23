package com.yijie.com.studentapp.activity.login.modle;




import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void login(String username, String password, final LoginCallBack callBack) {


        //请求网络

        HttpUtils instance = HttpUtils.getinstance();
        Map map=new HashMap();
        map.put("phone",username);
        map.put("password",password);
        instance.post(Constant.loginUrl, map, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
               callBack.beforLogin();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onLoginFail(e);
            }

            @Override
            public void onSuccess(Response response, String s) {
                callBack.onLoginSuccess(s);

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });

    }
}
