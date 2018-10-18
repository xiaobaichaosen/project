package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.LoadMoreCalenderAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentSignIn;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.ViewUtils;
import com.yijie.com.studentapp.view.CircleImageView;
import com.yijie.com.studentapp.view.schoolcalender.CalendarView;
import com.yijie.com.studentapp.view.schoolcalender.DayManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 学校考勤日历
 */
public class SchoolCalenderActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.tv_month)
    TextView tvMonth;

    @BindView(R.id.calendar)
    CalendarView calendar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_stuName)
    TextView tvStuName;

    @BindView(R.id.tv_kindName)
    TextView tvKindName;
    @BindView(R.id.tv_stuNumber)
    TextView tvStuNumber;
    @BindView(R.id.tv_signTimes)
    TextView tvSignTimes;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    private SimpleDateFormat formatter;
    private Calendar cal;
    private Date curDate;
    private List<StudentSignIn> dataList = new ArrayList<>();
    private String userIdString;
    private LoadMoreCalenderAdapter loadMoreWrapperAdapter;
    private StatusLayoutManager statusLayoutManager;
    //放最近6个月的日期
    private ArrayList<String> monthList = new ArrayList<String>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_schoolcalender);
    }

    //当前年月
    private String yearMouth;

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(llRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
//                        getProjecDetil(schoolPracticeId);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
//                        getProjecDetil(schoolPracticeId);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        userIdString = (String) SharedPreferencesUtils.getParam(this, "id", "");
        title.setText("考勤日历");
        cal = Calendar.getInstance();
        String signDay = getIntent().getStringExtra("signDay");
        String stuName = getIntent().getStringExtra("stuName");
        String kindName = getIntent().getStringExtra("kindName");
        String headPic = getIntent().getStringExtra("headPic");
        tvStuName.setText(stuName);
        tvKindName.setText(kindName);
        if (headPic==null||headPic.equals("")) {
            ivHead.setBackgroundResource(R.mipmap.head);

        }else {
            ImageLoaderUtil.getImageLoader(SchoolCalenderActivity.this).displayImage(Constant.infoUrl+userIdString+"/head_pic_/"+headPic, ivHead, ImageLoaderUtil.getPhotoImageOption());
        }
        //获取当前时间
        yearMouth = signDay.substring(0, 7);
        //================================================获取当前月份的前6个月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        monthList.add(yearMouth);
        for (int i = 1; i < 7; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MONTH, -i);
            Date m = c.getTime();
            String format = sdf.format(m);
            monthList.add(format);
        }
        //================================================获取当前月份的前6个月
        tvMonth.setText(yearMouth);
        initData(yearMouth);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setNestedScrollingEnabled(false);
        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        loadMoreWrapperAdapter = new LoadMoreCalenderAdapter(dataList, R.layout.calender_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);
        //获取传过来的日期考勤情况
        getData(signDay);
        calendar.setOnSelectChangeListener(new CalendarView.OnSelectChangeListener() {
            @Override
            public void selectChange(CalendarView calendarView, Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String signDay = format.format(date);
                dataList.clear();
                getData(signDay);
            }
        });
    }

    private void initData(String currentDate) {
        DayManager.normalDays.clear();
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userIdString);
        stringStringHashMap.put("signMonth", currentDate);
        getinstance.post(Constant.SELECTCURRMONTHSIGN, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                commonDialog.dismiss();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                StudentSignIn studentSignIn = gson.fromJson(data.getJSONObject(i).toString(), StudentSignIn.class);
                                String signin = studentSignIn.getSigninDate();
                                String substring = signin.substring(8, signin.length());
                                //正常天数
                                DayManager.addNomalDays(Integer.parseInt(substring));

                            }
                        }
                        calendar.setCalendar(cal);

                    } else {
                        ShowToastUtils.showToastMsg(SchoolCalenderActivity.this, jsonObject.getString("resMessage"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 根据日历点击日期查询考勤情况
     *
     * @param signDateDay
     */
    private void getData(final String signDateDay) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userIdString);
        stringStringHashMap.put("signCurrDay", signDateDay);
        getinstance.post(Constant.SELECTCURRDAYSIGN, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                commonDialog.dismiss();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                StudentSignIn studentSignIn = gson.fromJson(data.getJSONObject(i).toString(), StudentSignIn.class);
                                dataList.add(studentSignIn);
                                if (i == 0) {
                                    StudentSignIn studen = gson.fromJson(data.getJSONObject(i).toString(), StudentSignIn.class);
                                    tvSignTimes.setText("今日签到" + studen.getSignInTimes() + "次");
                                }
                                loadMoreWrapperAdapter.notifyDataSetChanged();

                            }
                            //设置上个页面传过来的日期选中
                            DayManager.setSelect(Integer.parseInt(signDateDay.substring(8, signDateDay.length())));
                        } else {
                            tvSignTimes.setText("今日未签到");
                        }
                        statusLayoutManager.showSuccessLayout();
                    } else {
                        statusLayoutManager.showEmptyLayout();
                        ShowToastUtils.showToastMsg(SchoolCalenderActivity.this, "当天没有考勤");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });

    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.tv_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                break;
            case R.id.tv_month:
                ViewUtils.alertBottomWheelOption(SchoolCalenderActivity.this, monthList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {

                        try {
                            tvMonth.setText(monthList.get(postion));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                            String str1 = yearMouth;
                            String str2 = monthList.get(postion);
                            Calendar bef = Calendar.getInstance();
                            Calendar aft = Calendar.getInstance();
                            bef.setTime(sdf.parse(str1));
                            aft.setTime(sdf.parse(str2));
                            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
                            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
                            LogUtil.e("相差几个月=========" + Math.abs(month + result));
                            if (Math.abs(month + result) == 0) {
                                cal = Calendar.getInstance();
                            } else {
                                //初始化日历类
                                cal = Calendar.getInstance();
                                cal.add(Calendar.MONTH, -Math.abs(month + result));
                            }
                            initData(monthList.get(postion));
//                            calendar.setCalendar(cal);
                            //获取选中的月份整体出勤情况
                            calendar.invalidate();
                            //TODO 日期背景没有重绘
                            //获取每个月一号的打卡情况
                            getData(monthList.get(postion) + "-01");
                            //默认选中每个月的1号
                            DayManager.setSelect(01);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                });

                break;

        }
    }


}
