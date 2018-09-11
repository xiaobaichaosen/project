package com.yijie.com.kindergartenapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;

import java.io.File;

/**
 * Created by 奕杰平台 on 2018/2/5.
 */

public class ImageLoaderUpload implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageLoaderUtil.getImageLoader(activity).displayImage(path, imageView, ImageLoaderUtil.getPhotoImageOption());

    }


    @Override
    public void clearMemoryCache() {

    }


}
