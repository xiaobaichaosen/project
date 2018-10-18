package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 班次设置
 */
public class ClassesSetActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_newClassSet)
    TextView tvNewClassSet;
    @BindView(R.id.title)
    TextView title;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_classset);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("班次设置");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_newClassSet,R.id.tv_save})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_newClassSet:
                intent.setClass(ClassesSetActivity.this, NewClassSetActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_save:

             break;
        }
    }
}
