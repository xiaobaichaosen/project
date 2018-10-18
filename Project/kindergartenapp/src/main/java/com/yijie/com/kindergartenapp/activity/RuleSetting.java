package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 规则设置
 */
public class RuleSetting extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.title)
    TextView title;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rule);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("规则设置");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_time, R.id.rl_date, R.id.tv_save})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_time:
                intent.setClass(RuleSetting.this, ClassesSetActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_date:
                intent.setClass(RuleSetting.this, SignDateActivity.class);
                startActivity(intent);


                break;
            case R.id.tv_save:
                break;
        }
    }
}
