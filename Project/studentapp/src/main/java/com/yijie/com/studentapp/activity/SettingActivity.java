package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.LoginActivity;
import com.yijie.com.studentapp.base.AppManager;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.UpdateAppUtils;

/**
 * Created by 奕杰平台 on 2018/1/30.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.phone_binding)
    RelativeLayout phoneBinding;
    @BindView(R.id.modity_password)
    RelativeLayout modityPassword;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.message_remind)
    RelativeLayout messageRemind;
    @BindView(R.id.remove_cache)
    RelativeLayout removeCache;
    @BindView(R.id.exit_app)
    Button exitApp;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("设置");
    }

    @OnClick({R.id.back, R.id.phone_binding, R.id.modity_password, R.id.message_remind, R.id.remove_cache, R.id.exit_app,R.id.rl_update})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.phone_binding:
                ShowToastUtils.showToastMsg(this, "手机绑定");
                break;
            case R.id.modity_password:
                ShowToastUtils.showToastMsg(this, "修改密码");
                break;
            case R.id.message_remind:

                break;

            case R.id.remove_cache:
                ShowToastUtils.showToastMsg(this, "清除缓存");
                break;
            case R.id.rl_update:
                //最简方式
                UpdateAppUtils.from(this)
                        .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
                        .serverVersionCode(2)  //服务器versionCode
                        .serverVersionName("2.0") //服务器versionName
//                        .updateInfo("1，添加删除信用卡接口\r\n 2，添加vip认证\r\n 3，区分自定义消费，一个小时不限制。\r\n4，添加放弃任务接口，小时内不生成。\r\n5，消费任务手动生成。")  //更新日志信息 String
                        .apkPath("http://192.168.0.163:8089/yijie/upload/student/student_user_id_16/head_pic_/1.apk") //最新apk下载地址
                        .update();
                break;
            case R.id.exit_app:
                SharedPreferencesUtils.setParam(SettingActivity.this, "user", "");
                AppManager.getAppManager().AppExit(this);
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
