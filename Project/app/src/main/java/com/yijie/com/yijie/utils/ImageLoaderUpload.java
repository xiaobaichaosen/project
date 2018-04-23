package com.yijie.com.yijie.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yijie.com.yijie.R;

/**
 * Created by 奕杰平台 on 2018/2/5.
 */

public class ImageLoaderUpload implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(path),imageView,getPhotoImageOption());

    }


    @Override
    public void clearMemoryCache() {

    }

    public DisplayImageOptions getPhotoImageOption() {
        Integer extra = 1;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .extraForDownloader(extra)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;
    }

}
