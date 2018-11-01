package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.CourseBean;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.bean.ProtectBean;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.bean.StayBean;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.DisplayUtil;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.TimeUtil;
import com.yijie.com.kindergartenapp.utils.UIUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

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
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/22.
 * 修改园所详情
 */

public class ModiKenderDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.kenderName)
    EditText kenderName;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.rl_gradAdress)
    RelativeLayout rlGradAdress;

    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_wachat)
    EditText etWachat;
    @BindView(R.id.et_qq)
    EditText etQq;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.tv_configuration)
    TextView tvConfiguration;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.et_phonenum)
    EditText etPhonenum;
    @BindView(R.id.et_classnum)
    EditText etClassnum;

    @BindView(R.id.tv_SpecCourses)
    TextView tvSpecCourses;
    @BindView(R.id.tv_stay)
    TextView tvStay;
    @BindView(R.id.rl_gradStay)
    RelativeLayout rlGradStay;
    @BindView(R.id.tv_meal)
    TextView tvMeal;
    @BindView(R.id.rl_meal)
    RelativeLayout rlMeal;
    @BindView(R.id.tv_uniform)
    TextView tvUniform;
    @BindView(R.id.tv_checkmoney)
    TextView tvCheckmoney;
    @BindView(R.id.tv_certificate)
    TextView tvCertificate;
    @BindView(R.id.tv_license)
    TextView tvLicense;
    @BindView(R.id.tv_kenderphone)
    TextView tvKenderphone;
    @BindView(R.id.tv_envierphone)
    TextView tvEnvierphone;
    @BindView(R.id.tv_kenderSimple)
    TextView tvKenderSimple;
    @BindView(R.id.tv_classset)
    TextView tvClassset;
    @BindView(R.id.rl_SpecCourses)
    RelativeLayout rlSpecCourses;
    @BindView(R.id.rl_level)
    RelativeLayout rlLevel;
    @BindView(R.id.rl_configuration)
    RelativeLayout rlConfiguration;
    @BindView(R.id.rl_checkmoney)
    RelativeLayout rlCheckmoney;
    @BindView(R.id.rl_uniform)
    RelativeLayout rlUniform;
    @BindView(R.id.rl_kenderSimple)
    RelativeLayout rlKenderSimple;

    @BindView(R.id.rl_license)
    RelativeLayout rlLicense;
    @BindView(R.id.rl_certificate)
    RelativeLayout rlCertificate;
    @BindView(R.id.rl_picture)
    RelativeLayout rlPicture;
    @BindView(R.id.rl_envierphone)
    RelativeLayout rlEnvierphone;
    @BindView(R.id.tv_kindCreateTime)
    TextView tvKindCreateTime;
    @BindView(R.id.rl_kindCreateTime)
    RelativeLayout rlKindCreateTime;
    @BindView(R.id.et_payKind)
    EditText etPayKind;
    @BindView(R.id.et_kinderNum)
    EditText etKinderNum;
    @BindView(R.id.rl_kinderNum)
    RelativeLayout rlKinderNum;
    @BindView(R.id.et_paymoney)
    TextView etPaymoney;

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_protection)
    TextView tvProtection;
    @BindView(R.id.rl_protection)
    RelativeLayout rlProtection;
    @BindView(R.id.rl_paymoney)
    RelativeLayout rlPaymoney;
    private KindergartenDetail modityKindergartenDetail;
    private SchoolAdress tempSchoolAdress=new SchoolAdress();
    private String eat;
    private String stay;
    //特色课程
    private String curseString;
    //园所级别
    private String levelString;
    //园所类别
    private String kindType;
    //首年体检费
    private String checkMoney;
    //工服押金
    private String clothDeposit;
    //班级配置
    private String classSet;
    private String summary;
    //荣誉证书
    private String certificate;
    //营业执照
    private String businessLicence;
    //园所照片
    private String environment;
    private String kinderId;
    private String attachment;
    private String stayString;
    private ArrayList<String> dayList = new ArrayList<String>();
    private String protectString;
    private  String kindName;
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_modekenderdetail);
        EventBus.getDefault().register(this);
    }

    @Override
    public void init() {
        title.setText("园所注册信息");
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        for (int i = 1; i < 32; i++) {
            dayList.add(i+"号");
        }
        setTranslucent(this); // 改变状态栏变成透明
        kinderId = (String) SharedPreferencesUtils.getParam(ModiKenderDetailActivity.this, "cellphone", "");
        getKenderDeail(kinderId);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(this);
        Map map = new HashMap();
        map.put("cellphone", kenderId);
        instance.post(Constant.SELECTBYCELLPHONE, map, new BaseCallback<String>() {

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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    modityKindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);

                    kindName = modityKindergartenDetail.getKindName();
                    kenderName.setText(kindName);
                    etContact.setText(modityKindergartenDetail.getKindContact());
                    etPhone.setText(modityKindergartenDetail.getCellphone());
                    etWachat.setText(modityKindergartenDetail.getWechart());
                    etEmail.setText(modityKindergartenDetail.getEmail());
                    etQq.setText(modityKindergartenDetail.getQq());
                    tvAdress.setText(modityKindergartenDetail.getKindAddress()+modityKindergartenDetail.getKindDetailAddress());
//                    etDetailAdress.setText(modityKindergartenDetail.getKindDetailAddress());
                    tempSchoolAdress.setDetailAdress(modityKindergartenDetail.getKindDetailAddress());
                    tempSchoolAdress.setName(modityKindergartenDetail.getKindAddress());
                    tempSchoolAdress.setLon(modityKindergartenDetail.getLongitude());
                    tempSchoolAdress.setLat(modityKindergartenDetail.getLatitude());
                    stay = modityKindergartenDetail.getStay();
                    tvStay.setText(stay);
                    etArea.setText(modityKindergartenDetail.getArea());
                    eat = modityKindergartenDetail.getEat();
                    tvMeal.setText(eat);
                    clothDeposit = modityKindergartenDetail.getClothesDeposit() + "";
                    tvUniform.setText(clothDeposit);
                    checkMoney = modityKindergartenDetail.getFirstTestFee() + "";
                    tvCheckmoney.setText(checkMoney);
                    //幼儿园人数
                    etPhonenum.setText(modityKindergartenDetail.getChildrenNum() + "");
                    //班级数量
                    etClassnum.setText(modityKindergartenDetail.getClassNum() + "");

                    classSet = modityKindergartenDetail.getClassSet();
                    tvClassset.setText(classSet);
                    //特色课程
                    curseString = modityKindergartenDetail.getFeatureCourse();
                    tvSpecCourses.setText(curseString);
                    //园所类别
                    kindType = modityKindergartenDetail.getKindType();
                    tvConfiguration.setText(kindType);
                    //园所级别
                    levelString = modityKindergartenDetail.getKindLevel();
                    tvLevel.setText(levelString);
                    //荣誉证书
                    certificate = modityKindergartenDetail.getCertificate();
                    //营业执照
                    businessLicence = modityKindergartenDetail.getBusinessLicence();
                    //园所照片
                    environment = modityKindergartenDetail.getEnvironment();
                    attachment = modityKindergartenDetail.getAttachment();
                    summary = modityKindergartenDetail.getSummary();
                    //建园日期
                    tvKindCreateTime.setText(modityKindergartenDetail.getBuildGardenDate());
                    //职工人数
                    Integer teacherNum = modityKindergartenDetail.getTeacherNum();
                    if (null==teacherNum){
                        etKinderNum.setText("");
                    }else {
                        etKinderNum.setText(teacherNum+"");
                    }

                    //托费
                    Integer nuseryFee = modityKindergartenDetail.getNuseryFee();
                    if (null==nuseryFee){
                        etPayKind.setText("");
                    }else{
                        etPayKind.setText(modityKindergartenDetail.getNuseryFee()+"");
                    }

                    //薪资发放日
                    etPaymoney.setText(modityKindergartenDetail.getSalaryGrantDate());
                     //保险
                    protectString = modityKindergartenDetail.getFormalWelfare();
                    tvProtection.setText(protectString);

                    if (!TextUtils.isEmpty(summary)) {
                        tvKenderSimple.setText("已填写");
                    }
                    if (!TextUtils.isEmpty(certificate)) {
                        tvCertificate.setText("已填写");
                    }
                    if (!TextUtils.isEmpty(businessLicence)) {
                        tvLicense.setText("已填写");
                    }
                    if (!TextUtils.isEmpty(environment)) {
                        tvKenderphone.setText("已填写");
                    }
                    if (!TextUtils.isEmpty(attachment)) {
                        tvEnvierphone.setText("已填写");
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void stay(StayBean stayBean) {
        //初始化
        stayString = "";
        String upString = stayBean.getUpString();
        String downString = stayBean.getDownString();
        String outString = stayBean.getOutString();
        String foureightString = stayBean.getFoureightString();
        String eighttwelveString = stayBean.getEighttwelveString();
        String twoString = stayBean.getTwoString();
        String threeString = stayBean.getThreeString();
        String otherString = stayBean.getOtherString();
        String wuString = stayBean.getWuString();
        if (!upString.isEmpty()) {
            stayString += upString + "、";
        }
        if (!downString.isEmpty()) {
            stayString += downString + "、";
        }
        if (!outString.isEmpty()) {
            stayString += outString + "、";
        }
        if (!foureightString.isEmpty()) {
            stayString += foureightString + "、";
        }
        if (!eighttwelveString.isEmpty()) {
            stayString += eighttwelveString + "、";
        }
        if (!twoString.isEmpty()) {
            stayString += twoString + "、";
        }
        if (!threeString.isEmpty()) {
            stayString += threeString + "、";
        }
        if (!wuString.isEmpty()) {
            stayString += wuString + "、";
        }if (!otherString.isEmpty()) {
            stayString += otherString + "、";
        }

        stayString = stayString.substring(0, stayString.length() - 1);
        tvStay.setText(stayString);

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void courseBean(CourseBean modiftyCourseBean) {
        ArrayList<String> strings = new ArrayList<>();
        strings.clear();
        StringBuilder stringBuilder = new StringBuilder();
        if (null != modiftyCourseBean) {
            if (null != modiftyCourseBean.getDanceString()) {
                strings.add(modiftyCourseBean.getDanceString());
            }
            if (null != modiftyCourseBean.getArtString()) {
                strings.add(modiftyCourseBean.getArtString());
            }
            if (null != modiftyCourseBean.getEnglishString()) {
                strings.add(modiftyCourseBean.getEnglishString());
            }
            if (null != modiftyCourseBean.getRollString()) {
                strings.add(modiftyCourseBean.getRollString());
            }
            if (null != modiftyCourseBean.getPainoString()) {
                strings.add(modiftyCourseBean.getRollString());
            }
            if (null != modiftyCourseBean.getThoughtString()) {
                strings.add(modiftyCourseBean.getThoughtString());
            }
            if (null != modiftyCourseBean.getOtherString() && !modiftyCourseBean.getOtherString().equals("")) {
                strings.add(modiftyCourseBean.getOtherString());
            }

        }
        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                stringBuilder.append(strings.get(i));
            } else {
                stringBuilder.append(strings.get(i) + "、");
            }
        }
        curseString = stringBuilder.toString();
        tvSpecCourses.setText(curseString);

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void protectBean(ProtectBean modiftyProtectBean) {
        ArrayList<String> strings = new ArrayList<>();
        strings.clear();
        StringBuilder stringBuilder = new StringBuilder();
        if (null != modiftyProtectBean) {
            if (null != modiftyProtectBean.getFiveInsurance()) {
                strings.add(modiftyProtectBean.getFiveInsurance());
            }
            if (null != modiftyProtectBean.getProvidentFund()) {
                strings.add(modiftyProtectBean.getProvidentFund());
            }
            if (null != modiftyProtectBean.getSuppMedInsurance()) {
                strings.add( modiftyProtectBean.getSuppMedInsurance());
            }

            if (null != modiftyProtectBean.getOther() && !modiftyProtectBean.getOther().equals("")) {
                strings.add(modiftyProtectBean.getOther());
            }

        }
        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                stringBuilder.append(strings.get(i));
            } else {
                stringBuilder.append(strings.get(i) + "、");
            }
        }
       protectString = stringBuilder.toString();
        tvProtection.setText(protectString);

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void bean(CommonBean commonBean) {
        if (commonBean.getType() == 4) {
            //住宿安排
            eat = commonBean.getRbString();
            tvMeal.setText(eat);
        } else if (commonBean.getType() == 0) {
            //园所类别
            kindType = commonBean.getRbString();
            tvConfiguration.setText(kindType);
        } else if (commonBean.getType() == 1) {
            //园所级别
            levelString = commonBean.getRbString();
            tvLevel.setText(levelString);
        } else if (commonBean.getType() == 2) {
            //园所类别
            kindType = commonBean.getRbString();
            tvConfiguration.setText(kindType);
        } else if (commonBean.getType() == 3) {
            //工服押金
            String rbString = commonBean.getRbString();
            String cbString = commonBean.getCbString();
            if (TextUtils.isEmpty(rbString)){
                clothDeposit=cbString;
            }else {
                clothDeposit=rbString;
            }
            tvUniform.setText(clothDeposit);
        } else if (commonBean.getType() == 9) {
            //首年体检费
            checkMoney = commonBean.getRbString();
            tvCheckmoney.setText(checkMoney);
        } else if (commonBean.getType() == 5) {
            //班级配置
            classSet = commonBean.getRbString();
            tvClassset.setText(classSet);
        } else if (commonBean.getType() == 100) {
            summary = commonBean.getContent();
            tvKenderSimple.setText("已填写");
        } else if (commonBean.getType() == 10) {
            tvCertificate.setText("已填写");
        } else if (commonBean.getType() == 11) {
            tvLicense.setText("已填写");
        } else if (commonBean.getType() == 12) {
            tvKenderphone.setText("已填写");
        } else if (commonBean.getType() == 13) {
            tvEnvierphone.setText("已填写");
        }
// else if (commonBean.getType() == 3) {
//            //工服押金
//            tvUniform.setText("已填写");
//            tempUniformCommonBean = commonBean;
//        } else if (commonBean.getType() == 4) {
//            //首年体检费
//            tvExaminationFee.setText("已填写");
//            tempExaminationCommonBean = commonBean;
//        } else if (commonBean.getType() == 5) {
//            //荣誉证书
//            tvCertificate.setText("已上传");
//            tempCertificateCommonBean = commonBean;
//        }else if (commonBean.getType() == 6) {
//            //营业执照
//            tvLicense.setText("已上传");
//            tempLicenseCommonBean = commonBean;
//        }else if (commonBean.getType() == 7) {
//            //园所照片
//            tvPicture.setText("已上传");
//            tempPictureCommonBean = commonBean;
//        }


    }

    /**
     * 通过id查询园所详情
     */
    public void ModityKenderDeail(KindergartenDetail kindergartenDetail) {
        final HttpUtils instance = HttpUtils.getinstance(ModiKenderDetailActivity.this);
        instance.postJson(Constant.KINDERGARTENDETAIL, kindergartenDetail, new BaseCallback<String>() {

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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    String resMessage = jsonObject.getString("resMessage");
                    if (rescode.equals("200")) {
                        ShowToastUtils.showToastMsg(ModiKenderDetailActivity.this, resMessage);
                        EventBus.getDefault().post("refrsh");
                        finish();
                    } else if (rescode.equals("500")) {
                        ShowToastUtils.showToastMsg(ModiKenderDetailActivity.this, resMessage);
                    }


//                    JSONObject data = jsonObject.getJSONObject("data");
//                    Gson gson = new Gson();


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

    }


        @Subscribe(threadMode = ThreadMode.MainThread)
        public void SchoolAdress(SchoolAdress schoolAdress) {
            tempSchoolAdress=schoolAdress;
            tvAdress.setText(tempSchoolAdress.getName()+tempSchoolAdress.getDetailAdress());

        }
    @OnClick({R.id.et_phone,R.id.rl_kindCreateTime, R.id.rl_protection,R.id.rl_paymoney,R.id.rl_gradAdress, R.id.rl_kenderSimple, R.id.tv_classset, R.id.tv_recommend, R.id.rl_gradStay, R.id.rl_meal, R.id.back, R.id.rl_SpecCourses, R.id.rl_envierphone, R.id.rl_certificate, R.id.rl_license, R.id.rl_picture, R.id.rl_level, R.id.rl_configuration, R.id.rl_checkmoney, R.id.rl_uniform})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            //点击手机号码修改绑定手机
            case R.id.et_phone:
                intent.putExtra("phoneNumber",etPhone.getText().toString().trim());
                intent.setClass(this, ModiPhoneActivity.class);
                startActivity(intent);
                break;

            case  R.id.rl_gradAdress:
                if (null != tempSchoolAdress) {
                    mBundle.putSerializable("tempSchoolAdress", tempSchoolAdress);
                }
                intent.putExtras(mBundle);
                intent.putExtra("kindName",kindName);
                intent.setClass(this, KinderDetailAdressActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_certificate:
                //跳转到荣誉证书
//                intent.putExtra("certificate", certificate);
                intent.setClass(ModiKenderDetailActivity.this, CertificateActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_license:
                //跳转到营业执照
//                intent.putExtra("businessLicence", businessLicence);
                intent.setClass(ModiKenderDetailActivity.this, LicenseActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_picture:
                //园所照片
//                intent.putExtra("environment", environment);
                intent.setClass(ModiKenderDetailActivity.this, KendPictureActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_envierphone:
                //园所宿舍环境
//                intent.putExtra("attachment", attachment);
                intent.setClass(ModiKenderDetailActivity.this, AttachmentActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_detailAdress:
                intent.putExtra("kindName",kindName);
                intent.setClass(ModiKenderDetailActivity.this, PoiSearchActivity.class);
                startActivity(intent);
                break;


            case R.id.rl_kenderSimple:
                //跳转到园所简介
                CommonBean scommonBean = new CommonBean();
                scommonBean.setContent(summary);
                mBundle.putSerializable("tempSimpleCommonBean", scommonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, KinderSimple.class);
                startActivity(intent);
                break;
            case R.id.tv_recommend:
                KindergartenDetail kindergartenDetail = new KindergartenDetail();
                //园所全称
                kindergartenDetail.setKindName(kenderName.getText().toString().trim());
                //园所地址
                kindergartenDetail.setKindAddress(tempSchoolAdress.getName());
                //园所详细地址
                kindergartenDetail.setKindDetailAddress(tempSchoolAdress.getDetailAdress());
                //园所联系人
                kindergartenDetail.setKindContact(etContact.getText().toString().trim());
                //园所联系人电话
                kindergartenDetail.setCellphone(etPhone.getText().toString().trim());
                //电子邮箱
                kindergartenDetail.setEmail(etEmail.getText().toString().trim());
                //微信号
                kindergartenDetail.setWechart(etWachat.getText().toString().trim());
                //QQ号
                kindergartenDetail.setQq(etQq.getText().toString().trim());
                kindergartenDetail.setLongitude(tempSchoolAdress.getLon());
                kindergartenDetail.setLatitude(tempSchoolAdress.getLat());

//                if (TextUtils.isEmpty(tvConfiguration.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写园所类别");
//                    return;
//                }if (TextUtils.isEmpty(etArea.getText().toString().trim())){
//                        ShowToastUtils.showToastMsg(this,"请添写占地面积");
//                        return;
//                    }

                //幼儿园人数
                if (!"null".equals(etPhonenum.getText().toString().trim()) && !"".equals(etPhonenum.getText().toString().trim())) {
                    String trim = etPhonenum.getText().toString().trim();
                    boolean numeric = DisplayUtil.isNumeric(trim);
                    if (numeric) {
                        kindergartenDetail.setChildrenNum(Integer.parseInt(etPhonenum.getText().toString().trim()));
                    } else {
                        ShowToastUtils.showToastMsg(ModiKenderDetailActivity.this, "幼儿人数必须为数字");
                        return;
                    }
                }
                //班级数量
                if (!"null".equals(etClassnum.getText().toString().trim()) && !"".equals(etClassnum.getText().toString().trim())) {
                    String trim = etClassnum.getText().toString().trim();
                    boolean numeric = DisplayUtil.isNumeric(trim);
                    if (numeric) {
                        kindergartenDetail.setClassNum(Integer.parseInt(etClassnum.getText().toString().trim()));
                    } else {
                        ShowToastUtils.showToastMsg(ModiKenderDetailActivity.this, "班级数量必须为数字");
                        return;
                    }

                }
//                if (TextUtils.isEmpty(kenderName.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写园所全称");
//                    return;
//                }if (TextUtils.isEmpty(tvAdress.getText().toString().trim())){
//                ShowToastUtils.showToastMsg(this,"请添写园所地址");
//                return;
//            }
//                if (TextUtils.isEmpty(etDetailAdress.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写园所详细地址");
//                    return;
//                }
//                if (TextUtils.isEmpty(etContact.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写园所联系人");
//                    return;
//                } if (TextUtils.isEmpty(etPhone.getText().toString().trim())){
//                ShowToastUtils.showToastMsg(this,"请添写园所联系人电话");
//                return;
//            }
//                if (TextUtils.isEmpty(tvClassset.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写班级配置");
//                    return;
//                }
//              if (TextUtils.isEmpty(etKinderNum.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写职工人数");
//                    return;
//                    }
//                if (TextUtils.isEmpty(tvStay.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写住宿安排");
//                    return;
//                }
//                if (TextUtils.isEmpty(tvMeal.getText().toString().trim())){
//                ShowToastUtils.showToastMsg(this,"请添写三餐安排");
//                return;
//                } if (TextUtils.isEmpty(tvUniform.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写工服押金");
//                    return;
//                }
//                if (TextUtils.isEmpty(tvCheckmoney.getText().toString().trim())){
//                    ShowToastUtils.showToastMsg(this,"请添写首年体检费");
//                    return;
//                }
//                if (!tvLicense.getText().toString().trim().equals("已填写")){
//                    ShowToastUtils.showToastMsg(this,"请添写营业执照");
//                    return;
//                }if (!tvKenderphone.getText().toString().trim().equals("已填写")){
//                ShowToastUtils.showToastMsg(this,"请添写园所照片");
//                return;
//                }if (!tvEnvierphone.getText().toString().trim().equals("已填写")){
//                    ShowToastUtils.showToastMsg(this,"请添写宿舍环境");
//                    return;
//                }
                //占地面积
                kindergartenDetail.setArea(etArea.getText().toString().trim());
                //园所类别
                kindergartenDetail.setKindType(tvConfiguration.getText().toString().trim());
                //园所级别
                kindergartenDetail.setKindLevel(tvLevel.getText().toString().trim());
                //建园日期
                kindergartenDetail.setBuildGardenDate(tvKindCreateTime.getText().toString().trim());
                //托费
                String trim = etPayKind.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)&&!trim.equals("null")){
                    kindergartenDetail.setNuseryFee(Integer.parseInt(trim));

                }
                String trim1 = etKinderNum.getText().toString().trim();
                if (!TextUtils.isEmpty(trim1)&&!trim1.equals("null")){
                    kindergartenDetail.setTeacherNum(Integer.parseInt(trim1));
                    //职工人数
//                    kindergartenDetail.setTeacherNum(Integer.parseInt(etKinderNum.getText().toString().trim()));
                }

                //薪资发放日
                kindergartenDetail.setSalaryGrantDate(etPaymoney.getText().toString().trim());
                //保险
                kindergartenDetail.setFormalWelfare(tvProtection.getText().toString().trim());
                //班级配置
                kindergartenDetail.setClassSet(tvClassset.getText().toString().trim());
                //特色课程
                kindergartenDetail.setFeatureCourse(tvSpecCourses.getText().toString().trim());
                //住宿安排
                kindergartenDetail.setStay(tvStay.getText().toString().trim());
                //三餐安排
                kindergartenDetail.setEat(tvMeal.getText().toString().trim());
                //工服押金
                kindergartenDetail.setClothesDeposit(tvUniform.getText().toString().trim());
                //首年体检费
                kindergartenDetail.setFirstTestFee(tvCheckmoney.getText().toString().trim());
                //荣誉证书
                //营业执照
                //园所照片
                //宿舍环境
                //园所简介
                kindergartenDetail.setSummary(summary);
                kindergartenDetail.setId(modityKindergartenDetail.getId());
                ModityKenderDeail(kindergartenDetail);
                break;
            case R.id.rl_gradStay:
                StayBean stayBean;
                if (null != stay) {
                    String[] split = stay.split("、");
                    List<String> list = Arrays.asList(split);
                    stayBean = new StayBean();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals("园内地上")) {
                            stayBean.setUpString("园内地上");
                        } else if (list.get(i).equals("园内地下")) {
                            stayBean.setDownString("园内地下");
                        } else if (list.get(i).equals("园外")) {
                            stayBean.setOutString("园外");
                        } else if (list.get(i).equals("4-8人")) {
                            stayBean.setFoureightString("4-8人");
                        } else if (list.get(i).equals("8-12人")) {
                            stayBean.setEighttwelveString("8-12人");
                        } else if (list.get(i).equals("两居")) {
                            stayBean.setTwoString("两居");
                        } else if (list.get(i).equals("三居")) {
                            stayBean.setThreeString("三居");
                        } else if (list.get(i).equals("无")) {
                            stayBean.setWuString("无");
                        } else {
                            stayBean.setOtherString(list.get(i));
                        }
                    }
                } else {
                    stayBean = new StayBean();
                }
                mBundle.putSerializable("tempStayBean", stayBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, StayActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_level:
                //跳转到园所级别
                CommonBean gcommonBean = new CommonBean();
                gcommonBean.setRbString(levelString);
                mBundle.putSerializable("tempGradeCommonBean", gcommonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, KenderGradeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_meal:
                CommonBean commonBean = new CommonBean();
                commonBean.setRbString(eat);
                mBundle.putSerializable("tempMealCommonBean", commonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, MealsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_configuration:
                //园所类别
                CommonBean ccommonBean = new CommonBean();
                ccommonBean.setRbString(kindType);
                mBundle.putSerializable("tempConfigCommonBean", ccommonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, KenderCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_checkmoney:
                //跳转到首年体检费
                CommonBean tempExaminationCommonBean = new CommonBean();
                tempExaminationCommonBean.setRbString(checkMoney);
                mBundle.putSerializable("tempExaminationCommonBean", tempExaminationCommonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, ExaminationFeeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_uniform:
                //跳转到工服押金
                CommonBean tempUniformCommonBean = new CommonBean();
                if (UIUtils.isNumeric(clothDeposit)){
                    tempUniformCommonBean.setRbString(clothDeposit);
                }else if (clothDeposit.equals("无")){
                    tempUniformCommonBean.setRbString(clothDeposit);
                }else {
                    tempUniformCommonBean.setCbString(clothDeposit);
                }
                mBundle.putSerializable("tempUniformCommonBean", tempUniformCommonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, UniformFreeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_SpecCourses:
                //跳转到特色课程
                CourseBean courseBean;
                if (null != curseString) {
                    String[] split1 = curseString.split("、");
                    List<String> list1 = Arrays.asList(split1);
                    courseBean = new CourseBean();
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).equals("舞蹈")) {
                            courseBean.setDanceString("舞蹈");
                        } else if (list1.get(i).equals("美术")) {
                            courseBean.setArtString("美术");
                        } else if (list1.get(i).equals("英语")) {
                            courseBean.setEnglishString("英语");
                        } else if (list1.get(i).equals("轮滑")) {
                            courseBean.setRollString("轮滑");
                        } else if (list1.get(i).equals("钢琴")) {
                            courseBean.setPainoString("钢琴");
                        } else if (list1.get(i).equals("思维课")) {
                            courseBean.setThoughtString("思维课");
                        } else {
                            courseBean.setOtherString(list1.get(i));
                        }
                    }

                } else {
                    courseBean = new CourseBean();
                }

                mBundle.putSerializable("tempCourseBean", courseBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, CourseActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;

            case R.id.tv_classset:
                //班级配置
                CommonBean tempConfigCommonBean;
                if (null != classSet) {
                    tempConfigCommonBean = new CommonBean();
                    tempConfigCommonBean.setRbString(classSet);
                } else {
                    tempConfigCommonBean = new CommonBean();
                }
                mBundle.putSerializable("tempConfigCommonBean", tempConfigCommonBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, ConfigurationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_kindCreateTime:
                //建园时间
                TimePickerView.Type type = TimePickerView.Type.YEAR_MONTH_DAY;
                String format = "yyyy年MM月dd日";
                ViewUtils.alertTimerPicker(this, type, format, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                            tvKindCreateTime.setText(date);

                    }
                });
                break;


                //薪资发放日
            case R.id.rl_paymoney:
                ViewUtils.alertBottomWheelOption(ModiKenderDetailActivity.this, dayList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        etPaymoney.setText(dayList.get(postion));
                    }

                });
                break;
            //保险
            case R.id.rl_protection:
                //跳转到特色课程
                ProtectBean protectBean = null;
                if (null != protectString) {
                    String[] split1 = protectString.split("、");
                    List<String> list1 = Arrays.asList(split1);
                    protectBean = new ProtectBean();
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).equals("五险")) {
                            protectBean.setFiveInsurance("五险");
                        } else if (list1.get(i).equals("公积金")) {
                            protectBean.setProvidentFund("公积金");
                        } else if (list1.get(i).equals("补充医疗")) {
                            protectBean.setSuppMedInsurance("补充医疗");
                        }else {
                            protectBean.setOther(list1.get(i));
                        }
                    }

                } else {
                    protectBean = new ProtectBean();
                }

                mBundle.putSerializable("tempProtectBean", protectBean);
                intent.putExtras(mBundle);
                intent.setClass(ModiKenderDetailActivity.this, ProtactActivity.class);
                startActivity(intent);
                break;
        }


    }


}
