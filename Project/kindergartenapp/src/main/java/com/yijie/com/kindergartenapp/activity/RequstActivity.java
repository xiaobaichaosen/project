package com.yijie.com.kindergartenapp.activity;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CourseBean;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.SchoolPractice;
import com.yijie.com.kindergartenapp.util.ViewUtils;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.SoftKeyBoardListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/8.
 * 提需求页面
 */

public class RequstActivity extends BaseActivity {
    CourseBean courseBean = new CourseBean();
    ;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.cb_dance)
    CheckBox cbDance;
    @BindView(R.id.cb_art)
    CheckBox cbArt;
    @BindView(R.id.cb_english)
    CheckBox cbEnglish;
    @BindView(R.id.cb_roll)
    CheckBox cbRoll;
    @BindView(R.id.cb_piano)
    CheckBox cbPiano;
    @BindView(R.id.cb_thought)
    CheckBox cbThought;
    @BindView(R.id.cb_other)
    CheckBox cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_studentNum)
    EditText etStudentNum;

    @BindView(R.id.ll_root)
    NestedScrollView llRoot;
    @BindView(R.id.tv_projectName)
    TextView tvProjectName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_request)
    TextView tvRequest;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_getTime)
    TextView tvGetTime;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.tv_free)
    TextView tvFree;
    @BindView(R.id.tv_peopleNum)
    TextView tvPeopleNum;
    @BindView(R.id.tv_confrim)
    TextView tvConfrim;
    @BindView(R.id.et_low)
    EditText etLow;
    @BindView(R.id.et_height)
    EditText etHeight;

    @BindView(R.id.ll_commit)
    LinearLayout llCommit;
    @BindView(R.id.imgBtn)
    TextView imgBtn;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.cb_isGreen)
    CheckBox cbIsGreen;
    private CourseBean modiftyCourseBean;
    int kenderNeedId;
    String location;
    String projectName;
    int kinderId;
    private StatusLayoutManager statusLayoutManager;
    private int schoolId;
    private int projectId;
    private SchoolPractice schoolPractice;
    private Integer kindpeoNumSet;
    public static Activity instance = null;
    private boolean isVisiabe=true;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_request);
    }

    @Override
    public void init() {
        SoftKeyBoardListener.setListener(RequstActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                llCommit.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide(int height) {
                llCommit.setVisibility(View.VISIBLE);
            }
        });
        instance=this;
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(llRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        getData(kenderNeedId);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        getData(kenderNeedId);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        String projectName = getIntent().getStringExtra("projectName");
        kenderNeedId = getIntent().getIntExtra("kenderNeedId", 0);
        schoolId = getIntent().getIntExtra("schoolId", 0);
        projectId = getIntent().getIntExtra("projectId", 0);
        schoolPractice = (SchoolPractice) getIntent().getExtras().getSerializable("SchoolPractice");
        LogUtil.e("SchoolPractice", schoolPractice);
        tvProjectName.setText(schoolPractice.getProjectName());
        tvAddress.setText(schoolPractice.getLocation());
        //薪资要求
        tvRequest.setText("不低于" + schoolPractice.getSalary() + "/月");
        tvType.setText(schoolPractice.getPracticeType());
        tvEducation.setText(schoolPractice.getEducation());
        tvGetTime.setText(schoolPractice.getToBeijingTime());
        tvMode.setText(schoolPractice.getManageModel());
        tvFree.setText(schoolPractice.getManageFee() + "/人");
        tvPeopleNum.setText(schoolPractice.getKindpeoNumSet() + "人");
        //人数设定

        kindpeoNumSet = schoolPractice.getKindpeoNumSet();
        if (null != kindpeoNumSet) {
            tvPeopleNum.setText(kindpeoNumSet + "人");
            tvSign.setText("该项目最多可提交需求人数" + kindpeoNumSet + "人");
        }
        title.setText(projectName);
        title.setTextSize(ViewUtils.sp2px(this, 6));
//        tvRecommend.setVisibility(View.VISIBLE);
//        tvRecommend.setText("发布");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        if (0 != kenderNeedId) {
            //通过需求id查询需求详情
            getData(kenderNeedId);
        } else {
            statusLayoutManager.showSuccessLayout();
        }
    }

    @OnCheckedChanged({R.id.cb_dance, R.id.cb_art, R.id.cb_english, R.id.cb_roll, R.id.cb_piano, R.id.cb_thought, R.id.cb_other})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_other:
                if (ischanged) {
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                } else {
                    etContent.setFocusable(false);
                    etContent.setFocusableInTouchMode(false);
                    etContent.setText(null);
                }
                break;
            case R.id.cb_dance:
                if (ischanged) {
                    courseBean.setDanceString("舞蹈");
                } else {
                    courseBean.setDanceString(null);
                }
                break;
            case R.id.cb_art:
                if (ischanged) {
                    courseBean.setArtString("美术");
                } else {
                    courseBean.setArtString(null);
                }
                break;
            case R.id.cb_english:
                if (ischanged) {
                    courseBean.setEnglishString("英语");
                } else {
                    courseBean.setEnglishString(null);
                }
                break;
            case R.id.cb_roll:
                if (ischanged) {
                    courseBean.setRollString("轮滑");
                } else {
                    courseBean.setRollString(null);
                }
                break;
            case R.id.cb_piano:
                if (ischanged) {
                    courseBean.setPainoString("钢琴");
                } else {
                    courseBean.setPainoString(null);
                }
                break;
            case R.id.cb_thought:
                if (ischanged) {
                    courseBean.setThoughtString("电子琴");
                } else {
                    courseBean.setThoughtString(null);
                }
                break;


        }
    }

    /**
     * 获取需求详情
     */
    private void getData(int kenderNeedId) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id", kenderNeedId + "");
        getinstance.post(Constant.SELECTRECRUITDETAILBYID, stringStringHashMap, new BaseCallback<String>() {
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
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                JSONObject data = null;
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    data = jsonObject.getJSONObject("data");
                    String kinderSalarySet = data.getString("kinderSalarySet");
                    if (!TextUtils.isEmpty(kinderSalarySet)&&!kinderSalarySet.equals("null")){
                        String[] split = kinderSalarySet.split("-");
                        etLow.setText(split[0]);
                        etHeight.setText(split[1]);
                    }

                    etStudentNum.setText(data.getString("studentNum"));
                    String dance = data.getString("dance");
                    String arts = data.getString("arts");
                    String english = data.getString("english");
                    String skidding = data.getString("skidding");
                    String piano = data.getString("piano");
                    String electronicOrgan = data.getString("electronicOrgan");
                    String other = data.getString("other");
                    projectName = data.getString("projectName");
                    location = data.getString("location");

                    kinderId = data.getInt("kinderId");

                    if ("null" != dance) {
                        cbDance.setChecked(true);
                        courseBean.setDanceString("舞蹈");
                    }
                    if ("null" != arts) {
                        cbArt.setChecked(true);
                        courseBean.setArtString("美术");
                    }
                    if ("null" != english) {
                        cbEnglish.setChecked(true);
                        courseBean.setEnglishString("英语");
                    }
                    if ("null" != skidding) {
                        cbRoll.setChecked(true);
                        courseBean.setRollString("轮滑");

                    }
                    if ("null" != piano) {
                        cbPiano.setChecked(true);
                        courseBean.setPainoString("钢琴");
                    }
                    if ("null" != electronicOrgan) {
                        cbThought.setChecked(true);
                        courseBean.setThoughtString("电子琴");
                    }
                    if ("null" != other) {
                        cbOther.setChecked(true);
                        courseBean.setThoughtString(other);
                        etContent.setText(other);
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

    @OnClick({R.id.back, R.id.tv_recommend, R.id.tv_confrim, R.id.imgBtn, R.id.tv_sign,R.id.et_height})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_height:

                break;


            case R.id.tv_confrim:
                Intent intent = new Intent();
                String requestNum = etStudentNum.getText().toString().trim();
                String lowMoney = etLow.getText().toString().trim();
                String heightMoney = etHeight.getText().toString().trim();
                String manageFee = schoolPractice.getManageFee();
                if (requestNum.isEmpty()) {
                    ShowToastUtils.showToastMsg(RequstActivity.this, "请填写人数需求");
                    return;
                } else if (lowMoney.isEmpty() || heightMoney.isEmpty()) {
                    ShowToastUtils.showToastMsg(RequstActivity.this, "请填写薪资");
                    return;
                } else if (Integer.parseInt(lowMoney) < Integer.parseInt(schoolPractice.getSalary())) {
                    ShowToastUtils.showToastMsg(RequstActivity.this, "最低工资不能低于" + schoolPractice.getSalary());
                    return;
                } else if (Integer.parseInt(heightMoney) < Integer.parseInt(lowMoney)) {
                    ShowToastUtils.showToastMsg(RequstActivity.this, "最高工资不能低于最低工资");
                    return;
                } else if (Integer.parseInt(requestNum) > kindpeoNumSet) {
                    ShowToastUtils.showToastMsg(RequstActivity.this, "需求人数不能大于" + kindpeoNumSet);
                    return;
                }
                intent.putExtra("requestNum", requestNum);
                intent.putExtra("lowMoney", lowMoney);
                intent.putExtra("heightMoney", heightMoney);
                intent.putExtra("manageFee", manageFee);

                if (cbIsGreen.isChecked()){
                    if (null != courseBean) {
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("courseBean", courseBean);
                        intent.putExtras(mBundle);
                    }
//                    intent.putExtra("nb", nb);
                    intent.putExtra("kindpeoNumSet", kindpeoNumSet);
                    intent.putExtra("kenderNeedId", kenderNeedId);
                    intent.putExtra("projectId", projectId);

                    intent.setClass(RequstActivity.this, RequsetComActivity.class);
                    startActivity(intent);
                }else {
                    ShowToastUtils.showToastMsg(RequstActivity.this, "请先勾选服务协议");
                }

                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                final HttpUtils instance = HttpUtils.getinstance(RequstActivity.this);
                KindergartenNeed kindergartenNeed = new KindergartenNeed();
                int userId = (int) SharedPreferencesUtils.getParam(RequstActivity.this, "userId", 0);
                if (kenderNeedId != 0) {
                    kindergartenNeed.setId(kenderNeedId);
                }
                kindergartenNeed.setKinderId(userId);
                kindergartenNeed.setSchoolId(schoolId);
                kindergartenNeed.setSchoolPracticeId(projectId);
                //需求人数
                kindergartenNeed.setStudentNum(Integer.parseInt(etStudentNum.getText().toString()));
                kindergartenNeed.setProjectName(projectName);
                kindergartenNeed.setLocation(location);
                //特长
                kindergartenNeed.setDance(courseBean.getDanceString());
                kindergartenNeed.setArts(courseBean.getArtString());
                kindergartenNeed.setEnglish(courseBean.getEnglishString());
                kindergartenNeed.setSkidding(courseBean.getRollString());
                kindergartenNeed.setPiano(courseBean.getPainoString());
                kindergartenNeed.setElectronicOrgan(courseBean.getThoughtString());
                if (cbOther.isChecked()) {
                    kindergartenNeed.setOther(etContent.getText().toString().trim());
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
                        statusLayoutManager.showErrorLayout();
                    }

                    @Override
                    public void onSuccess(Response response, String s) {
                        LogUtil.e(s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String resode = jsonObject.getString("rescode");
                            if (resode.equals("200")) {
                                ShowToastUtils.showToastMsg(RequstActivity.this, "发布成功!");
                                finish();
                            } else if (resode.equals("500")) {
                                ShowToastUtils.showToastMsg(RequstActivity.this, "请完善信息后提需求!");

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


                break;

            case R.id.imgBtn:
                tvSign.setVisibility(View.VISIBLE);
                imgBtn.setVisibility(View.GONE);

                break;
            case R.id.tv_sign:
                imgBtn.setVisibility(View.VISIBLE);
                tvSign.setVisibility(View.GONE);
                break;
        }
    }


}
