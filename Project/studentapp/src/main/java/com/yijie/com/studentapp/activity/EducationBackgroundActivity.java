package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/1.
 * 教育背景
 */

public class EducationBackgroundActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_college)
    EditText etCollege;
    @BindView(R.id.et_major)
    EditText etMajor;
    @BindView(R.id.et_education)
    EditText etEducation;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    private StudentEducation studentEducation;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_educationbackground);
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        if (null!=extras){
            studentEducation = (StudentEducation) extras.getSerializable("studentEducation");
            etEducation.setText(studentEducation.getEducation());
            etMajor.setText(studentEducation.getMajor());
            etCollege.setText(studentEducation.getCollege());
            tvStartTime.setText(studentEducation.getStartTime());
            tvEndTime.setText(studentEducation.getGraduateTime());
        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("教育背景");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend,R.id.rl_startTime, R.id.rl_endTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_recommend:
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                HttpUtils getinstance = HttpUtils.getinstance(this);
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                String s = tvStartTime.getText().toString();
                String s1 = tvEndTime.getText().toString();
                if (TextUtils.isEmpty(etCollege.getText().toString())){
                    ShowToastUtils.showToastMsg(this,"请填写学校名称");
                    return;
                }
                if (TextUtils.isEmpty(s)){
                    ShowToastUtils.showToastMsg(this,"请填写入学时间");
                    return;
                }if (TextUtils.isEmpty(s1)){
                    ShowToastUtils.showToastMsg(this,"请填写毕业时间");
                    return;
                }
                HashMap<String, String> requestData = new HashMap<>();
                if (null!=studentEducation){
                    stringStringHashMap.put("id", studentEducation.getId()+"");
                }
                 stringStringHashMap.put("studentUserId", userId);
                 stringStringHashMap.put("resumeId", userId);
                 stringStringHashMap.put("college", etCollege.getText().toString());
                 stringStringHashMap.put("major", etMajor.getText().toString());
                 stringStringHashMap.put("education", etEducation.getText().toString());
                String endTime = s1.replaceAll("/", "-");
                String startTime = s.replaceAll("/", "-");
                stringStringHashMap.put("startTime",startTime );
                stringStringHashMap.put("graduateTime",endTime );
                requestData.put("requestData", new JSONObject(stringStringHashMap).toString());
                getinstance.post(Constant.STUDENTEDUCATION, requestData, new BaseCallback<String>() {
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
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ShowToastUtils.showToastMsg(EducationBackgroundActivity.this, jsonObject.getString("resMessage"));
                            if (jsonObject.getString("rescode").equals("200")) {
                                finish();
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
                break;
            case R.id.rl_startTime:
                TimePickerView.Type yearType = TimePickerView.Type.YEAR_MONTH_DAY_HOUR;
                String yearFormat = "yyyy-MM";
                ViewUtils.alertTimerPicker(this, yearType, yearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvStartTime.setText(date);
                    }
                });
                break;
            case R.id.rl_endTime:
                TimePickerView.Type endyearType = TimePickerView.Type.YEAR_MONTH_DAY_HOUR;
                String endyearFormat = "yyyy-MM";
                ViewUtils.alertTimerPicker(this, endyearType, endyearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvEndTime.setText(date);
                    }
                });
                break;

        }
    }


}

