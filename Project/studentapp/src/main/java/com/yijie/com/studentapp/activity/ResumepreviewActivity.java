package com.yijie.com.studentapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.MainActivity;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.CardAdapter;
import com.yijie.com.studentapp.adapter.HonorCardAdapter;
import com.yijie.com.studentapp.adapter.LoadMoreEducationAdapter;
import com.yijie.com.studentapp.adapter.LoadMoreWorkAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.bean.StudentContact;
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.bean.StudentResume;
import com.yijie.com.studentapp.bean.StudentWorkDue;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CommomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/1.
 * 简历预览
 */

public class ResumepreviewActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_personalInformation)
    RelativeLayout rlPersonalInformation;
    @BindView(R.id.iv_stutus)
    ImageView ivStutus;
    @BindView(R.id.recycler_view_info)
    RecyclerView recyclerViewInfo;
    @BindView(R.id.ll_personalInformation)
    LinearLayout llPersonalInformation;
    @BindView(R.id.rl_contactWay)
    RelativeLayout rlContactWay;
    @BindView(R.id.ll_contactWay)
    LinearLayout llContactWay;
    @BindView(R.id.rl_educationBackground)
    RelativeLayout rlEducationBackground;
    @BindView(R.id.ll_educationBackground)
    LinearLayout llEducationBackground;
    @BindView(R.id.rl_workExperience)
    RelativeLayout rlWorkExperience;
    @BindView(R.id.ll_WorkExperience)
    LinearLayout llWorkExperience;
    @BindView(R.id.rl_relatedIntention)
    RelativeLayout rlRelatedIntention;
    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    @BindView(R.id.ll_relatedIntention)
    LinearLayout llRelatedIntention;
    @BindView(R.id.rl_selfAssessment)
    RelativeLayout rlSelfAssessment;
    @BindView(R.id.ll_selfAssessment)
    LinearLayout llSelfAssessment;
    @BindView(R.id.rl_honoraryCcertificate)
    RelativeLayout rlHonoraryCcertificate;
    @BindView(R.id.recycler_view_honor)
    RecyclerView recyclerViewHonor;
    @BindView(R.id.tv_stu_name)
    TextView tvStuName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_national)
    TextView tvNational;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_Idcard)
    TextView tvIdcard;
    @BindView(R.id.tv_cellphone)
    TextView tvCellphone;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_urgentContact)
    TextView tvUrgentContact;
    @BindView(R.id.tv_urgentCellphone)
    TextView tvUrgentCellphone;
    @BindView(R.id.tv_selfAssessment)
    TextView tvSelfAssessment;
    @BindView(R.id.ll_HonoraryCertificate)
    LinearLayout llHonoraryCertificate;
    @BindView(R.id.tv_workplace)
    TextView tvWorkplace;
    @BindView(R.id.recycler_view_education)
    RecyclerView recyclerViewEducation;
    @BindView(R.id.recycler_view_work)
    RecyclerView recyclerViewWork;
    @BindView(R.id.tv_nb)
    TextView tvNb;
    @BindView(R.id.ll_nb)
    LinearLayout llNb;
    private ArrayList<String> infoList = new ArrayList<>();
    private ArrayList<String> honorList = new ArrayList<>();
    private ArrayList<StudentEducation> educationList = new ArrayList<>();
    private ArrayList<StudentWorkDue> workList = new ArrayList<>();
    private String userId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_resumnpreview);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tvRecommend.setText("发送简历");
        title.setText("简历预览");
        LinearLayoutManager infoLinearManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewInfo.setLayoutManager(infoLinearManager);
        LinearLayoutManager honorLinearManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHonor.setLayoutManager(honorLinearManager);
        LinearLayoutManager educatioinLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewEducation.setLayoutManager(educatioinLinearManager);
        recyclerViewEducation.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        LinearLayoutManager workLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewWork.setLayoutManager(workLinearManager);
        recyclerViewWork.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        initDatas();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initDatas() {
        //获取简历详情
        HttpUtils getinstance = HttpUtils.getinstance(this);

        userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
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
                    JSONObject studentContactJson = data.getJSONObject("studentContact");
                    JSONObject studentResumeJson = data.getJSONObject("studentResume");

                    //教育背景
                    JSONArray studentEducationJson = data.getJSONArray("studentEducation");
                    //工作经历
                    JSONArray studentWorkDueJson = data.getJSONArray("studentWorkDue");

                    //个人信息

                    StudentInfo studentInfo = gson.fromJson(studentInfoJson.toString(), StudentInfo.class);
                    //联系人
                    StudentContact studentContact = gson.fromJson(studentContactJson.toString(), StudentContact.class);
                    //简历信息
                    StudentResume studentResume = gson.fromJson(studentResumeJson.toString(), StudentResume.class);
                    if (studentEducationJson.length() > 0) {
                        llEducationBackground.setVisibility(View.VISIBLE);
                        for (int i = 0; i < studentEducationJson.length(); i++) {
                            StudentEducation studentEducation = gson.fromJson(studentEducationJson.getJSONObject(i).toString(), StudentEducation.class);
                            educationList.add(studentEducation);
                        }
                        recyclerViewEducation.setAdapter(new LoadMoreEducationAdapter(educationList));

                    }
                    if (studentWorkDueJson.length() > 0) {
                        llWorkExperience.setVisibility(View.VISIBLE);
                        for (int i = 0; i < studentWorkDueJson.length(); i++) {
                            StudentWorkDue studentWorkDue = gson.fromJson(studentWorkDueJson.getJSONObject(i).toString(), StudentWorkDue.class);
                            workList.add(studentWorkDue);
                        }
                        recyclerViewWork.setAdapter(new LoadMoreWorkAdapter(workList));
                    }
                    if (null != studentInfo) {
                        llPersonalInformation.setVisibility(View.VISIBLE);
                        tvStuName.setText(studentInfo.getStuName());
                        tvAge.setText(studentInfo.getStuAge().toString());
                        tvSex.setText(studentInfo.getSex());
                        tvHeight.setText(studentInfo.getHeight().toString());
                        tvWeight.setText(studentInfo.getWeight().toString());
                        tvNational.setText(studentInfo.getNation());
                        tvPlace.setText(studentInfo.getPlace());
                        tvAdress.setText(studentInfo.getAddress());
                        tvBirth.setText(studentInfo.getBirth());
                        tvIdcard.setText(studentInfo.getIdCard());
                        String img = studentInfo.getPic();
                        if (!"".equals(img) && null != img) {
                            String[] split = img.split(";");
                            List<String> strings = Arrays.asList(split);
                            for (int i = 0; i < strings.size(); i++) {
                                infoList.add(strings.get(i));
                            }
                        }
                        recyclerViewInfo.setAdapter(new CardAdapter(ResumepreviewActivity.this, infoList, userId, "info","info"));
                    }
                    if (null != studentContact) {
                        llContactWay.setVisibility(View.VISIBLE);
                        tvCellphone.setText(studentContact.getCellphone());
                        tvWx.setText(studentContact.getWechat());
                        tvQq.setText(studentContact.getQq());
                        tvMail.setText(studentContact.getEmail());
                        tvUrgentCellphone.setText(studentContact.getUrgentCellphone());
                        tvUrgentContact.setText(studentContact.getUrgentContact());
                    }
                    if (null != studentResume) {
                        if (!TextUtils.isEmpty(studentResume.getCertificate())) {
                            llHonoraryCertificate.setVisibility(View.VISIBLE);
                            String img = studentResume.getCertificate();
                            if (!"".equals(img) && null != img) {
                                String[] split = img.split(";");
                                List<String> strings = Arrays.asList(split);
                                for (int i = 0; i < strings.size(); i++) {
                                    honorList.add(strings.get(i));
                                }
                            }
                            recyclerViewHonor.setAdapter(new HonorCardAdapter(ResumepreviewActivity.this, honorList, userId, "license"));
                        }
                        if (!TextUtils.isEmpty(studentResume.getSelfEvaluate())) {
                            llSelfAssessment.setVisibility(View.VISIBLE);
                            tvSelfAssessment.setText(studentResume.getSelfEvaluate());
                        }
                        if (!TextUtils.isEmpty(studentResume.getExpectWorkPlace()) || !TextUtils.isEmpty(studentResume.getExpectPartener())) {
                            llRelatedIntention.setVisibility(View.VISIBLE);
                            tvWorkplace.setText(studentResume.getExpectWorkPlace());
                            tvCompanionName.setText(studentResume.getExpectPartener());
                        }
                        if (!TextUtils.isEmpty(studentResume.getHobby())) {
                            llNb.setVisibility(View.VISIBLE);
                            tvNb.setText(studentResume.getHobby());
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

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                //发送审核
                HttpUtils getinstance = HttpUtils.getinstance(this);
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("studentUserId", userId);
                stringStringHashMap.put("state", "1");
                stringStringHashMap.put("code", "");
                stringStringHashMap.put("rejectReason", "");
                stringStringHashMap.put("formId", "");
                getinstance.post(Constant.STATEUPDATE, stringStringHashMap, new BaseCallback<String>() {
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
                            if (rescode.equals("200")){
                                final AlertDialog.Builder  normalDialog = new AlertDialog.Builder(ResumepreviewActivity.this);
                                normalDialog.setTitle("发送成功");
                                normalDialog.setMessage("等待审核");
                                normalDialog.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface   dialog, int which)

                                            {
                                                SharedPreferencesUtils.setParam(ResumepreviewActivity.this, "sendCheck", true);
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });

                                normalDialog.show();
                            }else{
                                ShowToastUtils.showToastMsg(ResumepreviewActivity.this,jsonObject.getString("resMessage"));
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
        }
    }
}
