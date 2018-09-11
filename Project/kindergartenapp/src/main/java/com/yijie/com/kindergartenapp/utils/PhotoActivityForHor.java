package com.yijie.com.kindergartenapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.view.SmoothImageView;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 奕杰平台 on 2017/12/18.
 * 横向的RecycleView
 */

public class PhotoActivityForHor extends FragmentActivity  {	private SmoothImageView ivPhoto;
    private ProgressBar progressBar;
    private Rect startBounds;
    private Activity activity;
    private RelativeLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kendergard_item_photo);
        init();

    }


    public void init() {
        ivPhoto = (SmoothImageView)findViewById(R.id.iv_photo);
        root = (RelativeLayout)findViewById(R.id.root);
        ivPhoto.setMinimumScale(0.5f);

        ivPhoto.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                root.setBackgroundColor(Color.TRANSPARENT);

                transformOut(new SmoothImageView.onTransformListener() {
                    @Override
                    public void onTransformCompleted(SmoothImageView.Status status) {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                });
            }
        });


        progressBar = (ProgressBar)findViewById(R.id.progress);


        String imgUrl = getIntent().getStringExtra("imgUrl");
        startBounds = getIntent().getParcelableExtra("startBounds");
        ivPhoto.transformIn(startBounds, null);
        File file = DiskCacheUtils.findInCache(imgUrl, ImageLoader.getInstance().getDiskCache());
        final boolean showLoading = file == null || !file.exists();
        ivPhoto.setTransformEnabled(!showLoading);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)  // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(null) // resource or drawable
                .showImageForEmptyUri(R.mipmap.ic_launcher) // resource or drawable
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();
        ImageLoader.getInstance().displayImage(imgUrl, ivPhoto,options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                if(showLoading){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
                ivPhoto.setTransformEnabled(true);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                progressBar.setVisibility(View.GONE);
                ivPhoto.setTransformEnabled(true);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                progressBar.setVisibility(View.GONE);
                ivPhoto.setTransformEnabled(true);
            }
        });
    }


    public void transformOut(SmoothImageView.onTransformListener listener){
        ivPhoto.transformOut(startBounds,listener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            root.setBackgroundColor(Color.TRANSPARENT);

            transformOut(new SmoothImageView.onTransformListener() {
                @Override
                public void onTransformCompleted(SmoothImageView.Status status) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            });
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
