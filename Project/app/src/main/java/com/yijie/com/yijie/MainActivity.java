package com.yijie.com.yijie;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.yijie.com.yijie.base.AppManager;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.BaseFragment2;
import com.yijie.com.yijie.base.PermissionsActivity;
import com.yijie.com.yijie.base.PermissionsChecker;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenBaseFragment;
import com.yijie.com.yijie.fragment.mine.MeFragment;
import com.yijie.com.yijie.fragment.school.SchoolBaseFragment;
import com.yijie.com.yijie.fragment.student.StudentFragment;
import com.yijie.com.yijie.fragment.yijie.YiJieFragment;
import com.yijie.com.yijie.home.ViewPagerAdapter;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.StutasToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.radio_school)
    RadioButton radioSchool;
    @BindView(R.id.radio_kindergarten)
    RadioButton radioKindergarten;
    @BindView(R.id.radio_logo)
    RadioButton radioLogo;

    @BindView(R.id.radio_discover)
    RadioButton radioDiscover;
    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;
    private MenuItem menuItem;

    // 类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,

//            Manifest.permission.WAKE_LOCK,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.WRITE_SETTINGS,
//            Manifest.permission.VIBRATE,
//            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
//            Manifest.permission.ACCESS_NETWORK_STATE,

//            Manifest.permission.ACCESS_WIFI_STATE,
////            Manifest.permission.SYSTEM_ALERT_WINDOW,
//            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
////            Manifest.permission.GET_TASKS,
//            Manifest.permission.CHANGE_NETWORK_STATE,
    };


    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    private static final int REQUEST_CODE = 0; // 请求码



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


        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        InitViewPager();
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        mainTabRadioGroup.check(R.id.radio_logo);
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
        mainViewPager.setOffscreenPageLimit(2);
        fragmentList = new ArrayList<Fragment>();

        // 将各Fragment加入数组中
        fragmentList.add(new YiJieFragment());
        fragmentList.add(new KndergartenBaseFragment());
        fragmentList.add(BaseFragment2.newInstance("通讯录"));
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
                    mainTabRadioGroup.check(R.id.radio_logo);
                    break;
                case 1:
                    mainTabRadioGroup.check(R.id.radio_kindergarten);
                    break;
                case 2:
                    mainTabRadioGroup.check(R.id.radio_school);
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

            case R.id.radio_logo:

                current = 0;
                break;
            case R.id.radio_kindergarten:
                current = 1;
                break;
            case R.id.radio_school:
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
