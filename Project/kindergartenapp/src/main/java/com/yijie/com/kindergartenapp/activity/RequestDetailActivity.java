package com.yijie.com.kindergartenapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.MainActivity;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.login.LoginActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.SchoolPractice;
import com.yijie.com.kindergartenapp.fragment.RequestDetailFragment;
import com.yijie.com.kindergartenapp.fragment.ResumnListFragment;
import com.yijie.com.kindergartenapp.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/6/25.
 * 招聘发布详细，和简历碎片
 */

public class RequestDetailActivity extends BaseActivity {

    int current = 0;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    //发布详细碎片
    private RequestDetailFragment requestDetailFragment;

    //收到简历的碎片
    private ResumnListFragment resumnListFragment;
    //园所需求id
    public int kenderNeedId;
   private String projectName;
    public int schoolId;
    public int projectId;
    public int kinderId;
    private SchoolPractice  schoolPractice;
    public static Activity instance = null;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_requestdetail);
    }

    @Override
    public void init() {
        instance=this;
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        initProjectDetailFragment();
        tvRecommend.setText("修改");
        kenderNeedId = getIntent().getIntExtra("id", 0);
        //TODO

        schoolPractice = (SchoolPractice) getIntent().getExtras().getSerializable("SchoolPractice");
        projectName = getIntent().getStringExtra("projectName");
       String schoolName = getIntent().getStringExtra("schoolName");
        schoolId = getIntent().getIntExtra("schoolId", 0);
        projectId = getIntent().getIntExtra("projectId", 0);
        kinderId= getIntent().getIntExtra("kinderId", 0);
       title.setText(schoolName);
//        title.setTextSize(ViewUtils.dp2px(this,5));
        title.setText("招聘发布详情");
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("招聘发布详情"));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("项目列表", R.mipmap.arrow_down)));
        tabLayout.addTab(tabLayout.newTab().setText("简历"));
        //绑定tab点击事件
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    tvRecommend.setVisibility(View.VISIBLE);
                    tvRecommend.setText("修改");
                    title.setText("招聘发布详细");
                    initProjectDetailFragment();
                    current = 0;
                } else if (position == 1) {
                    current = 1;
                    initMemeryFragment();
                    tvRecommend.setVisibility(View.GONE);
                    title.setText("简历");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initProjectDetailFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (requestDetailFragment == null) {
            requestDetailFragment = new RequestDetailFragment();
        }
        transaction.replace(R.id.main_frame_layout, requestDetailFragment);
        //提交事务
        transaction.commit();


    }


    private void initMemeryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (resumnListFragment == null) {
            resumnListFragment = new ResumnListFragment();
        }
        transaction.replace(R.id.main_frame_layout, resumnListFragment);
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.action_item})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                Intent intent = new Intent();
                intent.putExtra("kenderNeedId",kenderNeedId);
                intent.putExtra("projectName",projectName);
                intent.putExtra("schoolId",schoolId);
                intent.putExtra("projectId",projectId);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("SchoolPractice",schoolPractice);
                intent.putExtras(mBundle);
                 intent.setClass(RequestDetailActivity.this, RequstActivity.class);
                startActivity(intent);
                break;
            case R.id.action_item:

                break;

        }
    }
}
