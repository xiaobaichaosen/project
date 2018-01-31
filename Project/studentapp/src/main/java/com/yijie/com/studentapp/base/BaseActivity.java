package com.yijie.com.studentapp.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.android.tu.loadingdialog.LoadingDailog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public abstract  class BaseActivity extends AppCompatActivity {
    Unbinder bind;
    public Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏展示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //统一管理活动
        AppManager.getAppManager().addActivity(this);
        setContentView();
        ButterKnife.bind(this);


        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);

        dialog = loadBuilder.create();
        bind = ButterKnife.bind(this);


        init();
        // 系统 6.0 以上 状态栏白底黑字的实现方法
//        this.getWindow()
//                .getDecorView()
//                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        StutasToolUtils.MIUISetStatusBarLightMode(this.getWindow(), true);
//        StutasToolUtils. FlymeSetStatusBarLightMode(this.getWindow(), true);

    }


    //设置布局
    public abstract void setContentView();
    //初始化
    public abstract void init();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
//        AppManager.getAppManager().finishActivity();

        AppManager.getAppManager().finishActivity(this);

    }
//    /**
//     * 打开activity
//     */
//    protected void openActivity(Class<?> cls) {
//        openActivity(this, cls);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }
//    /**
//     * 打开activity
//     */
//    public static void openActivity(Context context, Class<?> cls) {
//        Intent intent = new Intent(context, cls);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
//    }

    public static void setColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View StatusView = createStatusView(activity,color);
            // 添加statusView到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(StatusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content))
                    .getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     * @param activity 需要设置的activity
     * @param color 状态栏的颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏的高度
        int resourceId = activity.getResources()
                .getIdentifier("status_bar_height","dimen","android");
        int statusBarHeight = activity.getResources()
                .getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);

        return statusView;
    }

    /***********************我是分割线***************************/

    /**
     * 使状态栏透明
     * 适用于图片作为背景的界面，此时需要图片填充到状态栏
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup)((ViewGroup)activity
                    .findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
}
