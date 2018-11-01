package com.yijie.com.yijie.base;

import android.app.Application;

import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.VersionUpdate;
import com.yijie.com.yijie.util.MyImageLoader;
import com.yijie.com.yijie.utils.ImageLoaderUpload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 奕杰平台 on 2017/12/14.
 */

public class APPApplication extends Application {
    private int maxImgCount = 8;               //允许选择图片最大数

    public String getDetailAdress() {
        return detailAdress;
    }

    public void setDetailAdress(String detailAdress) {
        this.detailAdress = detailAdress;
    }

    private String detailAdress;
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initImagePicker();
        UMShareAPI.get(this);
        JPushInterface.setDebugMode(true);     // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        Config.DEBUG=true;
//        Set<String> tags = new HashSet<String>();
//        tags.add("培训老师");
//        JPushInterface.setTags(this, tags, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//            }
//        });
//        JPushInterface.setAlias(this, "培训老师", new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//            }
//        });
        CretinAutoUpdateUtils.Builder builder =
                new CretinAutoUpdateUtils.Builder()
                        //设置更新api
                        .setBaseUrl(Constant.GETVERSIONUPDATE+"?appType=2")
                        //设置是否显示忽略此版本
                        .setIgnoreThisVersion(true)
                        .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                        //设置自定义的Model类
                        .setTransition(new VersionUpdate())
                        //设置下载显示形式 对话框或者通知栏显示 二选一
                        .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG)
                        //设置下载时展示的图标
                        .setIconRes(R.mipmap.ic_launcher)
                        //设置下载时展示的应用名称
                        .setAppName("测试应用")
                        .build();
        CretinAutoUpdateUtils.init(builder);
    }
    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");

        PlatformConfig.setQQZone("1106603559", "8DgivbG8OBSDr4hi");


    }
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new ImageLoaderUpload());   //设置图片加载器
        imagePicker.setImageLoader(new MyImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
}
