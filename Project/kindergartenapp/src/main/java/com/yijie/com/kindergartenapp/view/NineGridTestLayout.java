package com.yijie.com.kindergartenapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yijie.com.kindergartenapp.utils.ImageLoaderUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/12
 */
public class NineGridTestLayout extends NineGridLayout {
    public NineGridTestLayout(Context context) {
        super(context);
    }

    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {

    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {

    }

//    protected static final int MAX_W_H_RATIO = 3;
//
//    public NineGridTestLayout(Context context) {
//        super(context);
//    }
//
//    public NineGridTestLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
//
//        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
//                int w = bitmap.getWidth();
//                int h = bitmap.getHeight();
//
//                int newW;
//                int newH;
//                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
//                    newW = parentWidth / 2;
//                    newH = newW * 5 / 3;
//                } else if (h < w) {//h:w = 2:3
//                    newW = parentWidth * 2 / 3;
//                    newH = newW * 2 / 3;
//                } else {//newH:h = newW :w
//                    newW = parentWidth / 2;
//                    newH = h * newW / w;
//                }
//                setOneImageLayoutParams(imageView, newW, newH);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        });
//        return false;
//    }
//
//    @Override
//    protected void displayImage(RatioImageView imageView, String url) {
//        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
//    }
//
//    @Override
//    protected void onClickImage(int i, String url, List<String> urlList) {
////        Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(mContext, PhotoActivity.class);
//        String imageArray[] = new String[urlList.size()];
//        int childCount = getChildCount();
//        ArrayList<Rect> rects = new ArrayList<>();
//        for (int k = 0; k < childCount; k++) {
//
//            Rect rect = new Rect();
//            View child = getChildAt(k);
//            if (child instanceof  RatioImageView){
//                child.getGlobalVisibleRect(rect);
//                rects.add(rect);
//            }
//            imageArray[k] = urlList.get(k);
//
//        }
//
//
//        intent.putExtra("imgUrls", imageArray);
//        intent.putExtra("index", i);
//        intent.putExtra("bounds", rects);
//        mContext. startActivity(intent);
//
//    }
}
