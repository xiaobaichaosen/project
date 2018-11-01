package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.fragment.ProjectDetailFragment;
import com.yijie.com.yijie.fragment.ProjectMemeryFragment;
import com.yijie.com.yijie.fragment.ProjectSchoolDetailFragment;
import com.yijie.com.yijie.util.ViewUtils;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/5/16.
 * 项目详情+备忘录
 * 实习项目进去的
 */

public class ProjectDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.iv_see)
    ImageView ivSee;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.iv_schoolPic)
    CircleImageView ivSchoolPic;
    @BindView(R.id.tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_schoolName_edit)
    TextView tvSchoolNameEdit;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;


    //备忘录碎片
    private ProjectMemeryFragment memeryFragment;

    //用来判断   actionItem是哪个图片，来触发点击事件 1==项目，2==备忘录
    private int current;

    //是编辑还是完成
    public boolean changeText;
    //学校详情中联系人的编辑按钮
    TextView tvContactEdit;
    //项目详情碎片
    private ProjectDetailFragment projectDetailFragment;
    //学校id
    public String practiceId;
    //是否显示确认接此项目
    public boolean isShowLayout;
    public String projectName;
    //学校名称
    public String schoolName;
    private String projectStatus;
    public String schoolId;
    public String kindpeoNumSet;
    //学校详情碎片
    private ProjectSchoolDetailFragment projectSchoolFragment;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_temp);
    }

    @Override
    public void init() {
        projectName = getIntent().getStringExtra("projectName");
        practiceId = getIntent().getStringExtra("practiceId");
        schoolId = getIntent().getStringExtra("schoolId");
        schoolName = getIntent().getStringExtra("schoolName");
        projectStatus = getIntent().getStringExtra("status");
        isShowLayout = getIntent().getBooleanExtra("isShowLayout", false);
        String telephone = getIntent().getStringExtra("zjNumber");
//        if (isShowLayout) {
//            //如果是待培训状态，说明没有接收项目隐藏设置按钮
//            actionItem.setVisibility(View.GONE);
//        } else {
//            actionItem.setVisibility(View.VISIBLE);
//            actionItem.setBackgroundResource(R.mipmap.setting);
//        }
        title.setText(projectName);
        title.setTextSize(ViewUtils.dp2px(this, 12));
        String schoolContact = getIntent().getStringExtra("schoolContact");
        String schoolContactPhone = getIntent().getStringExtra("schoolContactPhone");
        String logoPath = getIntent().getStringExtra("logo");
        tvSchoolName.setText(schoolName);
        if (TextUtils.isEmpty(schoolContactPhone)) {
            tvContact.setText(schoolContact + " " + telephone);
        } else {
            tvContact.setText(schoolContact + " " + schoolContactPhone);
        }
        if (null == logoPath || "" == logoPath) {
            ivSchoolPic.setBackgroundResource(R.mipmap.logo);
        } else {
            //加载网络图片
            String[] split = logoPath.split(";");
            ImageLoaderUtil.getImageLoader(this).displayImage(Constant.getheadUrl + schoolId + "/head_pic_/" + split[0], ivSchoolPic, ImageLoaderUtil.getPhotoImageOption());
        }


//        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
//        setTranslucent(this); // 改变状态栏变成透明


        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("学校信息"));
        tabLayout.addTab(tabLayout.newTab().setText("项目详情"));
        tabLayout.addTab(tabLayout.newTab().setText("备忘录"));
        tabLayout.getTabAt(0).select();
        initSchoolDetailFragmentFragment();
        //绑定tab点击事件
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    current = 0;
                    initSchoolDetailFragmentFragment();
                    tvRecommend.setVisibility(View.GONE);
                } else if (position == 1) {
                    current = 1;
                    initProjectDetailFragment();
                    tvRecommend.setVisibility(View.GONE);
                    if (isShowLayout) {
                        //如果是待培训状态，说明没有接收项目隐藏设置按钮
                        actionItem.setVisibility(View.GONE);
                    } else {
                        actionItem.setVisibility(View.VISIBLE);
                        actionItem.setBackgroundResource(R.mipmap.setting);
                    }

                } else if (position == 2) {
                    current = 2;
                    initMemeryFragment();
                    appBarLayout.setExpanded(false);
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

    private void initSchoolDetailFragmentFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (projectSchoolFragment == null) {
            projectSchoolFragment = new ProjectSchoolDetailFragment();
        }
        transaction.replace(R.id.main_frame_layout, projectSchoolFragment);
        //提交事务
        transaction.commit();
    }

    private void initProjectDetailFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (projectDetailFragment == null) {
            projectDetailFragment = new ProjectDetailFragment();
        }
        transaction.replace(R.id.main_frame_layout, projectDetailFragment);
        //提交事务
        transaction.commit();


    }


    private void initMemeryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (memeryFragment == null) {
            memeryFragment = new ProjectMemeryFragment();
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
//                tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("分配详情", R.mipmap.arrow_down)));
                String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");
                if (projectDetailFragment.schoolSalaryInfo == null) {
                    ShowToastUtils.showToastMsg(ProjectDetailActivity.this, "请填写实习薪资");
                    return;
                    //开发老师
                } else if (projectDetailFragment.schoolTrainDetail.getSchoolContactName() == null) {
                    ShowToastUtils.showToastMsg(ProjectDetailActivity.this, "请填写培训联络人");
                    return;
                }
                if (roleName.contains("开发老师")) {
                    intent.putExtra("practiceId", practiceId);
                    intent.putExtra("projectStatus", projectStatus);
                    intent.putExtra("schoolId", schoolId);
                    intent.setClass(ProjectDetailActivity.this, PowerActivity.class);
                    startActivity(intent);

                } else {
                    if (projectDetailFragment.schoolTrainDetail.getToBeijingTime() == null) {
                        ShowToastUtils.showToastMsg(ProjectDetailActivity.this, "请填写培训详情");
                        return;
                    } else {
                        intent.putExtra("projectStatus", projectStatus);
                        intent.putExtra("practiceId", practiceId);
                        intent.putExtra("schoolId", schoolId);
                        intent.putExtra("kindpeoNumSet", kindpeoNumSet);
                        intent.setClass(ProjectDetailActivity.this, PowerActivity.class);
                        startActivity(intent);

                    }
                }
                finish();
                break;

        }
    }
}
