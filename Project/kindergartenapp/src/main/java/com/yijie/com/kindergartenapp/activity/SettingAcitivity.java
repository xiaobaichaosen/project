package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.login.LoginActivity;
import com.yijie.com.kindergartenapp.base.AppManager;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/6/28.
 * 设置页面
 */

public class SettingAcitivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_exit:
                Intent intent = new Intent();
                intent.setClass(SettingAcitivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferencesUtils.setParam(SettingAcitivity.this, "isLogin", "");
                SharedPreferencesUtils.setParam(SettingAcitivity.this, "userId", "");
                AppManager.getAppManager().AppExit(this);
                break;
        }
    }
}
