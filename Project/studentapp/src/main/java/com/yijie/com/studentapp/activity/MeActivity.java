package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.bean.StudentResume;
import com.yijie.com.studentapp.niorgai.StatusBarCompat;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/30.
 * 我的
 */

public class MeActivity extends BaseActivity {
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @BindView(R.id.collect_position)
    RelativeLayout collectPosition;

    @BindView(R.id.subscribe_position)
    RelativeLayout subscribePosition;
    @BindView(R.id.focus_kendergard)
    RelativeLayout focusKendergard;
    @BindView(R.id.position_consultant)
    TextView positionConsultant;
    @BindView(R.id.to_newAppointmenttime)
    RelativeLayout toNewAppointmenttime;
    @BindView(R.id.help_feedback)
    RelativeLayout helpFeedback;
    @BindView(R.id.settings)
    RelativeLayout settings;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.iv_headImage)
    CircleImageView ivHeadImage;
    @BindView(R.id.tv_stu_name)
    TextView tvStuName;
    @BindView(R.id.tv_status)
    TextView tvStatus;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_me);
    }

    @OnClick({R.id.collect_position, R.id.subscribe_position, R.id.to_newAppointmenttime, R.id.focus_kendergard, R.id.help_feedback, R.id.position_consultant, R.id.settings})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.collect_position:
                intent.setClass(this, PostCollectionActivity.class);
                startActivity(intent);
                break;

            case R.id.subscribe_position:
                ShowToastUtils.showToastMsg(this, "订阅职位");
                break;

            case R.id.focus_kendergard:
                ShowToastUtils.showToastMsg(this, "关注园所");
                break;
            case R.id.position_consultant:
                ShowToastUtils.showToastMsg(this, "我的职业顾问");

                break;
            case R.id.help_feedback:
                ShowToastUtils.showToastMsg(this, "帮助与反馈");
                break;
            case R.id.settings:
                ShowToastUtils.showToastMsg(this, "设置");
                break;
        }
    }

    @Override
    public void init() {
        initDatas();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");


        StatusBarCompat.setStatusBarColorForCollapsingToolbar(this, appbar, collapsingToolbar, toolbar, getResources().getColor(R.color.colorTheme));


        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                rlRoot.setAlpha(1 - Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void initDatas() {

        //获取简历详情
        HttpUtils getinstance = HttpUtils.getinstance(this);
        String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userId);
        getinstance.post(Constant.STUDENTRESUMEDETAIL, stringStringHashMap, new BaseCallback<String>() {
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
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject studentInfoJson = data.getJSONObject("studentInfo");
                    JSONObject studentResumeJson = data.getJSONObject("studentResume");

                    //个人信息
                    StudentInfo studentInfo = gson.fromJson(studentInfoJson.toString(), StudentInfo.class);
                    //简历信息
                    StudentResume studentResume = gson.fromJson(studentResumeJson.toString(), StudentResume.class);
                    if (null!=studentInfo){
                        tvStuName.setText(studentInfo.getStuName());
                        String headPic = studentInfo.getHeadPic();
                        if (headPic.equals(null)||headPic.equals("")) {
                            ivHeadImage.setBackgroundResource(R.mipmap.head);

                        }
                    }

                    if (null != studentResume) {
                        Integer status = studentResume.getStatus();
                        if (status == 0) {
                            tvStatus.setText("未发送审核");
                        } else if (status == 1) {
                            tvStatus.setText("待审核");
                        } else if (status == 2) {
                            tvStatus.setText("通过");
                        }
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
}
