package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.login.LoginActivity;
import com.yijie.com.yijie.base.AppManager;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;

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
    @BindView(R.id.rl_moditypwd)
    RelativeLayout rlModitypwd;

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

    @OnClick({R.id.back, R.id.rl_exit,R.id.rl_moditypwd, R.id.rl_update})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_moditypwd:
                intent.setClass(SettingAcitivity.this, ModityPwdAcitivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.rl_exit:
                intent.setClass(SettingAcitivity.this, LoginActivity.class);
                SharedPreferencesUtils.setParam(SettingAcitivity.this, "user", "");
                AppManager.getAppManager().AppExit(this);
                startActivity(intent);
                break;
            case R.id.rl_update:
                CretinAutoUpdateUtils.getInstance(SettingAcitivity.this).check();
                break;
        }
    }


}
