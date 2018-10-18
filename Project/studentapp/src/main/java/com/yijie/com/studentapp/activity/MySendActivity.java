package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.fragment.CheckFragment;
import com.yijie.com.studentapp.fragment.SelectFragment;
import com.yijie.com.studentapp.fragment.UnCheckFragment;
import com.yijie.com.studentapp.fragment.UnSelectFragment;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的投递
 */
public class MySendActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    private UnCheckFragment unCheckFragment;
    private CheckFragment checkFragment;
    private UnSelectFragment unSelectFragment;
    private SelectFragment selectFragment;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mysend);

    }

    @Override
    public void init() {
        title.setText("我的投递");
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("未查看"));
        tabLayout.addTab(tabLayout.newTab().setText("被查看"));
        tabLayout.addTab(tabLayout.newTab().setText("已接受"));
        tabLayout.addTab(tabLayout.newTab().setText("不合适"));
        initUnCheckFragment();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                   initUnCheckFragment();
                } else if (position == 1) {
                    initCheckFragment();
                } else if (position == 2) {
                   initSelectFragment();
                } else if (position == 3) {
                    initUnSelectFragment();
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
    private void initUnCheckFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (unCheckFragment == null) {
            unCheckFragment = new UnCheckFragment();
        }
        transaction.replace(R.id.main_frame_layout, unCheckFragment);
        //提交事务
        transaction.commit();
    }
    private void initCheckFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (checkFragment == null) {
            checkFragment = new CheckFragment();
        }
        transaction.replace(R.id.main_frame_layout, checkFragment);
        //提交事务
        transaction.commit();
    }
    private void initUnSelectFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (unSelectFragment == null) {
            unSelectFragment = new UnSelectFragment();
        }
        transaction.replace(R.id.main_frame_layout, unSelectFragment);
        //提交事务
        transaction.commit();
    }
    private void initSelectFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (selectFragment == null) {
            selectFragment = new SelectFragment();
        }
        transaction.replace(R.id.main_frame_layout, selectFragment);
        //提交事务
        transaction.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
