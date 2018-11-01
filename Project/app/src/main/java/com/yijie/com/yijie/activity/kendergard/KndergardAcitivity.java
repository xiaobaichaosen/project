package com.yijie.com.yijie.activity.kendergard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.yijie.activity.student.StudentActivity;
import com.yijie.com.yijie.adapter.BigCardAdapter;
import com.yijie.com.yijie.adapter.KindCardAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.MapDistanceUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.CommomDialog;
import com.yijie.com.yijie.view.ExpandableTextView;
import com.yijie.com.yijie.view.RatioImageView;

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
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/31.
 */

public class KndergardAcitivity extends BaseActivity implements AMapLocationListener, LocationSource {


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

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_eat)
    TextView tvEat;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_road)
    TextView tvRoad;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.tv_kindName)
    TextView tvKindName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_callPhone)
    ImageView ivCallPhone;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
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
    @BindView(R.id.tv_contactName)
    TextView tvContactName;
    @BindView(R.id.tv_contactPhone)
    TextView tvContactPhone;
    @BindView(R.id.tv_wachat)
    TextView tvWachat;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.ll_access)
    LinearLayout llAccess;
    @BindView(R.id.mrb_star)
    MaterialRatingBar mrbStar;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.ll_commit)
    LinearLayout llCommit;


    private KindergartenDetail kindergartenDetail;
    private KendergardAdapterRecyclerView myAdapterRecyclerView; //The LoadMoreEducationAdapter for RecyclerVIew
    private List<Item> mList; // My List the object 'StudentBean'.
    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mListener;

    @Override
    public void setContentView() {
        setContentView(R.layout.kendergard_share_home);
    }

    @Override
    public void init() {

        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
        Bundle extras = getIntent().getExtras();
        kindergartenDetail = (KindergartenDetail) extras.getSerializable("kenderDetail");
        tvStay.setText(kindergartenDetail.getStay());
        tvArea.setText(kindergartenDetail.getArea());
        tvMeals.setText(kindergartenDetail.getEat());
        String kindName = kindergartenDetail.getKindName();
        title.setText(kindName);

        String wechart = kindergartenDetail.getWechart();
        String email = kindergartenDetail.getEmail();
        String qq = kindergartenDetail.getQq();
        tvContactName.setText(kindergartenDetail.getKindContact());
        tvContactPhone.setText(kindergartenDetail.getCellphone());
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
        if (kindAddress.length() > 6) {
            tvAdress.setText(kindAddress.substring(6, kindAddress.length()) + kindergartenDetail.getKindDetailAddress());
        } else {
            tvAdress.setText(kindAddress + kindergartenDetail.getKindDetailAddress());
        }
        if (kindAddress.length() > 6) {
            tvKindName.setText("【" + kindergartenDetail.getKindAddress().substring(6, kindAddress.length()) + "】" + kindergartenDetail.getKindName());
        } else {
            tvKindName.setText("【" + kindergartenDetail.getKindAddress() + "】" + kindergartenDetail.getKindName());
        }
        tvType.setText(kindergartenDetail.getKindType());
        tvNumber.setText(kindergartenDetail.getChildrenNum() + kindergartenDetail.getTeacherNum() + "人");
        tvMeals.setText(kindergartenDetail.getEat());
        //判断是否从新园所跳转过来，显示隐藏整体评价 TODO
        boolean isHidden = extras.getBoolean("isHidden", false);
        boolean isShow = extras.getBoolean("isShow", false);
        if (isShow) {
            llCommit.setVisibility(View.VISIBLE);
        } else {
            llCommit.setVisibility(View.GONE);
        }

        if (isHidden) {
            llAccess.setVisibility(View.GONE);
//            llCommit.setVisibility(View.VISIBLE);
        } else {
            llAccess.setVisibility(View.VISIBLE);
//            llCommit.setVisibility(View.GONE);
        }
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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager certificateLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager licenseLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager attachmentLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCertificate.setLayoutManager(certificateLinearLayoutManager);
        recyclerViewLicense.setLayoutManager(licenseLinearLayoutManager);
        recyclerViewAttachment.setLayoutManager(attachmentLinearLayoutManager);
        if (!TextUtils.isEmpty(environment)) {
            String[] split = environment.split(";");
            List<String> strings = Arrays.asList(split);
            recyclerView.setAdapter(new BigCardAdapter(KndergardAcitivity.this, strings, kindergartenDetail.getId()));

        }


        if (!TextUtils.isEmpty(certificate)) {
            String[] split = certificate.split(";");
            List<String> strings = Arrays.asList(split);
            KindCardAdapter certificateAdapter = new KindCardAdapter(KndergardAcitivity.this, strings, kindergartenDetail.getId() + "", "certificate", "kinder");
            recyclerViewCertificate.setAdapter(certificateAdapter);
            final String imageArray[] = new String[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                imageArray[i] = Constant.kinderUrl + kindergartenDetail.getId() + "/certificate/" + strings.get(i);
            }
            certificateAdapter.setOnItemClickListener(new KindCardAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(KndergardAcitivity.this, PhotoActivity.class);
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
            KindCardAdapter licenseAdapter = new KindCardAdapter(KndergardAcitivity.this, strings, kindergartenDetail.getId() + "", "license", "kinder");
            recyclerViewLicense.setAdapter(licenseAdapter);
            final String imageArray[] = new String[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                imageArray[i] = Constant.kinderUrl + kindergartenDetail.getId() + "/license/" + strings.get(i);
            }
            licenseAdapter.setOnItemClickListener(new KindCardAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(KndergardAcitivity.this, PhotoActivity.class);
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
            KindCardAdapter attachmentAdapter = new KindCardAdapter(KndergardAcitivity.this, strings, kindergartenDetail.getId() + "", "attachment", "kinder");
            recyclerViewAttachment.setAdapter(attachmentAdapter);
            final String imageArray[] = new String[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                imageArray[i] = Constant.kinderUrl + kindergartenDetail.getId() + "/attachment/" + strings.get(i);
            }
            attachmentAdapter.setOnItemClickListener(new KindCardAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(KndergardAcitivity.this, PhotoActivity.class);
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

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明


    }


    //
    @OnClick({R.id.back, R.id.iv_callPhone,R.id.tv_no, R.id.tv_yes})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_callPhone:
                new CommomDialog(this, R.style.dialog, "您确定拨打电话么？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String string) {
                        if (confirm) {

                            call(kindergartenDetail.getCellphone());
                            dialog.dismiss();
                        }

                    }
                })
                        .setTitle("提示").show();
                break;
            case R.id.tv_no:
                new CommomDialog(KndergardAcitivity.this, R.style.dialog, "", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String reason) {
                        if (confirm) {
                            commitCheck(kindergartenDetail.getId()+"","2",reason,kindergartenDetail.getId()+"");
                            dialog.dismiss();
                        }
                    }


                }).setTitle("提示").show();
                break;
            case R.id.tv_yes:
                commitCheck(kindergartenDetail.getId()+"","1","",kindergartenDetail.getId()+"");
                break;


        }
    }

    /**
     * 审核园所状态
     * @param kinderId
     * @param status
     * @param notpassReason
     * @param alias
     */
   private void commitCheck(String kinderId,String status,String notpassReason,String alias ){
       final HttpUtils instance = HttpUtils.getinstance(KndergardAcitivity.this);
       Map map = new HashMap();
       map.put("kinderId", kinderId);
       map.put("status", status);
       map.put("notpassReason", notpassReason);
       map.put("alias", alias);
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

                       ShowToastUtils.showToastMsg(KndergardAcitivity.this, "审核成功!");
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
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                String detail = aMapLocation.getDistrict();

                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                String address = aMapLocation.getAddress();
                String description = aMapLocation.getDescription();
                LogUtil.e(detail + "------" + address + "----" + description);
                LogUtil.e(detail + "------" + latitude + "----" + latitude);
                LogUtil.e(detail + "------" + longitude + "----" + longitude);

                tvRoad.setText("距您:" + Math.round(MapDistanceUtils.GetDistance(latitude, longitude, Double.parseDouble(kindergartenDetail.getLatitude()), Double.parseDouble(kindergartenDetail.getLongitude()))) + "km");
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            }
        }
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    @SuppressLint("MissingPermission")
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }


    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }


}
