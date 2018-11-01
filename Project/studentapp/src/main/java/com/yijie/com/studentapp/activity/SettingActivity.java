package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.LoginActivity;
import com.yijie.com.studentapp.base.AppManager;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.VersionUpdate;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

                CretinAutoUpdateUtils.getInstance(SettingActivity.this).check();
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
