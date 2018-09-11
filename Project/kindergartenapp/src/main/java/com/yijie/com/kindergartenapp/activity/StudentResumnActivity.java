package com.yijie.com.kindergartenapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.LoadMoreEducationAdapter;
import com.yijie.com.kindergartenapp.adapter.LoadMoreWorkAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.bean.StudentContact;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/26.
 * 学生简历
 */

public class StudentResumnActivity extends BaseActivity {
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

    @BindView(R.id.tv_cellphone)
    TextView tvCellphone;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
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
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    int studentUserId;
    int kinderNeedId;
    int companionId;
    String stuName;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.tv_urgentcontact)
    TextView tvUrgentcontact;
    @BindView(R.id.tv_urgentphone)
    TextView tvUrgentphone;
    private StatusLayoutManager statusLayoutManager;
    private int kinderId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_studentresumn);
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(loading)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick (View view) {
                        statusLayoutManager.showLoadingLayout();
                        getResumnDetail(studentUserId);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        getResumnDetail(studentUserId);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        studentUserId = getIntent().getExtras().getInt("studentUserId");
        kinderNeedId = getIntent().getExtras().getInt("kinderNeedId");
        kinderId = getIntent().getExtras().getInt("kinderId");
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
    private void getResumnDetail(int id) {
        final HttpUtils instance = HttpUtils.getinstance(StudentResumnActivity.this);
        Map map = new HashMap();
        map.put("studentUserId", id + "");

        instance.post(Constant.SELECTBYSTUDENTUSERID, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
               statusLayoutManager.showErrorLayout();
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
                    tvIdcard.setText(studentInfo.getIdCard());
                    //联系方式
                    StudentContact studentContact = studentResumeDetail.getStudentContact();
                    tvCellphone.setText(studentContact.getCellphone());
                    tvWechat.setText(studentContact.getWechat());
                    tvQq.setText(studentContact.getQq());
                    tvEmail.setText(studentContact.getEmail());
                    tvUrgentcontact.setText(studentContact.getUrgentContact());
                    tvUrgentphone.setText(studentContact.getUrgentCellphone());
                    //教育经历
                    List<StudentEducation> studentEducation = studentResumeDetail.getStudentEducation();
                    LoadMoreEducationAdapter loadMoreEducationAdapter = new LoadMoreEducationAdapter(studentEducation);
                    educationRecyclerView.addItemDecoration(new DividerItemDecoration(StudentResumnActivity.this, LinearLayoutManager.VERTICAL));
                    LinearLayoutManager educationManager = new LinearLayoutManager(StudentResumnActivity.this);
                    educationRecyclerView.setLayoutManager(educationManager);
                    educationRecyclerView.setNestedScrollingEnabled(false);
                    educationRecyclerView.setAdapter(loadMoreEducationAdapter);
                    //工作经历
                    List<StudentWorkDue> studentWorkDue = studentResumeDetail.getStudentWorkDue();
                    LoadMoreWorkAdapter loadMoreWorkAdapter = new LoadMoreWorkAdapter(studentWorkDue);
                    workRecyclerView.addItemDecoration(new DividerItemDecoration(StudentResumnActivity.this, LinearLayoutManager.VERTICAL));
                    LinearLayoutManager workManager = new LinearLayoutManager(StudentResumnActivity.this);
                    workRecyclerView.setLayoutManager(workManager);
                    workRecyclerView.setNestedScrollingEnabled(false);
                    workRecyclerView.setAdapter(loadMoreWorkAdapter);
                    //相关意向
                    StudentResume studentResume = studentResumeDetail.getStudentResume();
                    tvExpectWorkPlace.setText(studentResume.getExpectWorkPlace());

                    expectPartener = studentResume.getExpectPartener();

                    companionId = studentResume.getCompanionId();
                    tvCompanionName.setText(expectPartener);
                    //自我评价
                    tvSelfEvaluate.setText(studentResume.getSelfEvaluate());
                    //荣誉证书
                    //TODO===============

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commonDialog.dismiss();
                statusLayoutManager.showSuccessLayout();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }
        });

    }

    /**
     * 更新简历状态(2;园所同意接收简历 3:园所放弃接收)
     */
    private void updateResumnStatus(int studentUserId, int kinderNeedId, int companionId, int state) {
        final HttpUtils instance = HttpUtils.getinstance(StudentResumnActivity.this);
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(StudentResumnActivity.this);
        Map map = new HashMap();
        map.put("studentUserId", studentUserId + "");
        map.put("kinderNeedId", kinderNeedId + "");
        map.put("companionId", companionId + "");
        map.put("state", state + "");
        map.put("alias", kinderId+"");
        instance.post(Constant.RESUMESTATEUPDATE, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                commonDialog.show();
                commonDialog.setTitle("提交数据中...");
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String resode = jsonObject.getString("rescode");
                    if (resode.equals("200")) {
                        ShowToastUtils.showToastMsg(StudentResumnActivity.this, "选择成功!");
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commonDialog.dismiss();
                statusLayoutManager.showSuccessLayout();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
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
                    new CommomDialog(StudentResumnActivity.this, R.style.dialog, str, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                updateResumnStatus(studentUserId, kinderNeedId, companionId, 3);
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onContentClick() {
                            Intent intent = new Intent();
                            intent.setClass(StudentResumnActivity.this, CompanyResumnActivity.class);
                            startActivity(intent);
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
                    new CommomDialog(StudentResumnActivity.this, R.style.dialog, str, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                updateResumnStatus(studentUserId, kinderNeedId, companionId, 2);
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onContentClick() {
                            Intent intent = new Intent();
                            intent.putExtra("companionId", companionId);
                            intent.putExtra("kinderNeedId", kinderNeedId);

                            intent.setClass(StudentResumnActivity.this, CompanyResumnActivity.class);
                            startActivity(intent);
                        }
                    })
                            .setTitle("选择【" + stuName + "】的同时").setTitleColor(getResources().getColor(R.color.item_title)).show();
                } else {
                    updateResumnStatus(studentUserId, kinderNeedId, 0, 2);
                }
                break;
            case R.id.tv_companionName:
                Intent intent = new Intent();
                intent.putExtra("companionId", companionId);
                intent.putExtra("kinderNeedId", kinderNeedId);
                intent.setClass(StudentResumnActivity.this, CompanyResumnActivity.class);
                startActivity(intent);

                break;
        }
    }
}