package com.yijie.com.kindergartenapp.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.utils.ImageLoaderUtil;


public class MyImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        //加载网络图片
        if (path.startsWith("http:")){
            ImageLoaderUtil.getImageLoader(activity).displayImage(path, imageView, ImageLoaderUtil.getPhotoImageOption());
        }else{
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(path),imageView,getPhotoImageOption());
        }

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
