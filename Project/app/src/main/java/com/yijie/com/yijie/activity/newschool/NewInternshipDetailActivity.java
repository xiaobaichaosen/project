package com.yijie.com.yijie.activity.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.to_mode)
    RelativeLayout toMode;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.to_newAppointmenttime)
    RelativeLayout toNewAppointmenttime;
    @BindView(R.id.to_newintershipmoney)
    RelativeLayout toNewintershipmoney;
    private ContactBean contactBean;
    //学历集合
    private ArrayList<String> educationList = new ArrayList<String>();
    //实习月份集合
    private ArrayList<String> monthList = new ArrayList<String>();

    //实习类型集合
    private ArrayList<String> typeList = new ArrayList<String>();

    //实习期限集合
    private ArrayList<String> lineList = new ArrayList<String>();
    private ContactBean modiftyContactBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newinternshipdetail);

    }

    @Override
    public void init() {
        setColor(NewInternshipDetailActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(NewInternshipDetailActivity.this); // 改变状态栏变成透明

        title.setText("实习详情");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        initData();


    }

    private void initData() {
        educationList.add(new String("中专"));
        educationList.add(new String("大专"));
        educationList.add(new String("本科"));

        for (int i = 1; i <= 12; i++) {
            monthList.add(new String(i + "月"));
        }
        typeList.add(new String("定岗实习1"));
        typeList.add(new String("定岗实习2"));
        typeList.add(new String("定岗实习3"));

        lineList.add(new String("3个月"));
        lineList.add(new String("6个月"));
        lineList.add(new String("9个月"));
        lineList.add(new String("1年"));


        modiftyContactBean = (ContactBean) getIntent().getSerializableExtra("internshipModify");
        if (modiftyContactBean != null) {
            tvNewEducation.setText(modiftyContactBean.getSchoolEduction());
            tvNewMonth.setText(modiftyContactBean.getSchoolMonth());
            tvNewType.setText(modiftyContactBean.getSchoolType());
            tvLine.setText(modiftyContactBean.getSchoolLine());
            tvNewAppointmenttime.setText(modiftyContactBean.getSchoolTime());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.to_newEducation, R.id.to_newMonth, R.id.to_newType, R.id.to_line, R.id.to_newAppointmenttime, R.id.tv_recommend, R.id.to_newintershipmoney})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
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
                startActivity(intent);
                break;

            case R.id.to_newAppointmenttime:
                TimePickerView.Type type = TimePickerView.Type.YEAR_MONTH_DAY;
                String format = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(this, type, format, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvNewAppointmenttime.setText(date);
                    }
                });
                break;
            case R.id.tv_recommend:

//                contactBean.setSchoolMode(t.getText().toString().trim());
                ContactBean newContactBean = new ContactBean();
                newContactBean.setSchoolEduction(tvNewEducation.getText().toString().trim());
                newContactBean.setSchoolMonth(tvNewMonth.getText().toString().trim());
                newContactBean.setSchoolType(tvNewType.getText().toString().trim());
                newContactBean.setSchoolLine(tvLine.getText().toString().trim());
                newContactBean.setSchoolTime(tvNewAppointmenttime.getText().toString().trim());
                if (modiftyContactBean != null) {
                    newContactBean.setId(modiftyContactBean.getId());
                    DatabaseAdapter.getIntance(NewInternshipDetailActivity.this).update(newContactBean);
                } else {
                    DatabaseAdapter.getIntance(NewInternshipDetailActivity.this).inserInfo(newContactBean);
                }
                EventBus.getDefault().post("schoolInternshipDetail");
                finish();
                break;

        }

    }
}
