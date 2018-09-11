package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
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
import com.yijie.com.kindergartenapp.view.ExpandableTextView;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.tv_detailAdress)
    TextView tvDetailAdress;
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
    private String kinderId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kenderdetail);

    }

    @Override
    public void init() {
        title.setText("园所信息预览");
        tvRecommend.setText("编辑");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        kinderId = (String) SharedPreferencesUtils.getParam(KenderDetailAcitivity.this, "userId", "");
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


    }

    @Override
    protected void onResume() {
        getKenderDeail(kinderId);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend})
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
        }
    }

    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(KenderDetailAcitivity.this);
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
                    tvContactName.setText(kindergartenDetail.getKindContact());
                    tvContactPhone.setText(kindergartenDetail.getCellphone());
                    tvWachat.setText(kindergartenDetail.getWechart());
                    tvQq.setText(kindergartenDetail.getQq());
                    tvEmail.setText(kindergartenDetail.getEmail());
                    tvAdress.setText(kindergartenDetail.getKindAddress());
                    tvDetailAdress.setText(kindergartenDetail.getKindDetailAddress());
                    tvStay.setText(kindergartenDetail.getStay());
                    tvArea.setText(kindergartenDetail.getArea());
                    tvMeals.setText(kindergartenDetail.getEat());
                    String kindName = kindergartenDetail.getKindName();
                    tvKindName.setText(kindName);
                    expandTextView.setText(kindergartenDetail.getSummary());
                    tvUniform.setText(kindergartenDetail.getClothesDeposit() + "");
                    tvCheckmoney.setText(kindergartenDetail.getFirstTestFee() + "");
                    //幼儿园人数
                    tvPhonenum.setText(kindergartenDetail.getChildrenNum() + "");
                    //班级数量
                    tvClassnum.setText(kindergartenDetail.getClassNum() + "");
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

                    if (!TextUtils.isEmpty(certificate)) {
                        String[] split = certificate.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewCertificate.setAdapter(new CardAdapter(KenderDetailAcitivity.this, strings, kinderId, "certificate"));
                    }
                    if (!TextUtils.isEmpty(businessLicence)) {
                        String[] split = businessLicence.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewLicense.setAdapter(new CardAdapter(KenderDetailAcitivity.this, strings, kinderId, "license"));
                    }
                    if (!TextUtils.isEmpty(environment)) {
                        String[] split = environment.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewPictrue.setAdapter(new CardAdapter(KenderDetailAcitivity.this, strings, kinderId, "environment"));
                    }
                    if (!TextUtils.isEmpty(attachment)) {
                        String[] split = attachment.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewAttachment.setAdapter(new CardAdapter(KenderDetailAcitivity.this, strings, kinderId, "attachment"));
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
