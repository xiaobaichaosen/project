package com.yijie.com.studentapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.photo.CameraActivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CircleRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/6.
 * 考勤
 */

public class AttendanceActivity extends BaseActivity {


    @BindView(R.id.circleRelative)
    CircleRelativeLayout circleRelative;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    private LocationManager locationManager;
    private String provider;
    private Location location;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_attendance);

    }

    @Override
    public void init() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//获得位置服务
        provider = judgeProvider(locationManager);
        if (provider != null) {//有位置提供器的情况
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            LocationListener  listener= new LocationListener(){

                @Override
                public void onLocationChanged(Location location) {
                    getLocation(location);//得到当前经纬度并开启线程去反向地理编码
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null || locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {

                locationManager.requestLocationUpdates(provider, 1000, 1000, listener);
            } else {
                //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
                Toast.makeText(this, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }


        }else{//不存在位置提供器的情况

        }
        WifiManager wifiManager= (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        tvWifi.setText("wifi信息："+wifiInfo.toString()+"wifi名称："+wifiInfo.getSSID());
    }


    /**
     * 得到当前经纬度并开启线程去反向地理编码116.296674,40.106374
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude()+"";
        String longitude = location.getLongitude()+"";
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=jx1GmOGTvg7au8tzR7gtKI2HpPmxgdrK&callback=renderReverse&location="+latitude+","+longitude+"&output=json&pois=0";
        HttpUtils instance = HttpUtils.getinstance();
        instance.get(url, new BaseCallback<String>(

        ) {
            @Override
            public void onRequestBefore() {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onSuccess(Response response, String str) {
                try {
                    LogUtil.e(str);
                    str = str.replace("renderReverse&&renderReverse","");
                    str = str.replace("(","");
                    str = str.replace(")","");
                    JSONObject jsonObject = new JSONObject(str);
                    JSONObject address = jsonObject.getJSONObject("result");
                    String city = address.getString("formatted_address");
                    String district = address.getString("sematic_description");
                    tvWifi.setText("当前位置："+city+district);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {

            }
        });

    }
    /**
     * 判断是否有可用的内容提供器
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
            return LocationManager.NETWORK_PROVIDER;
        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        }else{
            ShowToastUtils.showToastMsg(this,"没有可用的位置提供器");
        }
        return null;
    }
    @OnClick(R.id.circleRelative)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
