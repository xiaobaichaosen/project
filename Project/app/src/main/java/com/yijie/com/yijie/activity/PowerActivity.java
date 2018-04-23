package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.PopupWindowCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewContactActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.view.ShowTextViewDownPopuWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/2/26.
 * 权限控制
 */

public class PowerActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_power);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("权限管理");
    }
    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
//            case R.id.tv_modity:
//                ShowTextViewDownPopuWindow mWindow = new ShowTextViewDownPopuWindow(this);
//              mWindow.showAsDropDown(tvModity);
//                break;
//            case R.id.tv_modity1:
//                ShowTextViewDownPopuWindow mWindow1 = new ShowTextViewDownPopuWindow(this);
//                mWindow1.showAsDropDown(tvModity1);
//                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
