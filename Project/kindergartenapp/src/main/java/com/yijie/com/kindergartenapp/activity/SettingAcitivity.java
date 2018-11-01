package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.yijie.com.kindergartenapp.Constant;
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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void init() {
        String cellphone = (String) SharedPreferencesUtils.getParam(this, "cellphone", "");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("设置");
        tvPhone.setText(cellphone);
        //第一种形式 自定义参数
//        CretinAutoUpdateUtils.Builder builder =
//                new CretinAutoUpdateUtils.Builder()
//                        //设置更新api
//                        .setBaseUrl(Constant.UPDATE)
//                        //设置是否显示忽略此版本
//                        .setIgnoreThisVersion(true)
//                        .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_POST)
//                        //设置自定义的Model类
//                        .setTransition(new UpdateModel())
//                        //设置下载显示形式 对话框或者通知栏显示 二选一
//                        .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG)
//                        //设置下载时展示的图标
//                        .setIconRes(R.mipmap.ic_launcher)
//                        //设置下载时展示的应用名称
//                        .setAppName("测试应用")
//                        .build();
//        CretinAutoUpdateUtils.init(builder);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_exit, R.id.rl_modiPwd, R.id.rl_update})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_modiPwd:
                intent.setClass(SettingAcitivity.this, ModityPwdAcitivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.rl_exit:
                intent.setClass(SettingAcitivity.this, LoginActivity.class);
                SharedPreferencesUtils.setParam(SettingAcitivity.this, "isLogin", "");
                SharedPreferencesUtils.setParam(SettingAcitivity.this, "userId", "");
                SharedPreferencesUtils.setParam(SettingAcitivity.this, "cellphone", "");
                AppManager.getAppManager().AppExit(this);
                startActivity(intent);
                break;

            case R.id.rl_update:
                CretinAutoUpdateUtils.getInstance(SettingAcitivity.this).check();
                break;
        }
    }
}
