package com.yijie.com.yijie.activity.newschool;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/1/22.
 * 学校简介
 */

public class NewSchoolIntroduction extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newschoolintroduction);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("学校简介");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        String textContent = (String) SharedPreferencesUtils.getParam(this, "textContent", "");
        etContent.setText(textContent);

    }
    //监听文本变化
    @OnTextChanged(value = R.id.et_content, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        tvNum.setText((2000-s.length())+"/2000");
    }
    @OnClick({R.id.action_item  ,R.id.back})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action_item:
                SharedPreferencesUtils.setParam(this,"textContent",etContent.getText().toString().trim());
                EventBus.getDefault().post("schoolIntroduction");
                finish();
                break;

            case R.id.back:
                  finish();
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
