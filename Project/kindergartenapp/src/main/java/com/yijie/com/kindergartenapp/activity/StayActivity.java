package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/3/29.
 */

public class StayActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cb_up)
    CheckBox cbUp;
    @BindView(R.id.cb_down)
    CheckBox cbDown;
    @BindView(R.id.cb_out)
    CheckBox cbOut;
    @BindView(R.id.cb_other)
    CheckBox cbOther;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_stay);
    }

    @Override
    public void init() {
        title.setText("住宿安排");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.cb_up, R.id.cb_down, R.id.cb_out, R.id.cb_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cb_up:
                break;
            case R.id.cb_down:
                break;
            case R.id.cb_out:
                break;
            case R.id.cb_other:
                break;
        }
    }
}
