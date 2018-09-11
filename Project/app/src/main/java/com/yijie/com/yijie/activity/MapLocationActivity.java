package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.AppInstallUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.MyBottomWindow;
import com.yijie.com.yijie.view.MyPopuWindow;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示当前位置
 */
public class MapLocationActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.btn_road)
    Button btnRoad;
    private MapView mapView;
    private AMap aMap;
    private MyBottomWindow myPopuWindow;
    String latitude;
    String longitude;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_location_map);

    }

    @Override
    public void init() {
        // 设置刷新控件颜色
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("学校地址");
        tvRecommend.setVisibility(View.VISIBLE);
        mapView = (MapView) findViewById(R.id.map);
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置缩放按钮是否显示

        // 设置当前地图显示为当前位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), 19));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
        markerOptions.title("当前位置");
        markerOptions.visible(true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker));
        markerOptions.icon(bitmapDescriptor);
        aMap.addMarker(markerOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写,否则不显示地图

    }

    @OnClick({R.id.back, R.id.btn_road})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_road:
                myPopuWindow = new MyBottomWindow(MapLocationActivity.this);
                myPopuWindow.setOnClick(new MyBottomWindow.onConfirm() {
                    @Override
                    public void onGaoClick() {
                        if(AppInstallUtils.isAvilible(MapLocationActivity.this,"com.autonavi.minimap")){
                            Intent gaoDeIntent = new Intent();
                            gaoDeIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            gaoDeIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://route?sourceApplication=appName&slat=&slon=&sname=我的位置&dlat="+ latitude+"&dlon="+ longitude+"&dname=目的地&dev=0&t=2"));
                            startActivity(gaoDeIntent);
                        }else{
                            ShowToastUtils.showToastMsg(MapLocationActivity.this,"请先安装高德地图");
                        }

                    }

                    @Override
                    public void onBaiClick() {
                        if (AppInstallUtils.isAvilible(MapLocationActivity.this, "com.baidu.BaiduMap")) {

                            Intent intent = null;
                            try {
                                intent = Intent.getIntent("intent://map/direction?" +
                                        "destination=latlng:" +latitude + "," +longitude + "|name:我的目的地" +    //终点
                                        "&mode=driving&" +     //导航路线方式
                                        "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                                startActivity(intent); //启动调用
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }


                        } else {
                            ShowToastUtils.showToastMsg(MapLocationActivity.this, "请先安装百度地图");
                        }
                    }

                });
                myPopuWindow.show();
                break;
        }
    }
}
