package com.yijie.com.yijie.activity.newschool;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建实习详细
 */

public class NewInternshipDetailActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_newEducation)
    TextView tvNewEducation;
    @BindView(R.id.to_newEducation)
    TextView toNewEducation;
    @BindView(R.id.tv_newMonth)
    TextView tvNewMonth;
    @BindView(R.id.to_newMonth)
    TextView toNewMonth;
    @BindView(R.id.tv_newType)
    TextView tvNewType;
    @BindView(R.id.to_newType)
    TextView toNewType;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.to_line)
    TextView toLine;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.to_mode)
    TextView toMode;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.to_newAppointmenttime)
    TextView toNewAppointmenttime;
    private ArrayList<String> mList = new ArrayList<String>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newinternshipdetail);

    }

    @Override
    public void init() {
        setColor(NewInternshipDetailActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(NewInternshipDetailActivity.this); // 改变状态栏变成透明

        title.setText("实习详情");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");


            mList.add(new String("中专"));
        mList.add(new String("大专"));
        mList.add(new String("本科"));



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back,R.id.to_newEducation})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id. to_newEducation:


                ViewUtils.alertBottomWheelOption(NewInternshipDetailActivity.this, mList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                      tvNewEducation.setText(mList.get(postion));
                    }

            });
                break;


        }

    }
}
