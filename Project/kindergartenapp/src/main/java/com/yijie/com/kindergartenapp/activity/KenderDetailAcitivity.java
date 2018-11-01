package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.CardAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.view.ExpandableTextView;
import com.yijie.com.kindergartenapp.view.RatioImageView;

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
 * 园所详情
 */

public class KenderDetailAcitivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_contactName)
    TextView tvContactName;
    @BindView(R.id.tv_contactPhone)
    TextView tvContactPhone;
    @BindView(R.id.tv_wachat)
    TextView tvWachat;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_phonenum)
    TextView tvPhonenum;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_classConfig)
    TextView tvClassConfig;
    @BindView(R.id.tv_SpecCourses)
    TextView tvSpecCourses;
    @BindView(R.id.tv_stay)
    TextView tvStay;
    @BindView(R.id.tv_meals)
    TextView tvMeals;
    @BindView(R.id.tv_checkmoney)
    TextView tvCheckmoney;
    KindergartenDetail kindergartenDetail;
    @BindView(R.id.tv_uniform)
    TextView tvUniform;

    @BindView(R.id.recycler_view_certificate)
    RecyclerView recyclerViewCertificate;
    @BindView(R.id.recycler_view_license)
    RecyclerView recyclerViewLicense;
    @BindView(R.id.recycler_view_pictrue)
    RecyclerView recyclerViewPictrue;
    @BindView(R.id.recycler_view_attachment)
    RecyclerView recyclerViewAttachment;
    @BindView(R.id.tv_kindName)
    TextView tvKindName;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_kindCreateTime)
    TextView tvKindCreateTime;
    @BindView(R.id.et_payKind)
    TextView etPayKind;
    @BindView(R.id.et_kinderNum)
    TextView etKinderNum;
    @BindView(R.id.et_paymoney)
    TextView etPaymoney;
    @BindView(R.id.tv_protection)
    TextView tvProtection;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String kinderId;
    private String userId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kenderdetail);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public void init() {
        title.setText("园所信息预览");
        tvRecommend.setText("编辑");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        kinderId = (String) SharedPreferencesUtils.getParam(KenderDetailAcitivity.this, "cellphone", "");
        userId = (String) SharedPreferencesUtils.getParam(KenderDetailAcitivity.this, "kinderId", "");
        //保证图片在手机屏幕中间显示
        LinearSnapHelper mLinearySnapHelper = new LinearSnapHelper();
        LinearLayoutManager certificateLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager licenseLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager pictureLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager attachmentLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

//        mLinearySnapHelper.attachToRecyclerView(recyclerViewCertificate);
        recyclerViewCertificate.setLayoutManager(certificateLinearLayoutManager);


//        mLinearySnapHelper.attachToRecyclerView(recyclerViewLicense);
        recyclerViewLicense.setLayoutManager(licenseLinearLayoutManager);

//        mLinearySnapHelper.attachToRecyclerView(recyclerViewPictrue);
        recyclerViewPictrue.setLayoutManager(pictureLinearLayoutManager);

//        mLinearySnapHelper.attachToRecyclerView(recyclerViewAttachment);
        recyclerViewAttachment.setLayoutManager(attachmentLinearLayoutManager);

        getKenderDeail(kinderId);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                Intent intent = new Intent();
                intent.setClass(KenderDetailAcitivity.this, ModiKenderDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_commit:
                //发送审核接口

                if (TextUtils.isEmpty(kindergartenDetail.getKindName())
                        ||TextUtils.isEmpty(kindergartenDetail.getKindAddress())
                        ||TextUtils.isEmpty(kindergartenDetail.getKindContact())
                        ||TextUtils.isEmpty(kindergartenDetail.getKindType())
                        ||TextUtils.isEmpty(kindergartenDetail.getArea())
                        ||TextUtils.isEmpty(kindergartenDetail.getChildrenNum()+"")
                        ||TextUtils.isEmpty(kindergartenDetail.getClassNum()+"")
                        ||TextUtils.isEmpty(kindergartenDetail.getClassSet())
                        ||TextUtils.isEmpty(kindergartenDetail.getTeacherNum()+"")
                        ||TextUtils.isEmpty(kindergartenDetail.getStay())
                        ||TextUtils.isEmpty(kindergartenDetail.getEat())
                        ||TextUtils.isEmpty(kindergartenDetail.getClothesDeposit())
                        ||TextUtils.isEmpty(kindergartenDetail.getFirstTestFee())
                        ||TextUtils.isEmpty(kindergartenDetail.getBusinessLicence())
                        ||TextUtils.isEmpty(kindergartenDetail.getEnvironment())
                        ||TextUtils.isEmpty(kindergartenDetail.getAttachment())
                        ) {
                    ShowToastUtils.showToastMsg(this, "请填写所有带星号的必填项");
                    return;
                }
//                if (TextUtils.isEmpty(kindergartenDetail.getKindName())) {
//                    ShowToastUtils.showToastMsg(this, "请添写园所全称");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getKindAddress())) {
//                    ShowToastUtils.showToastMsg(this, "请添写园所地址");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getKindContact())) {
//                    ShowToastUtils.showToastMsg(this, "请添写园所联系人");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getKindType())) {
//                    ShowToastUtils.showToastMsg(this, "请添写园所类别");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getArea())) {
//                    ShowToastUtils.showToastMsg(this, "请添写占地面积");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getChildrenNum() + "")) {
//                    ShowToastUtils.showToastMsg(this, "请添写幼儿人数");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getClassNum() + "")) {
//                    ShowToastUtils.showToastMsg(this, "请添写班级数量");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getClassSet())) {
//                    ShowToastUtils.showToastMsg(this, "请添写班级配置");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getTeacherNum() + "")) {
//                    ShowToastUtils.showToastMsg(this, "请添写职工人数");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getStay())) {
//                    ShowToastUtils.showToastMsg(this, "请添写住宿安排");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getEat())) {
//                    ShowToastUtils.showToastMsg(this, "请添写三餐安排");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getClothesDeposit())) {
//                    ShowToastUtils.showToastMsg(this, "请添写工服押金");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getFirstTestFee())) {
//                    ShowToastUtils.showToastMsg(this, "请添写首年体检费");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getBusinessLicence())) {
//                    ShowToastUtils.showToastMsg(this, "请上传营业执照");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getEnvironment())) {
//                    ShowToastUtils.showToastMsg(this, "请上传园所照片");
//                    return;
//                }
//                if (TextUtils.isEmpty(kindergartenDetail.getAttachment())) {
//                    ShowToastUtils.showToastMsg(this, "请上传宿舍环境");
//                    return;
//                }
                final HttpUtils instance = HttpUtils.getinstance(KenderDetailAcitivity.this);
                Map map = new HashMap();
                map.put("kinderId", userId);
                map.put("status", "3");
                map.put("notpassReason", "");
                map.put("alias", userId);
                instance.post(Constant.UPDATEKINDERAUDITSTATUS, map, new BaseCallback<String>() {
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
                            if (rescode.equals("200")) {
                                ShowToastUtils.showToastMsg(KenderDetailAcitivity.this, "发送审核成功!");
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

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void bean(String string) {
        if (string.equals("refrsh")) {
            //数据改变刷新页面
            getKenderDeail(kinderId);
        }
    }

    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(KenderDetailAcitivity.this);
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
                    kindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);
                    tvContactName.setText(kindergartenDetail.getKindContact());
                    tvContactPhone.setText(kindergartenDetail.getCellphone());
                    int auditStatus = kindergartenDetail.getAuditStatus();
                    if (auditStatus == 0) {
                        tvCommit.setText("提交审核");
                    }else if (auditStatus==3){
                        tvCommit.setText("待审核");
                    }else if (auditStatus==1){
                        tvCommit.setText("审核通过");
                    }else if (auditStatus==2){
                        tvCommit.setText("审核未通过");
                    }

                    String wechart = kindergartenDetail.getWechart();
                    String email = kindergartenDetail.getEmail();
                    String qq = kindergartenDetail.getQq();

                    if (TextUtils.isEmpty(wechart)) {
                        tvWachat.setText("未填写");
                    } else {
                        tvWachat.setText(wechart);
                    }
                    if (TextUtils.isEmpty(email)) {
                        tvEmail.setText("未填写");
                    } else {
                        tvEmail.setText(email);
                    }
                    if (TextUtils.isEmpty(qq)) {
                        tvQq.setText("未填写");
                    } else {
                        tvQq.setText(qq);
                    }
//                    tvWachat.setText(kindergartenDetail.getWechart());
//                    tvQq.setText(kindergartenDetail.getQq());
//                    tvEmail.setText(kindergartenDetail.getEmail());
//                    tvAdress.setText(kindergartenDetail.getKindAddress());
                    String kindAddress = kindergartenDetail.getKindAddress();
                    if (kindAddress.length() > 6) {
                        tvAdress.setText(kindAddress.substring(6, kindAddress.length()) + kindergartenDetail.getKindDetailAddress());
                    } else {
                        tvAdress.setText(kindAddress + kindergartenDetail.getKindDetailAddress());
                    }
                    tvStay.setText(kindergartenDetail.getStay());
                    if (TextUtils.isEmpty(kindergartenDetail.getArea())) {
                        tvArea.setText("未填写");
                    } else {
                        tvArea.setText(kindergartenDetail.getArea() + "㎡");
                    }
                    tvMeals.setText(kindergartenDetail.getEat());
                    String kindName = kindergartenDetail.getKindName();
                    tvKindName.setText(kindName);
                    expandTextView.setText(kindergartenDetail.getSummary());
                    tvUniform.setText(kindergartenDetail.getClothesDeposit() + "");
                    tvCheckmoney.setText(kindergartenDetail.getFirstTestFee() + "");
                    //幼儿园人数
                    tvPhonenum.setText(kindergartenDetail.getChildrenNum() + "人");
                    //班级数量
                    tvClassnum.setText(kindergartenDetail.getClassNum() + "个");
                    tvClassConfig.setText(kindergartenDetail.getClassSet());
                    //特色课程
                    tvSpecCourses.setText(kindergartenDetail.getFeatureCourse());
                    //园所类别
                    tvCategory.setText(kindergartenDetail.getKindType());
                    //园所级别
                    tvLevel.setText(kindergartenDetail.getKindLevel());
                    //荣誉证书
                    String certificate = kindergartenDetail.getCertificate();
                    //营业执照
                    String businessLicence = kindergartenDetail.getBusinessLicence();
                    //园所照片
                    String environment = kindergartenDetail.getEnvironment();
                    //宿舍环境图片
                    String attachment = kindergartenDetail.getAttachment();
                    //建园日期
                    tvKindCreateTime.setText(kindergartenDetail.getBuildGardenDate());
                    //职工人数

                    Integer teacherNum = kindergartenDetail.getTeacherNum();
                    if (null == teacherNum) {
                        etKinderNum.setText("未填写");
                    } else {
                        etKinderNum.setText(teacherNum + "人");
                    }
                    //托费
                    etPayKind.setText(kindergartenDetail.getNuseryFee() + "元");
                    //薪资发放日
                    etPaymoney.setText(kindergartenDetail.getSalaryGrantDate());
                    //保险
                    tvProtection.setText(kindergartenDetail.getFormalWelfare());
                    if (!TextUtils.isEmpty(certificate)) {
                        String[] split = certificate.split(";");
                        List<String> strings = Arrays.asList(split);
                        CardAdapter certificateAdapter = new CardAdapter(KenderDetailAcitivity.this, strings, userId, "certificate");
                        recyclerViewCertificate.setAdapter(certificateAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.certificateUrl + userId + "/certificate/" + strings.get(i);
                        }
                        certificateAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KenderDetailAcitivity.this, PhotoActivity.class);
                                int childCount = recyclerViewCertificate.getChildCount();
                                ArrayList<Rect> rects = new ArrayList<>();
                                for (int k = 0; k < childCount; k++) {
                                    Rect rect = new Rect();
                                    View child = recyclerViewCertificate.getChildAt(k);
                                    if (child instanceof RatioImageView) {
                                        child.getGlobalVisibleRect(rect);
                                        rects.add(rect);
                                    }
                                }
                                intent.putExtra("imgUrls", imageArray);
                                intent.putExtra("index", position);
                                intent.putExtra("bounds", rects);
                                startActivity(intent);
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(businessLicence)) {
                        String[] split = businessLicence.split(";");
                        final List<String> strings = Arrays.asList(split);
                        CardAdapter licenseAdapter = new CardAdapter(KenderDetailAcitivity.this, strings, userId, "license");
                        recyclerViewLicense.setAdapter(licenseAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.certificateUrl + userId + "/license/" + strings.get(i);
                        }
                        licenseAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KenderDetailAcitivity.this, PhotoActivity.class);
                                int childCount = recyclerViewLicense.getChildCount();
                                ArrayList<Rect> rects = new ArrayList<>();
                                for (int k = 0; k < childCount; k++) {
                                    Rect rect = new Rect();
                                    View child = recyclerViewLicense.getChildAt(k);
                                    if (child instanceof RatioImageView) {
                                        child.getGlobalVisibleRect(rect);
                                        rects.add(rect);
                                    }
                                }
                                intent.putExtra("imgUrls", imageArray);
                                intent.putExtra("index", position);
                                intent.putExtra("bounds", rects);
                                startActivity(intent);
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(environment)) {
                        String[] split = environment.split(";");
                        List<String> strings = Arrays.asList(split);
                        CardAdapter environmentAdapter = new CardAdapter(KenderDetailAcitivity.this, strings, userId, "environment");
                        recyclerViewPictrue.setAdapter(environmentAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.certificateUrl + userId + "/environment/" + strings.get(i);
                        }
                        environmentAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KenderDetailAcitivity.this, PhotoActivity.class);
                                int childCount = recyclerViewPictrue.getChildCount();
                                ArrayList<Rect> rects = new ArrayList<>();
                                for (int k = 0; k < childCount; k++) {
                                    Rect rect = new Rect();
                                    View child = recyclerViewPictrue.getChildAt(k);
                                    if (child instanceof RatioImageView) {
                                        child.getGlobalVisibleRect(rect);
                                        rects.add(rect);
                                    }
                                }
                                intent.putExtra("imgUrls", imageArray);
                                intent.putExtra("index", position);
                                intent.putExtra("bounds", rects);
                                startActivity(intent);
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(attachment)) {
                        String[] split = attachment.split(";");
                        List<String> strings = Arrays.asList(split);
                        CardAdapter attachmentAdapter = new CardAdapter(KenderDetailAcitivity.this, strings, userId, "attachment");
                        recyclerViewAttachment.setAdapter(attachmentAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.certificateUrl + userId + "/attachment/" + strings.get(i);
                        }
                        attachmentAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KenderDetailAcitivity.this, PhotoActivity.class);
                                int childCount = recyclerViewAttachment.getChildCount();
                                ArrayList<Rect> rects = new ArrayList<>();
                                for (int k = 0; k < childCount; k++) {
                                    Rect rect = new Rect();
                                    View child = recyclerViewAttachment.getChildAt(k);
                                    if (child instanceof RatioImageView) {
                                        child.getGlobalVisibleRect(rect);
                                        rects.add(rect);
                                    }
                                }
                                intent.putExtra("imgUrls", imageArray);
                                intent.putExtra("index", position);
                                intent.putExtra("bounds", rects);
                                startActivity(intent);
                            }
                        });
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
}
