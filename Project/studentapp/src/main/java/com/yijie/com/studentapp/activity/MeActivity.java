package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.niorgai.StatusBarCompat;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/1/30.
 */

public class MeActivity extends BaseActivity {
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @BindView(R.id.collect_position)
    RelativeLayout collectPosition;

    @BindView(R.id.subscribe_position)
    RelativeLayout subscribePosition;
    @BindView(R.id.focus_kendergard)
    RelativeLayout focusKendergard;
    @BindView(R.id.position_consultant)
    TextView positionConsultant;
    @BindView(R.id.to_newAppointmenttime)
    RelativeLayout toNewAppointmenttime;
    @BindView(R.id.help_feedback)
    RelativeLayout helpFeedback;
    @BindView(R.id.settings)
    RelativeLayout settings;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_me);
    }

    @OnClick({R.id.collect_position, R.id.subscribe_position, R.id.to_newAppointmenttime, R.id.focus_kendergard, R.id.help_feedback, R.id.position_consultant, R.id.settings})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.collect_position:
                ShowToastUtils.showToastMsg(this,"收藏职位");
                break;

            case R.id.subscribe_position:
                ShowToastUtils.showToastMsg(this,"订阅职位");
                break;

            case R.id.focus_kendergard:
                ShowToastUtils.showToastMsg(this,"关注园所");
                break;
            case R.id.position_consultant:
            ShowToastUtils.showToastMsg(this,"我的职业顾问");

            break;
            case R.id.help_feedback:
                ShowToastUtils.showToastMsg(this,"帮助与反馈");
                break;
            case R.id.settings:
                ShowToastUtils.showToastMsg(this,"设置");
                break;
        }
    }

    @Override
    public void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        StatusBarCompat.setStatusBarColorForCollapsingToolbar(this, appbar, collapsingToolbar, toolbar, getResources().getColor(R.color.colorTheme));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
