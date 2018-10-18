package com.yijie.com.studentapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.PushAccessActivity;
import com.yijie.com.studentapp.activity.SchoolSignActivity;
import com.yijie.com.studentapp.activity.kndergard.KndergardAcitivity;
import com.yijie.com.studentapp.activity.photo.CameraActivity;
import com.yijie.com.studentapp.activity.photo.PhotoProcessActivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.TimeUtils;
import com.yijie.com.studentapp.view.CircleRelativeLayout;
import com.yijie.com.studentapp.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 签到
 */
public class SchoolSignFragment extends BaseFragment implements AMapLocationListener, LocationSource {
    @BindView(R.id.tv_signTime)
    Chronometer tvSignTime;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.tv_warn)
    TextView tvWarn;
    Unbinder unbinder;
    @BindView(R.id.cr_sign)
    CircleRelativeLayout crSign;
    @BindView(R.id.tv_signDuring)
    TextView tvSignDuring;
    @BindView(R.id.tv_signTimes)
    TextView tvSignTimes;
    private AMap aMap;
    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mListener;
    private String address;
    private String userIdString;
    private String signinDate;
    private  String  signinTime;

    private  double latitude;
    private double longitude;
    private int miss;
    private  boolean isFirstPushCard;
    private  boolean noShowDialog;

    @Override
    protected void initView() {


    }

    private void setUpMap() {

        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(mActivity.getApplicationContext());
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
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_blue));// 设置小蓝点的图标

        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色  。
//        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
        aMap.moveCamera(CameraUpdateFactory.zoomBy(12));
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置缩放按钮是否显示
        aMap.getUiSettings().setScrollGesturesEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

    }

    @Override
    public void onResume() {
        isPrepared = true;
        initData();


        mLocationClient.startLocation();
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void initData() {

        isFirstPushCard = (boolean) SharedPreferencesUtils.getParam(mActivity,"isFirstPushCard",false);
        noShowDialog = (boolean) SharedPreferencesUtils.getParam(mActivity,"noShowDialog",false);
        if (!isPrepared || isVisible) {
            return;
        }

        userIdString = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        getData(userIdString);
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置定位监听
            aMap.setLocationSource(this);
            setUpMap();
        }


    }

    /**
     * 获取打卡初始化数据
     */
    private void getData(String userId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userId);
        getinstance.post(Constant.SELECTSTUDENTSIGNIN, stringStringHashMap, new BaseCallback<String>() {
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
                commonDialog.dismiss();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String id = data.getString("id");

                        signinDate = data.getString("signinDate");
                        signinTime = data.getString("signinTime");
                        String   signInTimes = data.getString("signInTimes");
                        String kindName = data.getString("kindName");
                        String headPic = data.getString("headPic");
                        String stuName = data.getString("stuName");
                        //第一次打卡显示提示框
                        //确定之后再不显示提示框

                        if (isFirstPushCard&&!noShowDialog){
                            SharedPreferencesUtils.setParam(mActivity,"noShowDialog",true);
                            new CommomDialog(mActivity, R.style.dialog, "去评价一下对该园所的感受吧!", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm, String sContent) {
                                    if(confirm){
                                       Intent intent=new Intent();
                                       intent.setClass(mActivity, PushAccessActivity.class);
                                       startActivity(intent);
                                    }else {
                                         mActivity.finish();
                                    }
                                    dialog.dismiss();
                                }
                            })
                                    .setTitle("您已抵达"+kindName)
                                    .setNegativeButton("返回首页")
                                    .setPositiveButton("立即评价")
                                    .show();
                        }
                        if ("0".equals(signInTimes)) {
                            tvSignTimes.setText("今天还未签到");
                        }else {
                            tvSignTimes.setText("今天第"+ signInTimes+"签到");
                        }
                        tvSignTime.setText(signinTime);


                        String dateTime = "01:30:55";
                        Calendar calendar = Calendar.getInstance();
                        try {
                            calendar.setTime(new SimpleDateFormat("HH:mm:ss").parse(dateTime));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        miss = (int) TimeUtils.formatTurnSecond(signinTime.toString().trim());
                        tvSignTime.start();
                        tvSignTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer ch) {
                                miss++;
                                ch.setText(TimeUtils.FormatMiss(miss));
                            }
                        });


                    } else {
                        ShowToastUtils.showToastMsg(mActivity, "考勤初始化数据失败");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schoolsign;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mapView.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 显示我的位置
                mListener.onLocationChanged(aMapLocation);
                //设置第一次焦点中心
//                LatLng    latlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14), 1000, null);
                String detail = aMapLocation.getDistrict();

                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                address = aMapLocation.getAddress();
                String description = aMapLocation.getDescription();
                LogUtil.e(detail + "------" + address + "----" + description);
                LogUtil.e(detail + "------" + latitude + "----" + latitude);
                LogUtil.e(detail + "------" + longitude + "----" + longitude);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            }
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
        mapView.onPause();

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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

        if (null != mapView) {
            mapView.onDestroy();

        }

        unbinder.unbind();

    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }


    @OnClick({R.id.tv_signTime, R.id.cr_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_signTime:
                break;
            case R.id.cr_sign:
                if (TextUtils.isEmpty(address)) {
                    ShowToastUtils.showToastMsg(mActivity, "定位失败");
                    return;
                } else if(TextUtils.isEmpty(signinTime)){
                    ShowToastUtils.showToastMsg(mActivity, "获取打卡时间失败");
                    return;
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("address", address);
                    intent.putExtra("signinTime", tvSignTime.getText().toString());
                    intent.putExtra("signinDate", signinDate);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    intent.setClass(mActivity, CameraActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }


}
