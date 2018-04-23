package com.yijie.com.studentapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yijie.com.studentapp.base.AppManager;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.base.PermissionsActivity;
import com.yijie.com.studentapp.base.PermissionsChecker;
import com.yijie.com.studentapp.fragment.discover.DiscoverFragment;
import com.yijie.com.studentapp.fragment.kndergaren.KndergartenFragment;
import com.yijie.com.studentapp.fragment.school.SchoolFragment;
import com.yijie.com.studentapp.fragment.student.StudentMoreFragment;
import com.yijie.com.studentapp.fragment.student.StudentlBaseFragment;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.BadgeRadioButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.radio_school)
    RadioButton radioSchool;
    @BindView(R.id.radio_kindergarten)
    BadgeRadioButton radioKindergarten;

    @BindView(R.id.radio_student)
    RadioButton radioStudent;
    @BindView(R.id.radio_discover)
    RadioButton radioDiscover;
    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;
    private MenuItem menuItem;
    MyAdapter myAdapter;
    // 类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,



    };
    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    private static final int REQUEST_CODE = 0; // 请求码

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    public void setContentView() {
        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, 0, PERMISSIONS);
        }
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
//        setTranslucent(this); // 改变状态栏变成透明
        InitViewPager();
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        mainTabRadioGroup.check(R.id.radio_student);
        radioKindergarten.setBadgeNumber(2);


//
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ShowToastUtils.showToastMsg(MainActivity.this, "再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                //退出app
                AppManager.getAppManager().AppExit(this);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {

        }
    }



    public void InitViewPager() {
        //会影响沉浸式状态栏如果设置成2
        mainViewPager.setOffscreenPageLimit(5);
        fragmentList = new ArrayList<Fragment>();

        // 将各Fragment加入数组中
//        boolean isStart = (boolean) SharedPreferencesUtils.getParam(this, "isStart", false);
//        if (isStart){
            fragmentList.add(new StudentMoreFragment());
//        }else {
//            fragmentList.add(new StudentlBaseFragment());
//        }

        fragmentList.add(new SchoolFragment());
        fragmentList.add(new KndergartenFragment());
        fragmentList.add(new DiscoverFragment());
        // 设置ViewPager的设配器`

        myAdapter = new MyAdapter(getSupportFragmentManager(),
                fragmentList);
        mainViewPager.setAdapter(myAdapter);
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
                    mainTabRadioGroup.check(R.id.radio_student);
                    break;
                case 1:
                    mainTabRadioGroup.check(R.id.radio_school);

                    break;
                case 2:
                    mainTabRadioGroup.check(R.id.radio_kindergarten);

                    break;
                case 3:
                    mainTabRadioGroup.check(R.id.radio_discover);

                    break;

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
        int current = 0;
        switch (CheckedId) {


            case R.id.radio_student:
                current = 0;
                break;
            case R.id.radio_school:
                current = 1;
                break;
            case R.id.radio_kindergarten:
                current = 2;
                break;
            case R.id.radio_discover:
                current = 3;
                break;

        }
        if (mainViewPager.getCurrentItem() != current) {
            mainViewPager.setCurrentItem(current);
        }
    }
}
