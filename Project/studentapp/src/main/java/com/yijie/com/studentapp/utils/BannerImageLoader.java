package com.yijie.com.studentapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.yijie.com.studentapp.bean.SchoolAdress;
import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoaderUtil.getImageLoader(context).displayImage(((SchoolAdress)path).getName(),imageView, ImageLoaderUtil.getPhotoImageOption());
    }


}
