package com.yijie.com.yijie.activity.kendergard;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.yijie.adapter.BigCardAdapter;
import com.yijie.com.yijie.adapter.CardAdapter;
import com.yijie.com.yijie.adapter.KindCardAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.view.ExpandableTextView;

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
 * Created by 奕杰平台 on 2018/1/31.
 */

public class Kndergard2KeedAcitivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;


    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
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
    @BindView(R.id.tv_uniform)
    TextView tvUniform;
    @BindView(R.id.tv_checkmoney)
    TextView tvCheckmoney;
    @BindView(R.id.recycler_view_license)
    RecyclerView recyclerViewLicense;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view_certificate)
    RecyclerView recyclerViewCertificate;
    @BindView(R.id.recycler_view_attachment)
    RecyclerView recyclerViewAttachment;

    private KendergardAdapterRecyclerView myAdapterRecyclerView; //The LoadMoreEducationAdapter for RecyclerVIew
    private List<Item> mList; // My List the object 'StudentBean'.


    @Override
    public void setContentView() {
        setContentView(R.layout.kendergard_share_home);
    }

    @Override
    public void init() {


        Bundle extras = getIntent().getExtras();
        String kinderName = extras.getString("kinderName");
        title.setText(kinderName);
        String kinderId = extras.getString("kinderId");
        //保证图片在手机屏幕中间显示
        LinearSnapHelper mLinearySnapHelper = new LinearSnapHelper();
        mLinearySnapHelper.attachToRecyclerView(recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager certificateLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager licenseLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager attachmentLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCertificate.setLayoutManager(certificateLinearLayoutManager);
        recyclerViewLicense.setLayoutManager(licenseLinearLayoutManager);
        recyclerViewAttachment.setLayoutManager(attachmentLinearLayoutManager);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        getKenderDeail(kinderId);




    }

    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(final String kenderId) {
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
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    KindergartenDetail kindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);

                    tvStay.setText(kindergartenDetail.getStay());
                    tvArea.setText(kindergartenDetail.getArea());
                    tvMeals.setText(kindergartenDetail.getEat());
                    String kindName = kindergartenDetail.getKindName();
                    title.setText(kindName);
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

                    if (!TextUtils.isEmpty(environment)) {
                        String[] split = environment.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerView.setAdapter(new BigCardAdapter(Kndergard2KeedAcitivity.this, strings,kindergartenDetail.getId()));

                    }
                    if (!TextUtils.isEmpty(certificate)) {
                        String[] split = certificate.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewCertificate.setAdapter(new KindCardAdapter(Kndergard2KeedAcitivity.this, strings, kindergartenDetail.getId()+"", "certificate", "kinder"));
                    }
                    if (!TextUtils.isEmpty(businessLicence)) {
                        String[] split = businessLicence.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewLicense.setAdapter(new KindCardAdapter(Kndergard2KeedAcitivity.this, strings, kindergartenDetail.getId()+"", "license", "kinder"));
                    }

                    if (!TextUtils.isEmpty(attachment)) {
                        String[] split = attachment.split(";");
                        List<String> strings = Arrays.asList(split);
                        recyclerViewAttachment.setAdapter(new KindCardAdapter(Kndergard2KeedAcitivity.this, strings, kindergartenDetail.getId()+"", "attachment", "kinder"));
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
    @OnClick({R.id.back})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
