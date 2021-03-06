package com.yijie.com.kindergartenapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cretin.www.cretinautoupdatelibrary.utils.CretinAutoUpdateUtils;
import com.yijie.com.kindergartenapp.base.AppManager;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.PermissionsActivity;
import com.yijie.com.kindergartenapp.base.PermissionsChecker;
import com.yijie.com.kindergartenapp.fragment.discover.DiscoverFragment;
import com.yijie.com.kindergartenapp.fragment.me.MeFragment;
import com.yijie.com.kindergartenapp.fragment.student.StudentFragment;
import com.yijie.com.kindergartenapp.fragment.yijie.YiJieFragment;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.view.BadgeRadioButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.radio_discover)
    BadgeRadioButton radioDiscover;
    @BindView(R.id.radio_student)
    RadioButton radioStudent;
    @BindView(R.id.radio_yijie)
    RadioButton radioYijie;
    @BindView(R.id.radio_me)
    BadgeRadioButton radioMe;
    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    // 类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList;

    //记录用户首次点击返回键的时间
    private long firstTime = 0;


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void setContentView() {
//        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
//        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//            PermissionsActivity.startActivityForResult(this, 0, PERMISSIONS);
//        }
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        InitViewPager();
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        mainTabRadioGroup.check(R.id.radio_yijie);
//        radioDiscover.setBadgeNumber(2);
        //检查升级
        CretinAutoUpdateUtils.getInstance(this).check();

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





    public void InitViewPager() {
        //会影响沉浸式状态栏如果设置成2
        mainViewPager.setOffscreenPageLimit(3);
        fragmentList = new ArrayList<Fragment>();

        // 将各Fragment加入数组中
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new StudentFragment());
        fragmentList.add(new YiJieFragment());
        fragmentList.add(new MeFragment());

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
                    mainTabRadioGroup.check(R.id.radio_discover);
                    break;
                case 1:
                    mainTabRadioGroup.check(R.id.radio_student);

                    break;
                case 2:
                    mainTabRadioGroup.check(R.id.radio_yijie);

                    break;
                case 3:
                    mainTabRadioGroup.check(R.id.radio_me);

                    break;

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
        int current = 0;
        switch (CheckedId) {


            case R.id.radio_discover:
                current = 0;
                break;
            case R.id.radio_student:
                current = 1;
                break;
            case R.id.radio_yijie:
                current = 2;
                break;
            case R.id.radio_me:
                current = 3;
                break;

        }
        if (mainViewPager.getCurrentItem() != current) {
            mainViewPager.setCurrentItem(current);
        }
    }
}

