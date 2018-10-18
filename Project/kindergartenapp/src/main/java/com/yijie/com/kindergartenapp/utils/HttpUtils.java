package com.yijie.com.kindergartenapp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/26.
 */

public class HttpUtils {

    OkHttpClient mClientInstance;
    private Handler mHandler;
    private Gson mGson;
    private static HttpUtils mOkHttpUtilsInstance;
    private static Context mContext;
    public   MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    /**
     * 获取实例
     *
     * @return
     */
    public static HttpUtils getinstance(Context context) {
        if (mOkHttpUtilsInstance == null) {
            synchronized (HttpUtils.class) {
                if (mOkHttpUtilsInstance == null) {
                    mContext=context;

                    mOkHttpUtilsInstance = new HttpUtils();


                }
            }
        }        return mOkHttpUtilsInstance;
    }
    private HttpUtils() {
            mClientInstance = new OkHttpClient.Builder()

                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)

                    .build();
            mGson=new Gson();

            mHandler = new Handler(Looper.getMainLooper());

    }


    /**
     * 对外公开的get方法
     *
     * @param url
     * @param callback
     * new BaseCallback<List<Banner>>() 传集合
     */
    public void get(String url, BaseCallback callback) {
        Request request = new Request.Builder()
                .header("cookie", "JSESSIONID="+(String)(SharedPreferencesUtils.getParam(mContext,"cookie","")))
                .url(url)
                .build();
        request(request, callback);

    }    /**
     * 对外公开的post方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, String> params, BaseCallback callback) {
        Request request = new Request.Builder()
                .header("cookie", "JSESSIONID=" + (String) (SharedPreferencesUtils.getParam(mContext, "cookie", "")))
                .post(buildRequestBody(params))
                .url(url)
                .build();
        request(request, callback);

    }

    /**
     * 上传json到服务器
     * @param url
     * @param object
     * @param callback
     *
     */
    public void postJson(String url,Object object, BaseCallback callback) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        RequestBody requestBody = RequestBody.create(JSON,gson.toJson(object));
        Request request = new Request.Builder()
                .url(url)
                .header("cookie", "JSESSIONID="+(String)(SharedPreferencesUtils.getParam(mContext,"cookie","")))
                .post(requestBody)
                .build();
        request(request, callback);

    }

    /**
     * 上传图片和参数
     * @param resUrl
     * @param parames
     * @param files
     */
    /**
     * 上传图片和参数
     * @param resUrl
     * @param parames
     * @param files
     */
    public void uploadFiles(String picName,String resUrl, Map<String,String> parames, List<File> files,BaseCallback callback)
    {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (parames==null){
            for (File file:files){
                if (file.exists()){
                    builder.addFormDataPart(picName,file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
                }
            }
        }else {
            for (String key:parames.keySet()){
                builder.addFormDataPart(key,parames.get(key));
            }
            for (File file:files){
                if (file.exists()){
                    builder.addFormDataPart(picName,file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
                }
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .header("cookie", "JSESSIONID="+(String)(SharedPreferencesUtils.getParam(mContext,"cookie","")))
                .url(resUrl)
                .post(requestBody)
                .build();
        request(request, callback);

    }

    /**
     * 构建请求对象
     */
//    private Request buildRequest(String url, Map<String, String> params, HttpMethodType type) {
//        Request.Builder builder = new Request.Builder();
//        builder.url(url).header("cookie","JSESSIONID="+(String)(SharedPreferencesUtils.getParam(mContext,"token","")));
//        if (type == HttpMethodType.GET) {
//            builder.get();
//        } else if (type == HttpMethodType.POST) {
//            builder.post(buildRequestBody(params));
//        }        return builder.build();
//    }

    /**
     * 通过Map的键值对构建请求对象的body
     *
     * @param params
     * @return
     */
    private RequestBody buildRequestBody(Map<String, String> params) {


        FormBody.Builder builder=new FormBody.Builder();
        if (params != null) {

            for (Map.Entry<String, String> entity : params.entrySet()) {
                builder.add(entity.getKey(), entity.getValue());
            }
        }        return builder.build();
    }    /**
     * 这个枚举用于指明是哪一种提交方式
     */
    enum HttpMethodType {
        GET,
        POST
    }
    /**
     * 封装reques方法
     */
    public void request(final Request request,final  BaseCallback callback){
        if (NetWorkUtils.isNetworkConnected(mContext)) {
            callback.onRequestBefore();
            mClientInstance.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callbackFailure(request,callback,e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()){
                        String string = response.body().string();
                        if (callback.mType==String.class){
                            //如果我们需要返回String l类型
                            callbackSuccess(response,string,callback);
                        }else {
                            //如果返回的是其他类型，则利用Gson去解析
                            try {
                                Object o = mGson.fromJson(string, callback.mType);
                                callbackSuccess(response, o, callback);
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                                callbackError(response, callback, e);
                            }
                        }
                    }else{
                        //返回错误
                        callbackError(response,callback,null);
                    }
                }
            });
        }else{
            ShowToastUtils.showToastMsg(mContext,"请检测网络是否可用");
        }
    }



    /* 在主线程中执行的回调
     *
     * @param response
     * @param resString
     * @param callback
     */
    private void callbackSuccess(final Response response, final Object o, final BaseCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(response,o);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }    /**
     * 在主线程中执行的回调
     * @param response
     * @param callback
     * @param e
     */
    private void callbackError(final Response response, final BaseCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }    /**
     * 在主线程中执行的回调
     * @param request
     * @param callback
     * @param e
     */
    private void callbackFailure(final Request request, final BaseCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }
    //https需要加
//    private class SafeTrustManager implements X509TrustManager {
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            try {
//                for (X509Certificate certificate : chain) {
//                    certificate.checkValidity(); //检查证书是否过期，签名是否通过等
//                }
//            } catch (Exception e) {
//                throw new CertificateException(e);
//            }
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            return new X509Certificate[0];
//        }
//    }

//    private class SafeHostnameVerifier implements HostnameVerifier {
//        @Override
//        public boolean verify(String hostname, SSLSession session) {
//            //验证主机名是否匹配
//
//            return true;
//        }
//    }
}
