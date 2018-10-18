package com.yijie.com.kindergartenapp.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.SignActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 统计
 */
public class StatisticalFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_mounth)
    TextView tvMounth;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private DayFragment dayFragment;
    private MouthFragment mounthFragment;
    private int year;
    private int month;   Calendar cal;
    private SimpleDateFormat formatter;
    private Date curDate;
    private int flag=2;
    private void initDate(){
        formatter = new SimpleDateFormat("yyyy-MM");
        //获取当前时间
        curDate = cal.getTime();
        String str = formatter.format(curDate);
        tvMounth.setText(str);
    }
    @Override
    protected void initView() {
        cal = Calendar.getInstance();
        initDate();

        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("日统计"));
        tabLayout.addTab(tabLayout.newTab().setText("月统计"));
        tvDate.setVisibility(View.VISIBLE);
        llRoot.setVisibility(View.GONE);
        initDayFragment();
        //绑定tab点击事件
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    initDayFragment();
                    tvDate.setVisibility(View.VISIBLE);
                    llRoot.setVisibility(View.GONE);
                } else if (position == 1) {
                    tvDate.setVisibility(View.GONE);
                    llRoot.setVisibility(View.VISIBLE);
                    initMonthFragment();
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

    private void initMonthFragment() {

        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = ((SignActivity) mActivity).getSupportFragmentManager().beginTransaction();
        if (mounthFragment == null) {
            mounthFragment = new MouthFragment();
        }
        transaction.replace(R.id.main_frame_layout, mounthFragment);
        //提交事务
        transaction.commit();
    }

    private void initDayFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = ((SignActivity) mActivity).getSupportFragmentManager().beginTransaction();
        if (dayFragment == null) {
            dayFragment = new DayFragment();
        }
        transaction.replace(R.id.main_frame_layout, dayFragment);
        //提交事务
        transaction.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistical;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_date, R.id.tv_left, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                TimePickerView.Type yearType = TimePickerView.Type.YEAR_MONTH_DAY;
                String yearFormat = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(mActivity, yearType, yearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvDate.setText(date);
                    }
                });
                break;
            case R.id.tv_left:
                if (flag>0){
                    --flag;
                    cal.add(Calendar.MONTH, -1);
                    initDate();
                }else {
                    ShowToastUtils.showToastMsg(mActivity,"只能查看近三个月的考勤");
                }
                break;
            case R.id.tv_right:
                if (flag<2){
                    ++flag;
                    cal.add(Calendar.MONTH, +1);
                    initDate();
                }else{
                    ShowToastUtils.showToastMsg(mActivity,"只能查看近三个月的考勤");
                }


                break;
        }
    }
}
