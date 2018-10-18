package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评价
 */
public class AccessActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_access);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor));
        setTranslucent(this);
        title.setText("评价");
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("待评价"));
        tabLayout.addTab(tabLayout.newTab().setText("已评价"));

//        initUnCheckFragment();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
//                    initUnCheckFragment();
                } else if (position == 1) {
//                    initCheckFragment();
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
//    private void initUnCheckFragment() {
//        //开启事务，fragment的控制是由事务来实现的
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (unCheckFragment == null) {
//            unCheckFragment = new UnCheckFragment();
//        }
//        transaction.replace(R.id.main_frame_layout, unCheckFragment);
//        //提交事务
//        transaction.commit();
//    }
//    private void initCheckFragment() {
//        //开启事务，fragment的控制是由事务来实现的
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (checkFragment == null) {
//            checkFragment = new CheckFragment();
//        }
//        transaction.replace(R.id.main_frame_layout, checkFragment);
//        //提交事务
//        transaction.commit();
//    }

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
