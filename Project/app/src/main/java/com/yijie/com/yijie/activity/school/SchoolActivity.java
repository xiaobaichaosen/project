package com.yijie.com.yijie.activity.school;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ContactModityActivity;
import com.yijie.com.yijie.activity.PowerActivity;
import com.yijie.com.yijie.activity.TrainDetailAcitity;
import com.yijie.com.yijie.activity.newschool.MemorandumActivity;
import com.yijie.com.yijie.activity.newschool.NewInternshipDetailActivity;
import com.yijie.com.yijie.activity.newschool.NewSchoolIntroduction;
import com.yijie.com.yijie.activity.school.adapter.SchoolContactAdapterRecyclerView;
import com.yijie.com.yijie.activity.school.adapter.SchoolMemeryAdapterRecyclerView;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolContact;
import com.yijie.com.yijie.bean.school.SchoolMain;
import com.yijie.com.yijie.bean.school.SchoolMemoInfo;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 *项目详情
 */
public class SchoolActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.tv_memorandum)
    TextView tvMemorandum;
    @BindView(R.id.tv_newSchoolSample)
    TextView tvNewSchoolSample;
    @BindView(R.id.tv_InternshipDetail)
    TextView tvInternshipDetail;
    @BindView(R.id.tv_trainDetail)
    TextView tvTrainDetail;
    @BindView(R.id.recycler_contact)
    RecyclerView recyclerContact;
    @BindView(R.id.recycler_memery)
    RecyclerView recyclerMemery;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_toBeijinTime)
    TextView tvToBeijinTime;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_managePay)
    TextView tvManagePay;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_during)
    TextView tvDuring;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_appoitTime)
    TextView tvAppoitTime;
    @BindView(R.id.tv_projectName)
    TextView tvProjectName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private SchoolContactAdapterRecyclerView myAdapterRecyclerView; //The Adapter for RecyclerVIew
    private List<StudentBean> mListContact = new ArrayList<>(); // My List the object 'StudentBean'.
    private List<StudentBean> mListMemery = new ArrayList<>(); // My List the object 'StudentBean'.
    //获取学校id
    String schoolId;
    //获取项目id
    String practiceId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_school);
    }

    /**
     * 要传递到各个页面得数据
     */
    SchoolMain schoolMain;
    List<SchoolContact> schoolContact;
    List<SchoolMemoInfo> schoolMemoInfo;
    List<SchoolPractice> schoolPractice;
    List<SchoolSalaryInfo> schoolSalaryInfo;
    @Override
    public void init() {

        schoolId = getIntent().getStringExtra("schoolId");

        practiceId = getIntent().getStringExtra("practiceId");
        String schoolName = getIntent().getStringExtra("schoolName");
        title.setText(schoolName);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        actionItem.setBackgroundResource(R.mipmap.setting);

        recyclerContact.setLayoutManager(new LinearLayoutManager(this));
        recyclerMemery.setLayoutManager(new LinearLayoutManager(this));
        recyclerContact.setNestedScrollingEnabled(false);
        recyclerMemery.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onResume() {
        //测试用
        getSchoolDetail(schoolId, practiceId);
        //        if (practiceId!=null&&schoolId!=null){
//            getSchoolDetail(schoolId, practiceId);
//        }
        super.onResume();
    }

    /**
     * 通过学校id和项目id去查询学校详情
     *
     * @param schoolId
     * @param practiceId
     */
    private void getSchoolDetail(String schoolId, String practiceId) {
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(SchoolActivity.this);
        HttpUtils getinstance = HttpUtils.getinstance(SchoolActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolId", "94");
//        stringStringHashMap.put("schoolId", schoolId);
        getinstance.post(Constant.SELECTDETAIL, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }
            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                Gson gson=new Gson();
                School school=gson.fromJson(data.toString(),School.class);
                //学校
                    schoolMain = school.getSchoolMain();
                    tvContent.setText(schoolMain.getContent());
                //实习薪资

                    schoolSalaryInfo = school.getSchoolSalaryInfo();
                    tvToBeijinTime.setText(schoolSalaryInfo.get(0).getPeriod());
                    tvPay.setText(schoolSalaryInfo.get(0).getSalary()+"");
                    tvManagePay.setText(schoolSalaryInfo.get(0).getManageFee()+"");
                    //实习详情

                    schoolPractice = school.getSchoolPractice();
                    tvYear.setText(schoolPractice.get(0).getYear());
                    tvEducation.setText(schoolPractice.get(0).getEducation());
                    tvDuring.setText(schoolPractice.get(0).getTimelimit());
                    tvMonth.setText(schoolPractice.get(0).getPracticeMonth());
                    tvMode.setText(schoolPractice.get(0).getManageModel());
                    tvType.setText(schoolPractice.get(0).getPracticeType());
                    tvAppoitTime.setText(schoolPractice.get(0).getOrderTime()+"");
                    tvProjectName.setText(schoolPractice.get(0).getProjectName());
                    //学校联系人

                    schoolContact = school.getSchoolContact();
                    for (int i = 0; i < schoolContact.size(); i++) {
                        StudentBean studentBean = new StudentBean();
                        studentBean.setConcatName(schoolContact.get(i).getUserName());
                        studentBean.setPhoneNumber(schoolContact.get(i).getCellphone());
                        studentBean.setZiNubmer(schoolContact.get(i).getTelephone());
                        mListContact.add(studentBean);
                    }
                    recyclerContact.setAdapter(new SchoolContactAdapterRecyclerView(SchoolActivity.this, mListContact));
                    //备忘录
                    schoolMemoInfo = school.getSchoolMemoInfo();
                    for (int i = 0; i < schoolMemoInfo.size(); i++) {
                        StudentBean studentBean = new StudentBean();
                        studentBean.setName(schoolMemoInfo.get(i).getCreateBy()+"");
                        studentBean.setMemeryContent(schoolMemoInfo.get(i).getMemoContent());
                        studentBean.setCreatDate(schoolMemoInfo.get(i).getCreateTime()+"");
                        mListMemery.add(studentBean);
                    }
                    recyclerMemery.setAdapter(new SchoolMemeryAdapterRecyclerView(SchoolActivity.this, mListMemery));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }
            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_contact, R.id.tv_memorandum, R.id.tv_newSchoolSample, R.id.tv_InternshipDetail, R.id.tv_trainDetail, R.id.action_item, R.id.back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_contact:
                //把获取到的学校id传入，通过学校id，查询联系人列表
                intent.setClass(this, ContactModityActivity.class);
                intent.putExtra("schoolId",schoolId);
                intent.putExtra("schoolContact", (Serializable) schoolContact);
                startActivity(intent);
                break;
            case R.id.tv_memorandum:
                intent.setClass(this, MemorandumActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_newSchoolSample:
                intent.setClass(this, NewSchoolIntroduction.class);
                intent.putExtra("schoolMain", schoolMain);
                startActivity(intent);
                break;
            case R.id.tv_InternshipDetail:
                intent.setClass(this, NewInternshipDetailActivity.class);
                intent.putExtra("isFromNet",true);
                intent.putExtra("fromNetSchoolPractice", (Serializable) schoolPractice);
                intent.putExtra("schoolSalaryInfo", (Serializable) schoolSalaryInfo);

                startActivity(intent);
                break;
            case R.id.tv_trainDetail:
                intent.setClass(this, TrainDetailAcitity.class);
                startActivity(intent);

                break;

            case R.id.action_item:
                intent.setClass(this, PowerActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;

        }
    }
}
