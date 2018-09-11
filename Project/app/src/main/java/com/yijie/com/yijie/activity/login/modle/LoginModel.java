package com.yijie.com.yijie.activity.login.modle;

import android.app.Application;
import android.content.Intent;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.activity.registerd.PassWordActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public class LoginModel implements ILoginModel {
    @Override
    public void login(String username, String password, final LoginCallBack callBack) {


        //请求网络

//        HttpUtils instance = HttpUtils.getinstance();
        Map map=new HashMap();
        map.put("phone",username);
        map.put("password",password);
//        instance.post(Constant.LOGIN, map, new BaseCallback<String>() {
//            @Override
//            public void onRequestBefore() {
//               callBack.beforLogin();
//            }
//
//            @Override
//            public void onFailure(Request request, Exception e) {
//                callBack.onLoginFail(e);
//            }
//
//            @Override
//            public void onSuccess(Response response, String s) {
//                callBack.onLoginSuccess(s);
//
//            }
//
//            @Override
//            public void onError(Response response, int errorCode, Exception e) {
//
//            }
//        });

    }
}
