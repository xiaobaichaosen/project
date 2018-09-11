package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.fragment.SchoolProjectDetailFragment;
import com.yijie.com.yijie.fragment.SchoolProjectMemeryFragment;
import com.yijie.com.yijie.util.ViewUtils;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.CircleImageView;
import com.yijie.com.yijie.view.StickyNavLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/5/16.
 * 项目详情+备忘录
 * 从学校列表进去的项项目
 */

public class SchoolProjectDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    //备忘录碎片
    private SchoolProjectMemeryFragment memeryFragment;

    //项目id
    public String practiceId;
    //是编辑还是完成
    public boolean changeText;
    //学校详情中联系人的编辑按钮
    TextView tvContactEdit;
    //项目详情碎片
    private SchoolProjectDetailFragment projectDetailFragment;
    //学校id
    public String schoolId;
    //用来判断   actionItem是哪个图片，来触发点击事件 1==项目，2==备忘录
    private int current;
    private String status;
    public String kindpeoNumSet;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_projectdetail);
    }

    @Override
    public void init() {

        String projectName = getIntent().getStringExtra("projectName");
        practiceId = getIntent().getStringExtra("practiceId");
        schoolId = getIntent().getStringExtra("schoolId");
        status = getIntent().getStringExtra("status");
        title.setText(projectName);
        title.setTextSize(ViewUtils.dp2px(this, 5));
//        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
//        setTranslucent(this); // 改变状态栏变成透明
        initProjectDetailFragment();
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setBackgroundResource(R.mipmap.setting);
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("项目详情"));
        tabLayout.addTab(tabLayout.newTab().setText("备忘录"));
        //绑定tab点击事件
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    tvRecommend.setVisibility(View.GONE);
                    actionItem.setVisibility(View.VISIBLE);
                    actionItem.setBackgroundResource(R.mipmap.setting);
                    initProjectDetailFragment();
                    current = 0;
                }
//                else if (position == 1) {
//                    initProjectListFragment();
//                    tvRecommend.setVisibility(View.GONE);
//                    actionItem.setVisibility(View.VISIBLE);
//                   actionItem.setBackgroundResource(R.mipmap.new_school);
//                   current=1;
//                }
                else if (position == 1) {
                    current = 1;
                    initMemeryFragment();
                    tvRecommend.setVisibility(View.GONE);
                    actionItem.setVisibility(View.VISIBLE);
                    actionItem.setBackgroundResource(R.mipmap.no_vode);

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
        if (projectDetailFragment == null) {
            projectDetailFragment = new SchoolProjectDetailFragment();
        }
        transaction.replace(R.id.main_frame_layout, projectDetailFragment);
        //提交事务
        transaction.commit();


    }


    //    private void initProjectListFragment(){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if(projectListFragment == null) {
//            projectListFragment = new ProjectListFragment();
//        }
//        transaction.replace(R.id.main_frame_layout, projectListFragment);
//        transaction.commit();
//    }
//
    private void initMemeryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (memeryFragment == null) {
            memeryFragment = new SchoolProjectMemeryFragment();
        }
        transaction.replace(R.id.main_frame_layout, memeryFragment);
        transaction.commit();
    }

    /**
     * 设置自定义的tabitem
     *
     * @param name
     * @param iconID
     * @return
     */
    private View tab_icon(String name, int iconID) {
        View newtab = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView textTitle = (TextView) newtab.findViewById(R.id.tv_title);
        textTitle.setText(name);
        TextView textIcon = (TextView) newtab.findViewById(R.id.tv_icon);
        textIcon.setBackgroundResource(iconID);
        return newtab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.action_item})

    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                break;
            case R.id.action_item:
                String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");
                if (current == 0) {
                    if (projectDetailFragment.schoolSalaryInfo == null) {
                        ShowToastUtils.showToastMsg(SchoolProjectDetailActivity.this, "请填写实习薪资");
                        return;
                    } else if (projectDetailFragment.schoolTrainDetail == null) {
                        ShowToastUtils.showToastMsg(SchoolProjectDetailActivity.this, "请填写培训联络人");
                        return;
                    }
//                    else{
//                        intent.putExtra("practiceId", practiceId);
//                        intent.putExtra("status", status);
//                        intent.setClass(SchoolProjectDetailActivity.this,PowerActivity.class);
//                        startActivity(intent);
//                    }
                    if (roleName.contains("开发老师")) {
                        intent.putExtra("practiceId", practiceId);
                        intent.putExtra("projectStatus", status);
                        intent.putExtra("schoolId", schoolId);
                        intent.setClass(SchoolProjectDetailActivity.this, PowerActivity.class);
                        startActivity(intent);

                    } else {
                        if (projectDetailFragment.schoolTrainDetail.getToBeijingTime() == null) {
                            ShowToastUtils.showToastMsg(SchoolProjectDetailActivity.this, "请填写培训详情");
                            return;
                        } else {
                            intent.putExtra("projectStatus", status);
                            intent.putExtra("practiceId", practiceId);
                            intent.putExtra("schoolId", schoolId);
                            intent.putExtra("kindpeoNumSet", kindpeoNumSet);
                            intent.setClass(SchoolProjectDetailActivity.this, PowerActivity.class);
                            startActivity(intent);

                        }
                    }
                    finish();
                } else {
                    ShowToastUtils.showToastMsg(SchoolProjectDetailActivity.this, "备忘录");
                }
//                tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("分配详情", R.mipmap.arrow_down)));

                break;

        }
    }
}
