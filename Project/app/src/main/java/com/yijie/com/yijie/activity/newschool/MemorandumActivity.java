package com.yijie.com.yijie.activity.newschool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/2/24.
 * 备忘录
 */

public class MemorandumActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.back)
    TextView back;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_memorandum);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("备忘录");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_recommend, R.id.btn_send,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recommend:
                break;
            case R.id.btn_send:
                ShowToastUtils.showToastMsg(this, etContent.getText().toString().trim());
                break;

            case R.id.back:
                finish();
                break;


        }
    }


}
