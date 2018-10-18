package com.yijie.com.yijie.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.view.MyPopuWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学生考勤
 */
public class SchoolSignActivity extends BaseActivity implements AMapLocationListener, LocationSource {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_kindName)
    TextView tvKindName;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private AMap aMap;
    List<MarkBean> markBeans = new ArrayList<>();
    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mListener;
    private MyPopuWindow myPopuWindow;

    @Override
    public void onResume() {

        mapView.onResume();
        super.onResume();
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
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_schoolsign);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        if (aMap == null) {
            aMap = mapView.getMap();
            //清除所有marker等，保留自身
            aMap.clear();

            MarkBean markBean = new MarkBean();
            markBean.setName("王府井");
            markBean.setLat("39.911607");
            markBean.setLon("116.411503");
            MarkBean markBean1 = new MarkBean();
            markBean1.setName("天安门");
            markBean1.setLat("39.911607");
            markBean1.setLon("116.397477");
            markBeans.add(markBean1);
            markBeans.add(markBean);

            addMarker(markBeans);

            //设置定位监听
            aMap.setLocationSource(this);
            setUpMap();
            mLocationClient.startLocation();

        }
    }

    private void addMarker(List<MarkBean> markBeans) {
        for (int i = 0; i < markBeans.size(); i++) {
            MarkBean markBean = markBeans.get(i);
            setMarkerOptions(markBean.getName(), markBean.getLat(), markBean.getLon());
        }

    }

    @OnClick({R.id.back, R.id.tv_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_detail:
                break;
        }
    }


    class MarkBean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        private String lat;
        private String lon;
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 设置标记
     *
     * @param name      标记的名称
     * @param latitude  纬度
     * @param longitude 经度
     */
    private void setMarkerOptions(String name, String latitude, String longitude) {

        if (aMap != null) {
            View view = View.inflate(this, R.layout.mark_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_name);
            textView.setText(name);
            MarkerOptions mk = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view)));
            LatLng ll = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            mk.position(ll);
            CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
            aMap.moveCamera(cu);
            aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
            aMap.addMarker(mk);

        }
    }

    private void setUpMap() {
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
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色  。
//        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
//        aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置缩放按钮是否显示
//        aMap.getUiSettings().setScrollGesturesEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String number = marker.getId().substring(6);
                if (!number.equals("") && number != null) {
                    int posion = Integer.parseInt(number) - 1;
                    llBottom.setVisibility(View.VISIBLE);
                    tvKindName.setText(markBeans.get(posion).getName());
                }
                return false;
            }
        });
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                llBottom.setVisibility(View.GONE);

            }
        });
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

                String address = aMapLocation.getAddress();
                String description = aMapLocation.getDescription();
                LogUtil.e(detail + "------" + address + "----" + description);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        mapView.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
