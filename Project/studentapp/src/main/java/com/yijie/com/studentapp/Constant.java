package com.yijie.com.studentapp;

/**
 * Created by 奕杰平台 on 2018/1/17.
 */

public class Constant {
    public static final  String baseUrl="http://192.168.0.163:8080";
    //登陆接口
    public static final  String loginUrl=baseUrl+"/login";
    public static final  String loginUrl1=baseUrl+"/getAppId";
    //获取验证码接口
    public static final  String getRegistCodeUrl=baseUrl+"/getRegistCode";
    //注册接口
    public static final  String registUrl=baseUrl+"/rigst";

    //头像上传接口
    public static final  String headUploadUrl=baseUrl+"/headload";
    //档案荣誉证书上传图片接口
    public static final  String honoraryCcertificateUploadUrl=baseUrl+"/batch/upload";

    //档案荣誉证书获取图片接口
    public static final  String honoraryCcertificateGetUrl=baseUrl+"/getPhotoList";
}
