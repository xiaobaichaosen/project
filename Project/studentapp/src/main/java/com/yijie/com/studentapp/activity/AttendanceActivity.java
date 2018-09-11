package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.fragment.ApplyForFragment;
import com.yijie.com.studentapp.fragment.PunchCardFragment;
import com.yijie.com.studentapp.fragment.StatisticsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤打卡，申请，统计
 */
public class AttendanceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;

    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.radio_punchCard)
    RadioButton radioPunchCard;
    @BindView(R.id.radio_applyFor)
    RadioButton radioApplyFor;
    @BindView(R.id.radio_statistics)
    RadioButton radioStatistics;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    // 类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_attendance);
    }

    @Override
    public void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        InitViewPager();
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        mainTabRadioGroup.check(R.id.radio_punchCard);
        title.setText("考勤打卡");
    }

    public void InitViewPager() {
        //会影响沉浸式状态栏如果设置成2
        mainViewPager.setOffscreenPageLimit(3);
        fragmentList = new ArrayList<Fragment>();

        // 将各Fragment加入数组中
        fragmentList.add(new PunchCardFragment());
        fragmentList.add(new ApplyForFragment());
        fragmentList.add(new StatisticsFragment());


        // 设置ViewPager的设配器`
        mainViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),
                fragmentList));
        // 当前为第一个页面

//		main_viewPager.setCurrentItem(1);
        // ViewPager的页面改变监听器
        mainViewPager.setOnPageChangeListener(new MyListner());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.action_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.action_item:
                break;
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    public class MyListner implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            //获取当前页面用于改变对应RadioButton的状态
            int current = mainViewPager.getCurrentItem();
            switch (current) {
                case 0:
                    mainTabRadioGroup.check(R.id.radio_punchCard);
                    title.setText("打卡");
                    break;
                case 1:
                    mainTabRadioGroup.check(R.id.radio_applyFor);
                    title.setText("申请");
                    break;
                case 2:
                    mainTabRadioGroup.check(R.id.radio_statistics);
                    title.setText("统计");
                    break;

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
        int current = 0;
        switch (CheckedId) {


            case R.id.radio_punchCard:
                current = 0;
                title.setText("考勤打卡");
                break;
            case R.id.radio_applyFor:
                title.setText("申请");
                current = 1;
                break;
            case R.id.radio_statistics:
                current = 2;
                title.setText("统计");
                break;


        }
        if (mainViewPager.getCurrentItem() != current) {
            mainViewPager.setCurrentItem(current);
        }
    }
}
