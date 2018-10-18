package com.yijie.com.kindergartenapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CourseBean;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.StayBean;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.view.AmountView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 需求确认
 */
public class RequsetComActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_requestnum)
    TextView tvRequestnum;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    @BindView(R.id.tv_nb)
    TextView tvNb;
    @BindView(R.id.et_studentNum)
    AmountView etStudentNum;
    @BindView(R.id.tv_free)
    TextView tvFree;
    @BindView(R.id.tv_paymonth)
    TextView tvPaymonth;
    @BindView(R.id.tv_totalmoney)
    TextView tvTotalmoney;
    @BindView(R.id.ll_root)
    ScrollView llRoot;
    @BindView(R.id.tv_totalFree)
    TextView tvTotalFree;
    @BindView(R.id.tv_requst)
    TextView tvRequst;
    @BindView(R.id.tv_serviceFree)
    TextView tvServiceFree;
    private int kenderNeedId;
    private int schoolId;
    private int projectId;
    private CourseBean tempCourseBean;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_requestcom);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("需求确认");
        final String requestNum = getIntent().getStringExtra("requestNum");
        String lowMoney = getIntent().getStringExtra("lowMoney");
        String heightMoney = getIntent().getStringExtra("heightMoney");
        final String manageFee = getIntent().getStringExtra("manageFee");
        final Integer kindpeoNumSet = getIntent().getIntExtra("kindpeoNumSet",0);
        kenderNeedId = getIntent().getIntExtra("kenderNeedId", 0);
        schoolId = getIntent().getIntExtra("schoolId", 0);
        projectId = getIntent().getIntExtra("projectId", 0);
        tempCourseBean = (CourseBean) getIntent().getExtras().getSerializable("courseBean");
        if (null!=tempCourseBean){
            String nb = "";
            if (null != tempCourseBean.getDanceString()) {
                nb += tempCourseBean.getDanceString() + "、";
            }
            if (null != tempCourseBean.getArtString()) {
                nb += tempCourseBean.getArtString() + "、";
            }
            if (null != tempCourseBean.getEnglishString()) {
                nb += tempCourseBean.getEnglishString() + ";";
            }
            if (null != tempCourseBean.getRollString()) {
                nb += tempCourseBean.getRollString() + "、";
            }
            if (null != tempCourseBean.getPainoString()) {
                nb += tempCourseBean.getPainoString() + "、";
            }
            if (null != tempCourseBean.getThoughtString()) {
                nb += tempCourseBean.getThoughtString() + "、";
            }
            //  TODO 去掉最后一个、
            if (nb.length() > 0) {
                nb = nb.substring(0, nb.length() - 1);
            }
            tvNb.setText(nb);
        }

        tvRequestnum.setText(requestNum+"/人");
        //薪资范围
        tvMoney.setText(lowMoney + "-" + heightMoney);
        tvFree.setText(manageFee + "/人");
        tvServiceFree.setText(manageFee + "/人");

        //设置设定不能超过的人数
        etStudentNum.setGoods_storage(kindpeoNumSet);
        //设置初始化人数
        etStudentNum.setAmount(Integer.parseInt(requestNum));
        etStudentNum.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                tvRequestnum.setText(amount+"/人");
                tvTotalFree.setText("￥"+(amount*Integer.parseInt(manageFee)*(Integer.parseInt(tvPaymonth.getText().toString().trim()))));
                tvTotalmoney.setText("￥"+(amount*Integer.parseInt(manageFee)*(Integer.parseInt(tvPaymonth.getText().toString().trim()))));
            }
        });
//        tvTotalFree.setText("￥"+(Integer.parseInt(requestNum)*Integer.parseInt(manageFee)));
//        tvTotalmoney.setText("￥"+(Integer.parseInt(requestNum)*Integer.parseInt(manageFee)));
        tvTotalFree.setText("￥"+(Integer.parseInt(requestNum)*Integer.parseInt(manageFee)*(Integer.parseInt(tvPaymonth.getText().toString().trim()))));
        tvTotalmoney.setText("￥"+(Integer.parseInt(requestNum)*Integer.parseInt(manageFee)*(Integer.parseInt(tvPaymonth.getText().toString().trim()))));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.back, R.id.tv_requst})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_requst:
                //发布需求
                final HttpUtils instance = HttpUtils.getinstance(RequsetComActivity.this);
                KindergartenNeed kindergartenNeed = new KindergartenNeed();
                String kinderId = (String) SharedPreferencesUtils.getParam(RequsetComActivity.this, "kinderId", "");
                if (kenderNeedId != 0) {
                    kindergartenNeed.setId(kenderNeedId);
                }
                kindergartenNeed.setKinderId(Integer.parseInt(kinderId));
                kindergartenNeed.setSchoolId(schoolId);
                kindergartenNeed.setSchoolPracticeId(projectId);
                //园所设定工资
                kindergartenNeed.setKinderSalarySet(tvMoney.getText().toString().trim());
                //首次支付月数
                kindergartenNeed.setFirstPayMonth(Integer.parseInt(tvPaymonth.getText().toString().trim()));
                //合计金额
                kindergartenNeed.setAmout(tvTotalmoney.getText().toString().trim().substring(1, tvTotalmoney.getText().toString().trim().length()));
                //需求人数
                kindergartenNeed.setStudentNum(Integer.parseInt(tvRequestnum.getText().toString().trim().substring(0, tvRequestnum.getText().toString().trim().length() - 2)));
                //特长
                if (null!=tempCourseBean){
                    kindergartenNeed.setDance(tempCourseBean.getDanceString());
                    kindergartenNeed.setArts(tempCourseBean.getArtString());
                    kindergartenNeed.setEnglish(tempCourseBean.getEnglishString());
                    kindergartenNeed.setSkidding(tempCourseBean.getRollString());
                    kindergartenNeed.setPiano(tempCourseBean.getPainoString());
                    kindergartenNeed.setElectronicOrgan(tempCourseBean.getThoughtString());
                    kindergartenNeed.setOther(tempCourseBean.getOtherString());
                }
                instance.postJson(Constant.KINDERGARTENNEED, kindergartenNeed, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
//                        commonDialog.setTitle("修改中...");
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) {
                        LogUtil.e(s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String resode = jsonObject.getString("rescode");
                            if (resode.equals("200")) {
                                ShowToastUtils.showToastMsg(RequsetComActivity.this, "发布成功!");
                                Activity instance1 = RequstActivity.instance;
                                if (null!=instance1){
                                    instance1.finish();
                                }
                                Activity instance2 = RequestDetailActivity.instance;
                                if (null!=instance2){
                                    instance2.finish();
                                }
                                finish();

                            } else if (resode.equals("500")) {
                                ShowToastUtils.showToastMsg(RequsetComActivity.this, "请完善信息后提需求!");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        commonDialog.dismiss();
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }
                });


                break;
        }
    }
}
