package com.yijie.com.studentapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.SchoolCalenderActivity;
import com.yijie.com.studentapp.activity.SchoolSignActivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.bean.StudentSignIn;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 足迹
 */
public class FootprintFragment extends BaseFragment  {

    @BindView(R.id.mapView)
    MapView mapView;
    Unbinder unbinder;
    private AMap aMap;
    List<StudentSignIn> markBeans = new ArrayList<>();
    private String userIdString;

    @Override
    protected void initView() {


    }


    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }

        userIdString = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        markBeans.clear();
        getData(userIdString);
        if (aMap == null) {
            aMap = mapView.getMap();

        }

    }
    /**
     * 获取打卡数据
     */
    private void getData(String userId) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userId);
        getinstance.post(Constant.STUDENTSIGNIN, stringStringHashMap, new BaseCallback<String>() {
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
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            StudentSignIn studentSignIn = gson.fromJson(data.getJSONObject(i).toString(), StudentSignIn.class);
                            markBeans.add(studentSignIn);
                        }
                        //清除所有marker等，保留自身
                        aMap.clear();
                        addMarkers(markBeans);

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
    private void addMarkers(final List<StudentSignIn> markBeans) {
        for (int i = 0; i < markBeans.size(); i++) {
            final StudentSignIn markBean = markBeans.get(i);
            if (aMap!=null){
                final int flag=i;
                final View view1 = View.inflate(mActivity, R.layout.mark_layout, null);
                ImageView ivSign = (ImageView) view1.findViewById(R.id.iv_sign);
                ImageLoader imageLoader = ImageLoaderUtil.getImageLoader(mActivity);
                //要加监听，否则图片没加载出来就开始转换bitmap了
                imageLoader.displayImage(Constant.infoUrl + userIdString + "/studentSignIn/" + markBean.getSigninPic(), ivSign, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        LogUtil.e("====="+Constant.infoUrl+userIdString+"/studentSignIn/"+markBean.getSigninPic());
                        MarkerOptions mk = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view1)));
                        //设置纬度和经度
                        LatLng ll = new LatLng(Double.parseDouble( markBean.getLatitude()), Double.parseDouble( markBean.getLongitude()));
                        mk.position(ll);
                        CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
                        aMap.moveCamera(cu);
                        aMap.moveCamera(CameraUpdateFactory.zoomBy(17));
                        mk.period(flag);
                        aMap.addMarker(mk);

                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });



            }
        }
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                    Intent intent=new Intent();
                SchoolSignActivity schoolSignActivity= (SchoolSignActivity)mActivity;
                    intent.putExtra("stuName",schoolSignActivity.stuName);
                    intent.putExtra("kindName",schoolSignActivity.kindName);
                intent.putExtra("headPic",schoolSignActivity.headPic);
                int period = marker.getPeriod();
                intent.putExtra("signDay",markBeans.get(period-1).getSigninDate());
                    intent.setClass(mActivity, SchoolCalenderActivity.class);
                    mActivity.startActivity(intent);

                return false;
            }
        });


    }


    public  Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
    /**
     * 设置标记
     *
     * @param
     * @param latitude  纬度
     * @param longitude 经度
     */
    private void setMarkerOptions(String singPic, String latitude, String longitude) {
        //在地图上添加一个marker，并将地图中移动至此处
        //设置定位的图片 (默认)
        //本地图片BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable
        // .location_marker))

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_footprint;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        mapView.onCreate(savedInstanceState);
        return rootView;
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
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

        if (null != mapView) {
            mapView.onDestroy();
        }
        unbinder.unbind();
    }

}




