package com.yijie.com.yijie.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.MapLocationActivity;
import com.yijie.com.yijie.activity.ProjectDetailActivity;
import com.yijie.com.yijie.activity.TrainDetailAcitity;
import com.yijie.com.yijie.activity.newschool.NewInternshipDetailActivity;
import com.yijie.com.yijie.activity.newschool.NewIntershipMoney;
import com.yijie.com.yijie.activity.newschool.NewSchoolLiaisonsActivity;
import com.yijie.com.yijie.base.APPApplication;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.bean.SchoolProjectDetail;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.bean.school.SchoolTrainDetail;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/5/17.
 * 项目详情
 * 从实习项目进去的项目详情
 */

public class ProjectDetailFragment extends BaseFragment  {

    @BindView(R.id.rl_shipDetil)
    RelativeLayout rlShipDetil;
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
    @BindView(R.id.ll_shipDetil)
    LinearLayout llShipDetil;
    @BindView(R.id.rl_shipmoney)
    RelativeLayout rlShipmoney;
    @BindView(R.id.tv_duringDate)
    TextView tvDuringDate;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_manageMoney)
    TextView tvManageMoney;
    @BindView(R.id.ll_shipmoney)
    LinearLayout llShipmoney;
    @BindView(R.id.rl_school_contact)
    RelativeLayout rlSchoolContact;
    @BindView(R.id.tv_scool_contact)
    TextView tvScoolContact;
    @BindView(R.id.tv_)
    TextView tv;
    @BindView(R.id.tv_trainDate)
    TextView tvTrainDate;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.ll_school_contact)
    LinearLayout llSchoolContact;

    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.rl_trainDetail)
    RelativeLayout rlTrainDetail;
    @BindView(R.id.tv_arriveTime)
    TextView tvArriveTime;
    @BindView(R.id.tv_arriveMetch)
    TextView tvArriveMetch;
    @BindView(R.id.tv_arriveNum)
    TextView tvArriveNum;
    @BindView(R.id.tv_arriveMoney)
    TextView tvArriveMoney;
    @BindView(R.id.tv_shipHight)
    TextView tvShipHight;
    @BindView(R.id.tv_finishTime)
    TextView tvFinishTime;
    @BindView(R.id.ll_trainDetail)
    LinearLayout llTrainDetail;
    @BindView(R.id.loading)
    LinearLayout loading;
    @BindView(R.id.tv_shipdetail)
    TextView tvShipdetail;
    @BindView(R.id.tv_shipmoney)
    TextView tvShipmoney;
    @BindView(R.id.tv_school_contact)
    TextView tvSchoolContact;
    @BindView(R.id.tv_trainDetail)
    TextView tvTrainDetail;
    private Unbinder unbinder;
    //项目详情id
    String schoolPracticeId;
    //接口获取的项目详情
    public SchoolPractice schoolPractice;
    //接口获取到的实习薪资
    public SchoolSalaryInfo schoolSalaryInfo;
    //接口获取到的培训详情（开发老师）
    public SchoolTrainDetail schoolTrainDetail;
    //学校id
    private  Integer schoolId;
    private  int userId = 0;
    //培训详情id
    private  Integer schoolTrainId;
    private    boolean isShowLayout;
    private StatusLayoutManager statusLayoutManager;
    //培训学校的经纬度
    private  String latitude;
    private String longitude;
    private ProjectDetailActivity projectDetailActivity;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_detail;
    }

    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        super.onResume();
    }

    @Override
    protected void initView() {

        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(loading)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        getProjecDetil(schoolPracticeId);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        getProjecDetil(schoolPracticeId);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        //获取父activity中得详情id

        projectDetailActivity = (ProjectDetailActivity) mActivity;
        schoolPracticeId = projectDetailActivity.practiceId;
        isShowLayout = projectDetailActivity.isShowLayout;
        if (isShowLayout) {
            btnConfirm.setVisibility(View.VISIBLE);
            tvShipmoney.setVisibility(View.GONE);
            tvTrainDetail.setVisibility(View.GONE);
            tvSchoolContact.setVisibility(View.GONE);
            tvShipdetail.setVisibility(View.GONE);
        }
        String json = (String) SharedPreferencesUtils.getParam(mActivity, "user", "");
        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        getProjecDetil(schoolPracticeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }


    /**
     * 查询项目详细
     *
     * @param schoolPracticeId
     */
    public void getProjecDetil(String schoolPracticeId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolPracticeId", schoolPracticeId);
        getinstance.post(Constant.SCHOOLPROJECTDETAIL, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    SchoolProjectDetail schoolProjectDetail = gson.fromJson(data.toString(), SchoolProjectDetail.class);
                    schoolPractice = schoolProjectDetail.getSchoolPractice();
                    schoolSalaryInfo = schoolProjectDetail.getSchoolSalaryInfo();
                    //实习详情
                    schoolTrainDetail = schoolProjectDetail.getSchoolTrainDetail();
                    tvYear.setText(schoolPractice.getYear());
                    tvEducation.setText(schoolPractice.getEducation());
                    tvDuring.setText(schoolPractice.getTimelimit());
                    tvMonth.setText(schoolPractice.getPracticeMonth());
                    tvMode.setText(schoolPractice.getManageModel());
                    tvType.setText(schoolPractice.getPracticeType());
                    tvAppoitTime.setText(schoolPractice.getOrderTime());
                    projectDetailActivity.kindpeoNumSet=schoolPractice.getKindpeoNumSet()+"";
                    schoolId = schoolPractice.getSchoolId();
                    //实习薪资
                    if (null != schoolSalaryInfo) {
                        llShipmoney.setVisibility(View.VISIBLE);
                        tvDuringDate.setText(schoolSalaryInfo.getPeriod());
                        tvMoney.setText(schoolSalaryInfo.getSalary().toString());
                        tvManageMoney.setText(schoolSalaryInfo.getManageFee().toString());
                    }
                    //学校联络人和培训详情
                    if (null != schoolTrainDetail) {
                        llSchoolContact.setVisibility(View.VISIBLE);
                        schoolTrainId = schoolTrainDetail.getId();
                        tvScoolContact.setText(schoolTrainDetail.getSchoolContactName());
                        tv.setText(schoolTrainDetail.getSchoolContactCelphone());
                        tvTrainDate.setText(schoolTrainDetail.getTrainTime());
                        tvAdress.setText(schoolTrainDetail.getDetailAddress());
                        latitude = schoolTrainDetail.getLatitude();
                        longitude = schoolTrainDetail.getLongitude();
//                        APPApplication application = (APPApplication) mActivity.getApplication();
//                        tvAdress.setText(application.getDetailAdress());
                        if (null != schoolTrainDetail.getToBeijingTime()) {
                            llTrainDetail.setVisibility(View.VISIBLE);
                            tvArriveTime.setText(schoolTrainDetail.getToBeijingTime());
                            tvArriveMetch.setText(schoolTrainDetail.getToBeijingMethod());
                            tvArriveNum.setText(schoolTrainDetail.getEnrollNum() + "");
                            tvArriveMoney.setText(schoolTrainDetail.getWipeUpFee());
                            tvShipHight.setText(schoolTrainDetail.getPromote());
                            tvFinishTime.setText(schoolTrainDetail.getEndTime());
                        }
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

    /**
     * 确认接送此项目，更新状态
     *
     * @param acceptId
     * @param practiceId
     */
    public void confirmProject(String acceptId, String practiceId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("acceptId", acceptId);
        stringStringHashMap.put("practiceId", practiceId);
        getinstance.post(Constant.CONFIRMPROJECT, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                mActivity.finish();
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.rl_shipDetil, R.id.rl_shipmoney, R.id.rl_school_contact, R.id.btn_confirm, R.id.rl_trainDetail,R.id.tv_adress})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_shipDetil:
                //证明还没有接受此项目
                if (isShowLayout) {

                    return;
                }
                intent.putExtra("schoolId", schoolId.toString());
                mBundle.putSerializable("tempSchoolPractice", schoolPractice);
                intent.putExtras(mBundle);
                intent.setClass(mActivity, NewInternshipDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_shipmoney:
                //证明还没有接受此项目
                if (isShowLayout) {

                    return;
                }
                intent.putExtra("practiceId", schoolPractice.getId());
                if (null != schoolSalaryInfo) {
                    mBundle.putSerializable("tempSchoolSalaryInfo", schoolSalaryInfo);
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, NewIntershipMoney.class);
                startActivity(intent);
                break;
            case R.id.rl_school_contact:
                //证明还没有接受此项目
                if (isShowLayout) {

                    return;
                }
                intent.putExtra("practiceId", schoolPractice.getId());
                if (null != schoolSalaryInfo) {
                    mBundle.putSerializable("tempSchoolTrainDetail", schoolTrainDetail);
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, NewSchoolLiaisonsActivity.class);

                startActivity(intent);
                break;
            case R.id.btn_confirm:
                //确认提交
                new CommomDialog(mActivity, R.style.dialog, ((ProjectDetailActivity) mActivity).schoolName + "项目", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String string) {
                        if (confirm) {
                            confirmProject(userId + "", schoolPracticeId);
                            dialog.dismiss();
                        }
                    }
                })
                        .setTitle("是否确认接送").show();
                break;
            case   R.id.tv_adress:
                Intent intent1=new Intent();
                intent1.putExtra("latitude",latitude);
                intent1.putExtra("longitude",longitude);
                intent1.setClass(mActivity,MapLocationActivity.class);
                startActivity(intent1);

//                LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
//                AmapNaviPage.getInstance().showRouteActivity(mActivity.getApplicationContext(), new AmapNaviParams(null, null, new Poi("故宫博物院", p2, ""), AmapNaviType.DRIVER), this);
//                if (isAvilible(this, "com.autonavi.minimap")) {
//                    Intent gaoDeIntent = new Intent();
//                    gaoDeIntent.setAction(Intent.ACTION_VIEW);
//                    gaoDeIntent.addCategory(Intent.CATEGORY_DEFAULT);
//                    //将功能Scheme以URI的方式传入data
//                    Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=" + 39.99329 + "&lon=" + 116.395571 + "&dev=1&style=2");
//                    gaoDeIntent.setData(uri);
//                    //启动该页面即可
//                    startActivity(gaoDeIntent);
//                } else {
//                    Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
//                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    if (intent.resolveActivity(getPackageManager()) != null){
//                        startActivity(intent);
//                    }
//                }

                break;
            case R.id.rl_trainDetail:
                //证明还没有接受此项目
                if (isShowLayout) {
                    return;
                }
                intent.putExtra("schoolTrainId", schoolTrainId);
                intent.putExtra("practiceId", schoolPractice.getId());
                //用值判断是否已经填写培训详情
                if (null != schoolTrainDetail.getToBeijingTime()) {
                    mBundle.putSerializable("tempSchoolTrainDetail", schoolTrainDetail);
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, TrainDetailAcitity.class);
                startActivity(intent);

                break;

        }

    }




}
