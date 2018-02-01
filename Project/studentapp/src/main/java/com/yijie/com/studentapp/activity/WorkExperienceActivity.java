package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/2/1.
 */

public class WorkExperienceActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;

    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_newEducation)
    EditText tvNewEducation;
    @BindView(R.id.ll_name)
    RelativeLayout llName;
    @BindView(R.id.tv_newSchoolAdress)
    TextView tvNewSchoolAdress;
    @BindView(R.id.ll_gender)
    RelativeLayout llGender;
    @BindView(R.id.tv_newType)
    TextView tvNewType;
    @BindView(R.id.to_newContact)
    ImageView toNewContact;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.line_or_v)
    View lineOrV;
    @BindView(R.id.tv_newSchoolSample)
    TextView tvNewSchoolSample;
    @BindView(R.id.rl_weight)
    RelativeLayout rlWeight;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.to_newSchoolPicture)
    TextView toNewSchoolPicture;
    @BindView(R.id.tv_national)
    RelativeLayout tvNational;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.rl_nativePlace)
    RelativeLayout rlNativePlace;
    @BindView(R.id.tv_newNote)
    TextView tvNewNote;
    @BindView(R.id.rl_dataofbirth)
    RelativeLayout rlDataofbirth;
    @BindView(R.id.to_newNodetal)
    TextView toNewNodetal;
    @BindView(R.id.rl_registeredPermanent)
    RelativeLayout rlRegisteredPermanent;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personalinformation);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("工作经历");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @OnClick({R.id.back, R.id.rl_title, R.id.ll_name, R.id.ll_gender, R.id.rl_height, R.id.rl_weight, R.id.tv_national, R.id.rl_nativePlace, R.id.rl_dataofbirth, R.id.rl_registeredPermanent,R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_title:
                break;
            case R.id.ll_name:
                break;
            case R.id.ll_gender:
                break;
            case R.id.rl_height:
                break;
            case R.id.rl_weight:
                break;
            case R.id.tv_national:
                break;
            case R.id.rl_nativePlace:
                break;
            case R.id.rl_dataofbirth:
                break;
            case R.id.rl_registeredPermanent:
                break;
            case R.id.tv_recommend:

                EventBus.getDefault().post("workexperience");
                finish();
                break;

        }
    }
}


