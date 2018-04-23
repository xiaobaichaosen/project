package com.yijie.com.kindergartenapp.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
    /**
     * 获取实例
     *
     * @return
     */
    public static HttpUtils getinstance() {
        if (mOkHttpUtilsInstance == null) {
            synchronized (HttpUtils.class) {
                if (mOkHttpUtilsInstance == null) {
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
        Request request = buildRequest(url, null, HttpMethodType.GET);
        request(request, callback);
    }    /**
     * 对外公开的post方法
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, String> params, BaseCallback callback) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        request(request, callback);
    }
//    /**
//     * 上传图片和参数
//     * @param resUrl
//     * @param parames
//     * @param pathList
//     */
//  public void uploadFiles(String resUrl, Map<String,String> parames, List<ImageItem> pathList, BaseCallback callback)
//  {
//      MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//      Map<String,File> files=new HashMap<>();
//      for (int i = 0; i < pathList.size(); i++) {
//            String newPath=BitmapUtils.compressImageUpload(pathList.get(i).path);
//            files.put(pathList.get(i).name+i,new File(newPath));
//          //image为参数名
//          builder.addFormDataPart("image",pathList.get(i).name+i,RequestBody.create(MediaType.parse("image/*"),new File(newPath)));
//      }
//          RequestBody requestBody = builder.build();
//           Request request = new Request.Builder()
//                   .url(resUrl)
//              .post(requestBody)
//              .build();
//                request(request, callback);
//
//
//
//
//  }
    /**
     * 上传图片和参数
     * @param resUrl
     * @param parames
     * @param files
     */
    public void uploadFiles(String resUrl, Map<String,String> parames, List<File> files,BaseCallback callback)
    {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (parames==null){
            for (File file:files){
                if (file.exists()){
                    builder.addFormDataPart("headload",file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
                }
            }
        }else {
            for (String key:parames.keySet()){
                builder.addFormDataPart(key,parames.get(key));
            }
            for (File file:files){
                if (file.exists()){
                    builder.addFormDataPart("headload",file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
                }
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
//              .header("Authorization", "Client-ID " + "...")
                .url(resUrl)
                .post(requestBody)
                .build();
        request(request, callback);

    }
    /**
     * 构建请求对象
     */
    private Request buildRequest(String url, Map<String, String> params, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET) {
            builder.get();
        } else if (type == HttpMethodType.POST) {
            builder.post(buildRequestBody(params));
        }        return builder.build();
    }

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
                callback.onSuccess(response, o);
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

}
