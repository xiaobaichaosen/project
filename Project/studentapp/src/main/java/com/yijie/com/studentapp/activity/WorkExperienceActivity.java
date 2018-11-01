package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.bean.StudentWorkDue;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.TimeUtils;
import com.yijie.com.studentapp.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/1.
 */

public class WorkExperienceActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;
    @BindView(R.id.rl_startTime)
    RelativeLayout rlStartTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.rl_endTime)
    RelativeLayout rlEndTime;
    @BindView(R.id.et_post)
    EditText etPost;
    @BindView(R.id.et_postContent)
    EditText etPostContent;
    private StudentWorkDue studentWorkDue;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_workexperience);
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        if (null!=extras){
            studentWorkDue = (StudentWorkDue) extras.getSerializable("studentWorkDue");
            etName.setText(studentWorkDue.getCompanyName());
            String workDue = studentWorkDue.getWorkDue();
            tvStartTime.setText(workDue.split("-")[0]);
            tvEndTime.setText(workDue.split("-")[1]);
            etPost.setText(studentWorkDue.getPost());
            etPostContent.setText(studentWorkDue.getDescription());
        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("工作经历");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.rl_startTime, R.id.rl_endTime})
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
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请填写公司名称");
                    return;
                }
                if (TextUtils.isEmpty(s)) {
                    ShowToastUtils.showToastMsg(this, "请填写开始时间");
                    return;
                }
                if (TextUtils.isEmpty(s1)) {
                    ShowToastUtils.showToastMsg(this, "请填写结束时间");
                    return;
                }
                if (TextUtils.isEmpty(etPostContent.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请填写工作描述");
                    return;
                }

                String endTime = s1.replaceAll("/", "-");
                String startTime = s.replaceAll("/", "-");
                if (TimeUtils.compare_date("yyyy-MM",startTime,endTime)==1){
                    ShowToastUtils.showToastMsg(WorkExperienceActivity.this,"结束不能早于开始时间");
                    return;
                }
                HashMap<String, String> requestData = new HashMap<>();
                if (null!=studentWorkDue){
                    stringStringHashMap.put("id", studentWorkDue.getId() + "");
                }
                stringStringHashMap.put("studentUserId", userId);
                stringStringHashMap.put("resumeId", userId);
                stringStringHashMap.put("companyName", etName.getText().toString());


                stringStringHashMap.put("workDue",startTime+"-"+endTime);
                stringStringHashMap.put("post", etPost.getText().toString());
                stringStringHashMap.put("description", etPostContent.getText().toString());
                requestData.put("requestData", new JSONObject(stringStringHashMap).toString());
                getinstance.post(Constant.STUDENTWORKDUE, requestData, new BaseCallback<String>() {
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
                            ShowToastUtils.showToastMsg(WorkExperienceActivity.this, jsonObject.getString("resMessage"));
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


