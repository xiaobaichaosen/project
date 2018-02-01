package com.yijie.com.studentapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CommomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/2/1.
 * 简历预览
 */

public class ResumepreviewActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_resumnpreview);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tvRecommend.setText("发送简历");
        title.setText("简历预览");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                new CommomDialog(this, R.style.dialog, "您确定发送简历么？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            ShowToastUtils.showToastMsg(ResumepreviewActivity.this,"发送成功");
                            finish();
                            EventBus.getDefault().post("sendSample");
                        }

                    }
                })
                        .setTitle("提示").show();
                break;
        }
    }
}
