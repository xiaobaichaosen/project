package com.yijie.com.yijie.base;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by 奕杰平台 on 2017/12/14.
 */

public class APPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        UMShareAPI.get(this);
//        Config.DEBUG=true;

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
}
