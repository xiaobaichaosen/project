package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewInternshipDetailActivity;
import com.yijie.com.yijie.activity.newschool.NewIntershipMoney;
import com.yijie.com.yijie.activity.newschool.NewSchoolLiaisonsActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.bean.school.SchoolTrainDetail;
import com.yijie.com.yijie.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by 奕杰平台 on 2018/5/22.
 * 新建项目页面
 */

public class ProjectActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    @BindView(R.id.rl_shipdetail)
    RelativeLayout rlShipdetail;
    @BindView(R.id.rl_shipmoney)
    RelativeLayout rlShipmoney;
    @BindView(R.id.rl_shoolcontact)
    RelativeLayout rlShoolcontact;

    @BindView(R.id.tv_shipdetail)
    TextView tvShipdetail;
    @BindView(R.id.tv_shipmoney)
    TextView tvShipmoney;
    @BindView(R.id.tv_scool_contact)
    TextView tvScoolContact;
    private String schoolId;
    //用来存储实习详情
    private SchoolPractice tempSchoolPractice;
    //用来存储实习薪资
    private SchoolSalaryInfo tempSchoolSalaryInfo;
    //用来存储院校联络人
    private SchoolTrainDetail tempSchoolTrainDetail;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_project);
        EventBus.getDefault().register(this);
    }

    @Override
    public void init() {
        schoolId = getIntent().getStringExtra("schoolId");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("新建项目");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_shipdetail, R.id.rl_shipmoney, R.id.rl_shoolcontact, R.id.tv_recommend})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_shipdetail:

                intent.putExtra("schoolId", schoolId);
                if (tempSchoolPractice != null) {
                    mBundle.putSerializable("tempSchoolPractice", tempSchoolPractice);
                }
                intent.putExtras(mBundle);
                intent.setClass(ProjectActivity.this, NewInternshipDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_shipmoney:
                if (tvShipdetail.getText().toString().equals("已填写")) {
                    intent.putExtra("practiceId", tempSchoolPractice.getId());
                    if (tempSchoolSalaryInfo != null) {
                        mBundle.putSerializable("tempSchoolSalaryInfo", tempSchoolSalaryInfo);
                    }
                    intent.putExtras(mBundle);
                    intent.setClass(ProjectActivity.this, NewIntershipMoney.class);
                    startActivity(intent);
                } else {
                    ShowToastUtils.showToastMsg(ProjectActivity.this, "请先填写实习详情");
                }
                break;
            case R.id.rl_shoolcontact:

                if (tvShipdetail.getText().toString().equals("已填写")) {
                    if (tempSchoolTrainDetail != null) {
                        mBundle.putSerializable("tempSchoolTrainDetail", tempSchoolTrainDetail);
                    }
                    intent.putExtras(mBundle);
                    intent.putExtra("practiceId", tempSchoolPractice.getId());
                    intent.setClass(ProjectActivity.this, NewSchoolLiaisonsActivity.class);
                    startActivity(intent);
                } else {
                    ShowToastUtils.showToastMsg(ProjectActivity.this, "请先填写实习详情");
                }
                break;
            case R.id.tv_recommend:
                finish();
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void shoolPractice(SchoolPractice schoolPractice) {
        tvShipdetail.setText("已填写");
        tempSchoolPractice = schoolPractice;

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void shoolPractice(SchoolSalaryInfo schoolSalaryInfo) {
        tvShipmoney.setText("已填写");
        tempSchoolSalaryInfo = schoolSalaryInfo;

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void schoolTrainDetail(SchoolTrainDetail schoolTrainDetail) {
        tvScoolContact.setText("已填写");
        tempSchoolTrainDetail = schoolTrainDetail;

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
