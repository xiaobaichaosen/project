package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.LoadMoreCalenderAdapter;
import com.yijie.com.studentapp.adapter.LoadMorePunchAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.fragment.school.StudentBean;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.calender.CalendarView;
import com.yijie.com.studentapp.view.calender.DayManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考勤日历
 */
public class CalenderActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.calendar)
    CalendarView calendar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SimpleDateFormat formatter;
    private Calendar cal;
    private Date curDate;
    private List<StudentBean> dataList = new ArrayList<>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_calender);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("考勤日历");
        cal = Calendar.getInstance();
        initData();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        // 模拟获取数据
        getData();
//        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        LoadMoreCalenderAdapter loadMoreWrapperAdapter = new LoadMoreCalenderAdapter(dataList, R.layout.calender_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);
        calendar.setOnSelectChangeListener(new CalendarView.OnSelectChangeListener() {
            @Override
            public void selectChange(CalendarView calendarView, Date date) {
                SimpleDateFormat   format = new SimpleDateFormat("yyyy年MM月dd日");
                String formats = format.format(date);
                ShowToastUtils.showToastMsg(CalenderActivity.this,formats);
            }
        });
    }
private void initData(){
    DayManager.abnormalDays.clear();
    DayManager.normalDays.clear();
    dataList.clear();

    //考勤异常
    DayManager.addAbnormalDays(3);
    DayManager.addAbnormalDays(10);
    DayManager.addAbnormalDays(17);
    DayManager.addAbnormalDays(24);
    DayManager.addAbnormalDays(28);
    //正常天数
    DayManager.addNomalDays(4);
    DayManager.addNomalDays(5);
    DayManager.addNomalDays(6);
    DayManager.addNomalDays(7);
    DayManager.addNomalDays(11);
    DayManager.addNomalDays(12);
    DayManager.addNomalDays(13);
    DayManager.addNomalDays(14);
    DayManager.addNomalDays(18);
    DayManager.addNomalDays(19);
    DayManager.addNomalDays(20);
    DayManager.addNomalDays(21);
    DayManager.addNomalDays(25);
    DayManager.addNomalDays(26);
    DayManager.addNomalDays(27);

    formatter = new SimpleDateFormat("yyyy年MM月");
    //获取当前时间
    curDate = cal.getTime();
    String str = formatter.format(curDate);
    tvMonth.setText(str);
    String strPre = (cal.get(Calendar.MONTH)) + "月";
    if (strPre.equals("0月")) {
        strPre = "12月";
    }
    tvPre.setText(strPre);
    String strNext = (cal.get(Calendar.MONTH) + 2) + "月";
    if (strNext.equals("13月")) {
        strNext = "1月";
    }
    tvNext.setText(strNext);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 4; i++) {

            dataList.add(new StudentBean(1, String.valueOf(letter)));

            letter++;
        }
    }
    @OnClick({R.id.back, R.id.tv_recommend, R.id.tv_pre, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                break;
            case R.id.tv_pre:
                cal.add(Calendar.MONTH, -1);
                initData();
                calendar.setCalendar(cal);
                break;
            case R.id.tv_next:
                cal.add(Calendar.MONTH, +1);
                initData();
                calendar.setCalendar(cal);
                break;
        }
    }


}
