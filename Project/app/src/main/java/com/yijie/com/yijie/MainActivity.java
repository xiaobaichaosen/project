package com.yijie.com.yijie;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yijie.com.yijie.base.AppManager;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.BaseFragment2;
import com.yijie.com.yijie.base.PermissionsActivity;
import com.yijie.com.yijie.base.PermissionsChecker;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenBaseFragment;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenFragment;
import com.yijie.com.yijie.fragment.school.SchoolBaseFragment;
import com.yijie.com.yijie.fragment.school.SchoolFragment;
import com.yijie.com.yijie.fragment.student.StudentFragment;
import com.yijie.com.yijie.home.BottomNavigationViewHelper;
import com.yijie.com.yijie.home.ViewPagerAdapter;
import com.yijie.com.yijie.utils.LightStatusBarUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private MenuItem menuItem;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //记录用户首次点击返回键的时间
    private long firstTime=0;
    private static final int REQUEST_CODE = 0; // 请求码

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SchoolBaseFragment());
        adapter.addFragment(new KndergartenBaseFragment());
        adapter.addFragment(BaseFragment2.newInstance("YIJIE"));
        adapter.addFragment(new StudentFragment());
        adapter.addFragment(BaseFragment2.newInstance("发现"));
        viewPager.setOffscreenPageLimit(14);
        viewPager.setAdapter(adapter);



    }


    @Override
    public void setContentView() {
        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this,0, PERMISSIONS);
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {


        super.onResume();
    }

    @Override
    public void init() {


        setColor(MainActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(MainActivity.this); // 改变状态栏变成透明
//        LightStatusBarUtils.setLightStatusBar(this,true);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_news:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_lib:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_find:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_more:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.item_low:
                                viewPager.setCurrentItem(4);
                                break;

                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);

                }
                menuItem = bottomNavigationView.getMenu().getItem(position);

                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);
        //设置默认选中条目
        viewPager.setCurrentItem(2);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                ShowToastUtils.showToastMsg(MainActivity.this,"再按一次退出程序");
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }else{

        }
    }
}
