package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.login.LoginActivity;
import com.yijie.com.yijie.activity.newschool.NewSchoolActivity;
import com.yijie.com.yijie.activity.school.SchoolActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/5/8.
 * 闪屏页
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void init() {
//        Handler handler = new Handler();
        //        第一次安装应用，不要用adb安装，而是要用系统的安装器去安装，安装完成后，直接点击打开，不要点击完成，进入应用，随便点开其他界面，点击home键让应用进入后台，然后再点击应用的图标进入应用，这是应用会重新回到入口界面，实际上应用已经重启了

        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        LinearLayout ll_root = (LinearLayout) this.findViewById(R.id.ll_root);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.10f, 0.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        ll_root.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                //判断登陆状
                String longin = (String) SharedPreferencesUtils.getParam(SplashActivity.this, "user", "");
                Intent intent = new Intent();
                if (!longin.equals("")){
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }else
                {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
