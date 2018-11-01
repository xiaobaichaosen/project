package com.yijie.com.yijie.activity.newschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ContactModityActivity;
import com.yijie.com.yijie.adapter.ContactRecycleViewModityAapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.IntershipMoney;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建实习详细
 */

public class NewInternshipDetailActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_newEducation)
    TextView tvNewEducation;
    @BindView(R.id.to_newEducation)
    RelativeLayout toNewEducation;
    @BindView(R.id.tv_newMonth)
    TextView tvNewMonth;
    @BindView(R.id.to_newMonth)
    RelativeLayout toNewMonth;
    @BindView(R.id.tv_newType)
    TextView tvNewType;
    @BindView(R.id.to_newType)
    RelativeLayout toNewType;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.to_line)
    RelativeLayout toLine;

    @BindView(R.id.to_mode)
    RelativeLayout toMode;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.to_newAppointmenttime)
    RelativeLayout toNewAppointmenttime;
    @BindView(R.id.to_newintershipmoney)
    RelativeLayout toNewintershipmoney;
    @BindView(R.id.tv_newintershipmoney)
    TextView tvNewintershipmoney;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.to_year)
    RelativeLayout toYear;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_manageMoney)
    TextView tvManageMoney;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    //用于保存实习薪资的bean
    private IntershipMoney intershipMoney;
    //学历集合
    private ArrayList<String> educationList = new ArrayList<String>();
    //实习月份集合
    private ArrayList<String> monthList = new ArrayList<String>();

    //实习类型集合
    private ArrayList<String> typeList = new ArrayList<String>();

    //实习期限集合
    private ArrayList<String> lineList = new ArrayList<String>();
    //管理模式集合
    private ArrayList<String> modeList = new ArrayList<String>();
    private ContactBean modiftyContactBean;
    //从网络获取得实习薪资
    SchoolSalaryInfo fromNetSchoolPractice;
    //从网络获取到的实习详情
    ArrayList<SchoolPractice> schoolPractices = null;
    private String schoolId;
    int userId = 0;
    //保存按钮如果是true的话，提交数据到数据库，如果是false，是从新建学校传递过来的，直接返回
//    boolean isFromNet;
    Integer schoolPracitceId=0;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newinternshipdetail);

    }

    @Override
    public void init() {
        schoolId = getIntent().getStringExtra("schoolId");
        SchoolPractice tempSchoolPractice= (SchoolPractice) getIntent().getExtras().getSerializable("tempSchoolPractice");
        //回显数据
        if (tempSchoolPractice!=null) {
            schoolPracitceId = tempSchoolPractice.getId();
            tvYear.setText(tempSchoolPractice.getYear());
            tvNewEducation.setText(tempSchoolPractice.getEducation());
            tvLine.setText(tempSchoolPractice.getTimelimit());
            tvNewMonth.setText(tempSchoolPractice.getPracticeMonth());
            tvMode.setText(tempSchoolPractice.getManageModel());
            tvNewType.setText(tempSchoolPractice.getPracticeType());
            tvNewAppointmenttime.setText(tempSchoolPractice.getOrderTime());
        }

        setColor(NewInternshipDetailActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(NewInternshipDetailActivity.this); // 改变状态栏变成透明
        title.setText("实习详情");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        initData();
        String json= (String) SharedPreferencesUtils.getParam(this,"user","");

        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initData() {
        educationList.add(new String("中专"));
        educationList.add(new String("大专"));
        educationList.add(new String("本科"));
        modeList.add("学校统一管理");
        modeList.add("学生自主实习");
        modeList.add("学校管理+自主实习");


        for (int i = 1; i <= 12; i++) {
            monthList.add(new String(i + "月份"));
        }
        typeList.add(new String("顶岗实习"));
        typeList.add(new String("教学实习"));
        typeList.add(new String("自主实习"));
        typeList.add(new String("教学实习+自主实习"));
        for (int i = 1; i <= 12; i++) {
            lineList.add(new String(i + "个月"));
        }


        /**
         * 从新建学校得页面跳过来回显数据
         */
        modiftyContactBean = (ContactBean) getIntent().getSerializableExtra("internshipModify");
        intershipMoney = (IntershipMoney) getIntent().getSerializableExtra("internshipMoney");
        if (modiftyContactBean != null) {
            tvYear.setText(modiftyContactBean.getSchoolSample());
            tvNewEducation.setText(modiftyContactBean.getSchoolEduction());
            tvNewMonth.setText(modiftyContactBean.getSchoolMonth());
            tvNewType.setText(modiftyContactBean.getSchoolType());
            tvLine.setText(modiftyContactBean.getSchoolLine());
            tvNewAppointmenttime.setText(modiftyContactBean.getSchoolTime());
            tvMode.setText(modiftyContactBean.getSchoolMode());
        }
        if (intershipMoney != null) {
            tvNewintershipmoney.setText("已填写");
        }
        /**
         * 此为从数据库获取得数据去回显
         */

//        isFromNet = getIntent().getExtras().getBoolean("isFromNet", false);

        schoolPractices = (ArrayList<SchoolPractice>) getIntent().getSerializableExtra("fromNetSchoolPractice");
        ArrayList<SchoolSalaryInfo> schoolSalaryInfo =  (ArrayList<SchoolSalaryInfo>) getIntent().getSerializableExtra("schoolSalaryInfo");

        if (schoolPractices!=null&&schoolSalaryInfo!=null){
            tvYear.setText(schoolPractices.get(0).getYear());
            tvNewEducation.setText(schoolPractices.get(0).getEducation());
            tvNewMonth.setText(schoolPractices.get(0).getYear());
            tvNewType.setText(schoolPractices.get(0).getPracticeType());
            tvLine.setText(schoolPractices.get(0).getTimelimit());
            tvNewAppointmenttime.setText(schoolPractices.get(0).getOrderTime()+"");
            tvMode.setText(schoolPractices.get(0).getManageModel());
            tvNewintershipmoney.setText("已填写");
            fromNetSchoolPractice = schoolSalaryInfo.get(0);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(IntershipMoney event) {
        if (event != null) {
            tvNewintershipmoney.setText("已填写");
            llPay.setVisibility(View.VISIBLE);
            tvDate.setText(event.getStartDate()+"-"+event.getEndDate());
            tvMoney.setText(event.getPay());
            tvManageMoney.setText(event.getCountMoney());
            intershipMoney = event;
            fromNetSchoolPractice=null;
        }
    }

    @OnClick({R.id.back, R.id.to_newEducation, R.id.to_newMonth, R.id.to_newType, R.id.to_line, R.id.to_newAppointmenttime, R.id.tv_recommend, R.id.to_newintershipmoney, R.id.to_mode, R.id.to_year})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.to_mode:
                ViewUtils.alertBottomWheelOption(NewInternshipDetailActivity.this, modeList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvMode.setText(modeList.get(postion));
                    }

                });
                break;
            case R.id.to_newEducation:
                ViewUtils.alertBottomWheelOption(NewInternshipDetailActivity.this, educationList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvNewEducation.setText(educationList.get(postion));
                    }

                });
                break;
            case R.id.to_newMonth:
                ViewUtils.alertBottomWheelOption(NewInternshipDetailActivity.this, monthList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvNewMonth.setText(monthList.get(postion));
                    }

                });
                break;
            case R.id.to_year:
                TimePickerView.Type yeartype = TimePickerView.Type.YEAR;
                String format = "yyyy";
                ViewUtils.alertTimerPicker(this, yeartype, format, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        if (TimeUtil.compare_date("yyyy",date,TimeUtil.getCurrentDate("yyyy"))==1){
                            tvYear.setText(date);
                        }else {
                            ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"选择的当前日期之前的日期");
                        }

                    }
                });
                break;

            case R.id.to_newType:
                ViewUtils.alertBottomWheelOption(NewInternshipDetailActivity.this, typeList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvNewType.setText(typeList.get(postion));
                    }

                });
                break;

            case R.id.to_line:


                ViewUtils.alertBottomWheelOption(NewInternshipDetailActivity.this, lineList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvLine.setText(lineList.get(postion));
                    }

                });
                break;
            case R.id.to_newintershipmoney:

                intent.setClass(this, NewIntershipMoney.class);
                Bundle mBundle = new Bundle();
                intent.putExtra("isFromNet",true);
                //如果是网络来得实习薪资传递
                 if(schoolPractices!=null){
                     mBundle.putSerializable("fromNetSchoolPractice", schoolPractices.get(0));
                 }
                 //如果是新建学校过来得薪资传递
                if(intershipMoney!=null){
                    mBundle.putSerializable("IntershipModify", intershipMoney);
                }
                intent.putExtras(mBundle);
                startActivity(intent);
                break;

            case R.id.to_newAppointmenttime:
                TimePickerView.Type yearType = TimePickerView.Type.YEAR_MPMTH_DAY_H;
                String yearFormat = "yyyy-MM-dd HH";
                ViewUtils.alertTimerPicker(this, yearType, yearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
//                        if (TimeUtil.compare_date("yyyy-MM-dd HH:mm",date,TimeUtil.getCurrentDate("yyyy-MM-dd HH:mm"))==1){
                            tvNewAppointmenttime.setText(date);
//                        }else {
//                            ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"选择的当前日期之前的日期");
//                        }

                    }
                });
                break;
            case R.id.tv_recommend:
                String educationString = tvNewEducation.getText().toString().trim();
                String mounthString = tvNewMonth.getText().toString().trim();
                String yearString = tvYear.getText().toString().trim();
                String typeString = tvNewType.getText().toString().trim();
                String lineString = tvLine.getText().toString().trim();
                if (educationString.equals("")){
                    ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"请填写学历");
                    return;
                }else  if (mounthString.equals("")){
                    ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"请填写实习月份");
                    return;
                }else  if (yearString.equals("")){
                    ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"请填写实习年份");
                    return;
                }else  if (typeString.equals("")){
                    ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"请填写实习类型");
                    return;
                }else  if (lineString.equals("")){
                    ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,"请填写实习期限");
                    return;
                }
                SchoolPractice schoolPractice = new SchoolPractice();
                if(schoolPracitceId!=0){
                    schoolPractice.setId(schoolPracitceId);
                }
                schoolPractice.setSchoolId(Integer.parseInt(schoolId));
//                schoolPractice.setUserId(userId);
                schoolPractice.setStatus(0);
                schoolPractice.setYear(yearString);
                schoolPractice.setEducation(educationString);
                schoolPractice.setTimelimit(lineString);
                schoolPractice.setPracticeMonth(mounthString);
                schoolPractice.setManageModel(tvMode.getText().toString().trim());
                schoolPractice.setPracticeType(typeString);
                String orderTime = tvNewAppointmenttime.getText().toString().trim();
                schoolPractice.setOrderTime(orderTime);
                schoolPractice.setProjectName(yearString+" "+educationString+" "+typeString+" "+mounthString+"到京");
//              //提交修改数据到数据库
                 updateShipDetail(schoolPractice);

                break;

        }

    }
    //新建实习详情
    private void updateShipDetail(SchoolPractice schoolPractice) {
        HttpUtils getinstance = HttpUtils.getinstance(NewInternshipDetailActivity.this);
        getinstance.postJson(Constant.NEWSHIPDETAIL, schoolPractice, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
                commonDialog.setTitle("数据提交中...");
            }
            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String shipDetail) {
                LogUtil.e(shipDetail);
               //将数据保存到上一个页面
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(shipDetail);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")||rescode.equals("300")){
                        JSONObject data = jsonObject.getJSONObject("data");
                        Gson gson=new Gson();
                        SchoolPractice schoolPractice1= gson.fromJson(data.toString(), SchoolPractice.class);
                        ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,jsonObject.getString("resMessage"));
                        EventBus.getDefault().post(schoolPractice1);
                        finish();
                    }else if(rescode.equals("400")){
                        ShowToastUtils.showToastMsg(NewInternshipDetailActivity.this,jsonObject.getString("resMessage"));
                    }
                    commonDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }
}
