package com.yijie.com.yijie.activity.school;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenBaseFragment;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenFragment;
import com.yijie.com.yijie.fragment.school.ProjectListFragment;
import com.yijie.com.yijie.fragment.school.SchoolBaseFragment;
import com.yijie.com.yijie.fragment.student.StudentFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 奕杰平台 on 2018/4/19.
 * 包含学校，园所，学生fragment得页面
 */

public class SchoolMoreActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.radio_school)
    RadioButton radioSchool;
    @BindView(R.id.radio_kindergarten)
    RadioButton radioKindergarten;

    @BindView(R.id.radio_student)
    RadioButton radioStudent;

    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;
    private MenuItem menuItem;
    // 类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_schoolmore);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        InitViewPager();
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        int page = getIntent().getExtras().getInt("page");
        if (page==0){
            mainTabRadioGroup.check(R.id.radio_school);
        }else if(page==1){
            mainTabRadioGroup.check(R.id.radio_kindergarten);
        }else if(page==2){
            mainTabRadioGroup.check(R.id.radio_student);
        }else{
            mainTabRadioGroup.check(R.id.radio_school);
        }

    }
    public void InitViewPager() {
        mainViewPager.setOffscreenPageLimit(0);
        fragmentList = new ArrayList<Fragment>();

        // 将各Fragment加入数组中
//        fragmentList.add(new SchoolBaseFragment());
//        fragmentList.add(new KndergartenBaseFragment());
        fragmentList.add(new ProjectListFragment());
        fragmentList.add(new KndergartenFragment());
        fragmentList.add(new StudentFragment());
        // 设置ViewPager的设配器`
        mainViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),
                fragmentList));
        // 当前为第一个页面

//		main_viewPager.setCurrentItem(1);
        // ViewPager的页面改变监听器
        mainViewPager.setOnPageChangeListener(new MyListner());
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
                    mainTabRadioGroup.check(R.id.radio_school);
                    break;
                case 1:
                    mainTabRadioGroup.check(R.id.radio_kindergarten);
                    break;
                case 2:
                    mainTabRadioGroup.check(R.id.radio_student);
                    break;

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
        int current = 0;
        switch (CheckedId) {

            case R.id.radio_school:

                current = 0;
                break;
            case R.id.radio_kindergarten:
                current = 1;
                break;
            case R.id.radio_student:
                current = 2;
                break;

        }
        if (mainViewPager.getCurrentItem() != current) {
            mainViewPager.setCurrentItem(current);
        }
    }
}
