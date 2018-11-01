package com.yijie.com.kindergartenapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.InfoCardAdapter;
import com.yijie.com.kindergartenapp.adapter.LoadMoreEducationAdapter;
import com.yijie.com.kindergartenapp.adapter.LoadMoreWorkAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.bean.StudentEducation;
import com.yijie.com.kindergartenapp.bean.StudentInfo;
import com.yijie.com.kindergartenapp.bean.StudentResume;
import com.yijie.com.kindergartenapp.bean.StudentResumeDetail;
import com.yijie.com.kindergartenapp.bean.StudentWorkDue;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;
import com.yijie.com.kindergartenapp.view.CommomDialog;
import com.yijie.com.kindergartenapp.view.LoadingLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/26.
 * 同伴简历
 */

public class CompanyResumnActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_stuName)
    TextView tvStuName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_birth)
    TextView tvBirth;


    @BindView(R.id.education_recyclerView)
    RecyclerView educationRecyclerView;
    @BindView(R.id.work_recyclerView)
    RecyclerView workRecyclerView;
    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    @BindView(R.id.anchor_bodyContainer)
    NestedScrollView anchorBodyContainer;
    @BindView(R.id.ll_commit)
    LinearLayout llCommit;
    @BindView(R.id.tv_expectWorkPlace)
    TextView tvExpectWorkPlace;
    @BindView(R.id.tv_selfEvaluate)
    TextView tvSelfEvaluate;
    //同伴的名字
    String expectPartener;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_location)
    TextView tvLocation;

    int studentUserId;
    int kinderNeedId;
    int companionId;
    String stuName;
    @BindView(R.id.loading)
    LoadingLayout loading;
    @BindView(R.id.ll_workExperience)
    LinearLayout llWorkExperience;
    @BindView(R.id.ll_relatedIntention)
    LinearLayout llRelatedIntention;
    @BindView(R.id.ll_selfAssessment)
    LinearLayout llSelfAssessment;
    @BindView(R.id.tv_nb)
    TextView tvNb;
    @BindView(R.id.ll_nb)
    LinearLayout llNb;
    @BindView(R.id.recycler_view_honor)
    RecyclerView recyclerViewHonor;
    @BindView(R.id.ll_honoraryCcertificate)
    LinearLayout llHonoraryCcertificate;
    private ArrayList<String> infoList = new ArrayList<>();
    private ArrayList<String> honorList = new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_studentresumn);
    }

    @Override
    public void init() {
        studentUserId = getIntent().getExtras().getInt("companionId");
        kinderNeedId = getIntent().getExtras().getInt("kinderNeedId");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("简历");
        getResumnDetail(studentUserId);
    }

    /**
     * 查询简历
     *
     * @param id
     */
    private void getResumnDetail(final int id) {
        final HttpUtils instance = HttpUtils.getinstance(CompanyResumnActivity.this);
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(CompanyResumnActivity.this);
        Map map = new HashMap();
        map.put("studentUserId", id + "");

        instance.post(Constant.SELECTBYSTUDENTUSERID, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                loading.showLoading();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                loading.showError();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    StudentResumeDetail studentResumeDetail = gson.fromJson(data.toString(), StudentResumeDetail.class);
                    //个人信息
                    StudentInfo studentInfo = studentResumeDetail.getStudentInfo();
                    stuName = studentInfo.getStuName();
                    tvStuName.setText(stuName);
                    tvAge.setText(studentInfo.getStuAge() + "");
                    tvSex.setText(studentInfo.getSex());
                    tvHeight.setText(studentInfo.getHeight() + "");
                    tvWeight.setText(studentInfo.getWeight() + "");
                    tvNation.setText(studentInfo.getNation());
                    tvPlace.setText(studentInfo.getPlace());
                    tvLocation.setText(studentInfo.getAddress());
                    tvBirth.setText(studentInfo.getBirth() + "");

                    //教育经历
                    List<StudentEducation> studentEducation = studentResumeDetail.getStudentEducation();
                    LoadMoreEducationAdapter loadMoreEducationAdapter = new LoadMoreEducationAdapter(studentEducation);
                    educationRecyclerView.addItemDecoration(new DividerItemDecoration(CompanyResumnActivity.this, LinearLayoutManager.VERTICAL));
                    LinearLayoutManager educationManager = new LinearLayoutManager(CompanyResumnActivity.this);
                    educationRecyclerView.setLayoutManager(educationManager);
                    educationRecyclerView.setNestedScrollingEnabled(false);
                    educationRecyclerView.setAdapter(loadMoreEducationAdapter);
                    //工作经历
                    List<StudentWorkDue> studentWorkDue = studentResumeDetail.getStudentWorkDue();
                    if (studentWorkDue.size() > 0) {
                        LoadMoreWorkAdapter loadMoreWorkAdapter = new LoadMoreWorkAdapter(studentWorkDue);
                        workRecyclerView.addItemDecoration(new DividerItemDecoration(CompanyResumnActivity.this, LinearLayoutManager.VERTICAL));
                        LinearLayoutManager workManager = new LinearLayoutManager(CompanyResumnActivity.this);
                        workRecyclerView.setLayoutManager(workManager);
                        workRecyclerView.setNestedScrollingEnabled(false);
                        workRecyclerView.setAdapter(loadMoreWorkAdapter);
                    } else {
                        llWorkExperience.setVisibility(View.GONE);
                    }

                    //相关意向
                    StudentResume studentResume = studentResumeDetail.getStudentResume();
                    if (null != studentResume) {
                        expectPartener = studentResume.getExpectPartener();
                        String expectWorkPlace = studentResume.getExpectWorkPlace();
                        companionId = studentResume.getCompanionId();
                        if (TextUtils.isEmpty(expectWorkPlace) && TextUtils.isEmpty(expectPartener)) {
                            llRelatedIntention.setVisibility(View.GONE);
                        } else {
                            tvCompanionName.setText(expectPartener);
                            tvExpectWorkPlace.setText(expectWorkPlace);
                        }
                        //自我评价
                        if (TextUtils.isEmpty(studentResume.getSelfEvaluate())) {
                            llSelfAssessment.setVisibility(View.GONE);
                        } else {
                            tvSelfEvaluate.setText(studentResume.getSelfEvaluate());

                        }
                        //荣誉证书
                        if (!TextUtils.isEmpty(studentResume.getCertificate())) {
                            String img1 = studentResume.getCertificate();
                            if (!"".equals(img1) && null != img1) {
                                String[] split = img1.split(";");
                                List<String> strings = Arrays.asList(split);
                                for (int i = 0; i < strings.size(); i++) {
                                    honorList.add(strings.get(i));
                                }
                            }
                            recyclerViewHonor.setAdapter(new InfoCardAdapter(CompanyResumnActivity.this, honorList, id + "", "certificate", "certificate"));
                        } else {
                            llHonoraryCcertificate.setVisibility(View.GONE);
                        }
                        String hobby = studentResume.getHobby();
                        if (TextUtils.isEmpty(hobby)) {
                            llNb.setVisibility(View.GONE);
                        } else {
                            tvNb.setText(hobby);
                        }
                    }


                    String img = studentInfo.getPic();
                    if (!"".equals(img) && null != img) {
                        String[] split = img.split(";");
                        List<String> strings = Arrays.asList(split);
                        for (int i = 0; i < strings.size(); i++) {
                            infoList.add(strings.get(i));
                        }
                        recyclerView.setAdapter(new InfoCardAdapter(CompanyResumnActivity.this, infoList, id + "", "info", "info"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.showContent();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                loading.showError();
            }
        });

    }

    /**
     * 更新简历状态(2;园所同意接收简历 3:园所放弃接收)
     */
    private void updateResumnStatus(int studentUserId, int kinderNeedId, int companionId, int state) {
        final HttpUtils instance = HttpUtils.getinstance(CompanyResumnActivity.this);
        Map map = new HashMap();
        map.put("studentUserId", studentUserId + "");
        map.put("kinderNeedId", kinderNeedId + "");
        map.put("companionId", companionId + "");
        map.put("state", state + "");
        instance.post(Constant.RESUMESTATEUPDATE, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                loading.showLoading();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                loading.showError();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        ShowToastUtils.showToastMsg(CompanyResumnActivity.this, "选择成功!");
                        finish();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.showContent();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                loading.showError();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_no, R.id.tv_yes, R.id.tv_companionName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_no:
                if (null != expectPartener) {
                    String str = " 需要同<font color='#FF0000'>" + expectPartener + "</font>一同放弃";
                    new CommomDialog(CompanyResumnActivity.this, R.style.dialog, str, new CommomDialog.OnCloseListener() {

                        @Override
                        public void onClick(Dialog dialog, boolean confirm, String sContent) {
                            if (confirm) {
                                updateResumnStatus(studentUserId, kinderNeedId, companionId, 3);
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onContentClick() {

                        }
                    })
                            .setTitle("选择【" + stuName + "】的同时").setTitleColor(getResources().getColor(R.color.item_title)).show();
                } else {
                    updateResumnStatus(studentUserId, kinderNeedId, 0, 3);
                }
                break;
            case R.id.tv_yes:
                //如果填写了同伴
                if (null != expectPartener) {
                    String str = " 需要同<font color='#FF0000'>" + expectPartener + "</font>一同接收";
                    new CommomDialog(CompanyResumnActivity.this, R.style.dialog, str, new CommomDialog.OnCloseListener() {

                        @Override
                        public void onClick(Dialog dialog, boolean confirm, String sContent) {
                            if (confirm) {
                                updateResumnStatus(studentUserId, kinderNeedId, companionId, 2);
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onContentClick() {

                        }
                    })
                            .setTitle("选择【" + stuName + "】的同时").setTitleColor(getResources().getColor(R.color.item_title)).show();
                } else {
                    updateResumnStatus(studentUserId, kinderNeedId, 0, 2);
                }
                break;
            case R.id.tv_companionName:


                break;
        }
    }
}
