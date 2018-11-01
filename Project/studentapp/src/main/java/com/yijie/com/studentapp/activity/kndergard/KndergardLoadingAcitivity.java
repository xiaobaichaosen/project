package com.yijie.com.studentapp.activity.kndergard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.kndergard.kndergardadapter.Item;
import com.yijie.com.studentapp.activity.kndergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.studentapp.adapter.BigCardAdapter;
import com.yijie.com.studentapp.adapter.CardAdapter;
import com.yijie.com.studentapp.adapter.KindCardAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.KindergartenDetail;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CommomDialog;
import com.yijie.com.studentapp.view.ExpandableTextView;
import com.yijie.com.studentapp.view.RatioImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/31.
 * 正在招聘的园所
 */

public class KndergardLoadingAcitivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_confrim)
    TextView tvConfrim;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;



    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_kindCreateTime)
    TextView tvKindCreateTime;
    @BindView(R.id.tv_phonenum)
    TextView tvPhonenum;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_classConfig)
    TextView tvClassConfig;
    @BindView(R.id.et_payKind)
    TextView etPayKind;
    @BindView(R.id.et_kinderNum)
    TextView etKinderNum;
    @BindView(R.id.et_paymoney)
    TextView etPaymoney;
    @BindView(R.id.tv_SpecCourses)
    TextView tvSpecCourses;
    @BindView(R.id.tv_stay)
    TextView tvStay;
    @BindView(R.id.tv_meals)
    TextView tvMeals;
    @BindView(R.id.tv_uniform)
    TextView tvUniform;
    @BindView(R.id.tv_checkmoney)
    TextView tvCheckmoney;
    @BindView(R.id.tv_protection)
    TextView tvProtection;
    @BindView(R.id.recycler_view_license)
    RecyclerView recyclerViewLicense;
    @BindView(R.id.recycler_view_pictrue)
    RecyclerView recyclerViewPictrue;
    @BindView(R.id.recycler_view_attachment)
    RecyclerView recyclerViewAttachment;
    @BindView(R.id.recycler_view_certificate)
    RecyclerView recyclerViewCertificate;


    @BindView(R.id.mrb_star)
    MaterialRatingBar mrbStar;
    @BindView(R.id.ll_access)
    LinearLayout llAccess;
    @BindView(R.id.cb_collect)
    CheckBox cbCollect;
    @BindView(R.id.cb_look)
    CheckBox cbLook;


    private String kinderNeedId;
    private KendergardAdapterRecyclerView myAdapterRecyclerView; //The LoadMoreEducationAdapter for RecyclerVIew
    private List<Item> mList; // My List the object 'StudentBean'.
    private KindergartenDetail kindergartenDetail;
    private int resumeCount;
    private String kinderId;
    private String status;
    private String schoolId;

    @Override
    public void setContentView() {
        setContentView(R.layout.kendergard_loading_home);
    }

    @Override
    public void init() {
        schoolId = (String) SharedPreferencesUtils.getParam(this, "schoolId", "");
        kinderId = getIntent().getStringExtra("kinderId");
        kinderNeedId = getIntent().getStringExtra("kinderNeedId");
        status = getIntent().getStringExtra("status");
        if (status.equals("0")) {
            tvConfrim.setText("已投递");
            tvConfrim.setBackgroundColor(getResources().getColor(R.color.app_background));
        } else {
            tvConfrim.setText("投简历");
            tvConfrim.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        getKenderDeail(kinderId);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
//        LinearLayoutManager licenseLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewLicense.setLayoutManager(licenseLinearLayoutManager);
//
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        //保证图片在手机屏幕中间显示
//        LinearSnapHelper mLinearySnapHelper = new LinearSnapHelper();
//        mLinearySnapHelper.attachToRecyclerView(recyclerView);
//        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(final String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(KndergardLoadingAcitivity.this);
        Map map = new HashMap();
        map.put("id", kenderId);

        instance.post(Constant.KINDERGARTENDETAILBYID, map, new BaseCallback<String>() {

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
                    String kindName = kindergartenDetail.getKindName();
                    title.setText(kindName);
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
                    // 地址
                    String kindAddress = kindergartenDetail.getKindAddress();


                    String wholeEvaluate = kindergartenDetail.getWholeEvaluate();
                    if (!TextUtils.isEmpty(wholeEvaluate)) {
                        mrbStar.setRating(Float.parseFloat(wholeEvaluate));
                    }

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
                    //保证图片在手机屏幕中间显示
                    LinearSnapHelper mLinearySnapHelper = new LinearSnapHelper();
                    mLinearySnapHelper.attachToRecyclerView(recyclerView);

                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KndergardLoadingAcitivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    LinearLayoutManager certificateLinearLayoutManager = new LinearLayoutManager(KndergardLoadingAcitivity.this, LinearLayoutManager.HORIZONTAL, false);
                    LinearLayoutManager licenseLinearLayoutManager = new LinearLayoutManager(KndergardLoadingAcitivity.this, LinearLayoutManager.HORIZONTAL, false);
                    LinearLayoutManager attachmentLinearLayoutManager = new LinearLayoutManager(KndergardLoadingAcitivity.this, LinearLayoutManager.HORIZONTAL, false);

                    recyclerViewCertificate.setLayoutManager(certificateLinearLayoutManager);
                    recyclerViewLicense.setLayoutManager(licenseLinearLayoutManager);
                    recyclerViewAttachment.setLayoutManager(attachmentLinearLayoutManager);
                    if (!TextUtils.isEmpty(environment)) {
                        String[] split = environment.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerView.setAdapter(new BigCardAdapter(KndergardLoadingAcitivity.this, strings, kindergartenDetail.getId()));

                    }


                    if (!TextUtils.isEmpty(certificate)) {
                        String[] split = certificate.split(";");
                        List<String> strings = Arrays.asList(split);
                        KindCardAdapter certificateAdapter = new KindCardAdapter(KndergardLoadingAcitivity.this, strings, kindergartenDetail.getId() + "", "certificate", "kinder");
                        recyclerViewCertificate.setAdapter(certificateAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.kinderUrl + kindergartenDetail.getId() + "/certificate/" + strings.get(i);
                        }
                        certificateAdapter.setOnItemClickListener(new KindCardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KndergardLoadingAcitivity.this, PhotoActivity.class);
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
                        KindCardAdapter licenseAdapter = new KindCardAdapter(KndergardLoadingAcitivity.this, strings, kindergartenDetail.getId() + "", "license", "kinder");
                        recyclerViewLicense.setAdapter(licenseAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.kinderUrl + kindergartenDetail.getId() + "/license/" + strings.get(i);
                        }
                        licenseAdapter.setOnItemClickListener(new KindCardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KndergardLoadingAcitivity.this, PhotoActivity.class);
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


                    if (!TextUtils.isEmpty(attachment)) {
                        String[] split = attachment.split(";");
                        List<String> strings = Arrays.asList(split);
                        KindCardAdapter attachmentAdapter = new KindCardAdapter(KndergardLoadingAcitivity.this, strings, kindergartenDetail.getId() + "", "attachment", "kinder");
                        recyclerViewAttachment.setAdapter(attachmentAdapter);
                        final String imageArray[] = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            imageArray[i] = Constant.kinderUrl + kindergartenDetail.getId() + "/attachment/" + strings.get(i);
                        }
                        attachmentAdapter.setOnItemClickListener(new KindCardAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(KndergardLoadingAcitivity.this, PhotoActivity.class);
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
//                    tvStay.setText(kindergartenDetail.getStay());
//                    tvArea.setText(kindergartenDetail.getArea());
//                    tvMeals.setText(kindergartenDetail.getEat());
//                    String kindName = kindergartenDetail.getKindName();
//                    title.setText(kindName);
//                    expandableText.setText(kindergartenDetail.getSummary());
//                    tvUniform.setText(kindergartenDetail.getClothesDeposit() + "");
//                    tvCheckmoney.setText(kindergartenDetail.getFirstTestFee() + "");
//                    //幼儿园人数
//                    tvPhonenum.setText(kindergartenDetail.getChildrenNum() + "");
//                    //班级数量
//                    tvClassnum.setText(kindergartenDetail.getClassNum() + "");
//                    tvClassConfig.setText(kindergartenDetail.getClassSet());
//                    //特色课程
//                    tvSpecCourses.setText(kindergartenDetail.getFeatureCourse());
//                    //园所类别
//                    tvCategory.setText(kindergartenDetail.getKindType());
//                    //园所级别
//                    tvLevel.setText(kindergartenDetail.getKindLevel());
//
//                    //营业执照
//                    String businessLicence = kindergartenDetail.getBusinessLicence();
//                    //园所照片
//                    String environment = kindergartenDetail.getEnvironment();
//                    if (!TextUtils.isEmpty(businessLicence)) {
//                        String[] split = businessLicence.split(";");
//                        List<String> strings = Arrays.asList(split);
//                        recyclerViewLicense.setAdapter(new CardAdapter(KndergardLoadingAcitivity.this, strings, kenderId + "", "license", "kinder"));
//                    }
//                    if (!TextUtils.isEmpty(environment)) {
//                        String[] split = environment.split(";");
//                        List<String> strings = Arrays.asList(split);
//                        recyclerView.setAdapter(new BigCardAdapter(KndergardLoadingAcitivity.this, strings, Integer.parseInt(kenderId)));
//                    }

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

    /**
     * 通过学生id查询简历详细
     */
    public void getSample(String userId) {
        final HttpUtils instance = HttpUtils.getinstance(KndergardLoadingAcitivity.this);
        Map map = new HashMap();
        map.put("studentUserId", userId);

        instance.post(Constant.SELECTBYSTUDENTUSERID, map, new BaseCallback<String>() {
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
                LogUtil.e("sample====" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        int companionId = data.getInt("companionId");
                        int studentUserId = data.getInt("studentUserId");
                        resumeCount = data.getInt("resumeCount");
                        if (resumeCount == 3) {
                            ShowToastUtils.showToastMsg(KndergardLoadingAcitivity.this, "三次投递机会已经用完");
                        } else {
                            postSample(studentUserId + "", kinderNeedId, companionId + "", kinderId, schoolId, "0", kinderId);
                        }
                    } else if (rescode.equals("300")) {
                        ShowToastUtils.showToastMsg(KndergardLoadingAcitivity.this, "请先填写简历！");
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

    //
    @OnClick({R.id.back, R.id.tv_confrim})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confrim:
                String userId = (String) SharedPreferencesUtils.getParam(KndergardLoadingAcitivity.this, "id", "");
                if (status.equals("0")) {
                    ShowToastUtils.showToastMsg(KndergardLoadingAcitivity.this, "已投递的园所不能再投递");

                } else {
                    getSample(userId);
                }


                break;

        }
    }

    /**
     * 投递简历
     */
    public void postSample(String studentUserId, String kinderNeedId, String companionId, String kinderId, String schoolId, String state, String alias) {
        final HttpUtils instance = HttpUtils.getinstance(KndergardLoadingAcitivity.this);
        Map map = new HashMap();
        map.put("studentUserId", studentUserId);
        map.put("kinderNeedId", kinderNeedId);
        map.put("companionId", companionId);
        map.put("kinderId", kinderId);
        map.put("schoolId", schoolId);
        map.put("state", state);
        map.put("alias", alias);

        instance.post(Constant.RESUMESTATEUPDATE, map, new BaseCallback<String>() {

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
                    if (jsonObject.getString("rescode").equals("200")) {
                        new CommomDialog(KndergardLoadingAcitivity.this, R.style.dialog, "您还有" + (3 - resumeCount - 1) + "个园所可投递简历", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm, String sContent) {
                                if (confirm) {
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    finish();
                                }
                            }


                        })
                                .setTitle("投递成功")
                                .setPositiveButton("继续投递")
                                .show();
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

    @OnCheckedChanged({R.id.cb_look, R.id.cb_collect})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_look:
                if (ischanged) {

                }
                break;
            case R.id.cb_collect:
                if (ischanged) {
                    ShowToastUtils.showToastMsg(KndergardLoadingAcitivity.this, "收藏成功");
                } else {
                    ShowToastUtils.showToastMsg(KndergardLoadingAcitivity.this, "取消收藏");
                }
                break;

        }
    }

    /**
     * 收藏园所
     */
    public void collectKender(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(this);
        Map map = new HashMap();
        map.put("id", kenderId);

        instance.post(Constant.KINDERGARTENDETAILBYID, map, new BaseCallback<String>() {

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
}
