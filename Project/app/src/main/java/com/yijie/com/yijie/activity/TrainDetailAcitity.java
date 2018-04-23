package com.yijie.com.yijie.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/2/24.
 * 培训详情
 */

public class TrainDetailAcitity extends BaseActivity {
    @BindView(R.id.to_trainTime)
    RelativeLayout toTrainTime;
    @BindView(R.id.to_adress)
    RelativeLayout toAdress;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_trainTime)
    TextView tvTrainTime;
    @BindView(R.id.back)
    TextView back;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_train);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        title.setText("培训详情");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.to_trainTime, R.id.to_adress,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_trainTime:
                TimePickerView.Type type = TimePickerView.Type.ALL;
                String format = "yyyy-MM-dd HH:mm ";
                ViewUtils.alertTimerPicker(this, type, format, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvTrainTime.setText(date);
                    }
                });
                break;
            case R.id.to_adress:
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
