package com.yijie.com.studentapp.fragment;

import android.Manifest;
import android.content.Context;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.LoadMorePunchAdapter;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 打卡碎片
 */
public class PunchCardFragment extends BaseFragment {
    @BindView(R.id.tv_date)
    TextView tvDate;

    Unbinder unbinder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LocationManager locationManager;
    private String provider;
    LocationListener listener;
    private List<StudentBean> dataList = new ArrayList<>();
    private String city;
    private String district;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_punchcard;
    }

    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 2; i++) {

            dataList.add(new StudentBean(1, String.valueOf(letter)));

            letter++;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        // 模拟获取数据
        getData();
//        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        LoadMorePunchAdapter loadMoreWrapperAdapter = new LoadMorePunchAdapter(dataList, R.layout.punchcard_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);
        locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);//获得位置服务
        provider = judgeProvider(locationManager);
        if (provider != null) {//有位置提供器的情况
            //为了压制getLastKnownLocation方法的警告
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }



            listener = new LocationListener() {

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
                Toast.makeText(mActivity, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }


        } else {//不存在位置提供器的情况

        }
        WifiManager wifiManager = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        tvWifi.setText("wifi信息："+wifiInfo.toString()+"wifi名称："+wifiInfo.getSSID());
    }


    /**
     * 得到当前经纬度并开启线程去反向地理编码116.296674,40.106374
     */
    public void getLocation(Location location) {
        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=jx1GmOGTvg7au8tzR7gtKI2HpPmxgdrK&callback=renderReverse&location=" + latitude + "," + longitude + "&output=json&pois=0";
//        HttpUtils instance = HttpUtils.getinstance();
//        instance.get(url, new BaseCallback<String>(
//
//        ) {
//            @Override
//            public void onRequestBefore() {
//
//            }
//
//            @Override
//            public void onFailure(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onSuccess(Response response, String str) {
//                try {
//                    LogUtil.e(str);
//                    str = str.replace("renderReverse&&renderReverse", "");
//                    str = str.replace("(", "");
//                    str = str.replace(")", "");
//                    JSONObject jsonObject = new JSONObject(str);
//                    JSONObject address = jsonObject.getJSONObject("result");
//
//                    city = address.getString("formatted_address");
//                    district = address.getString("sematic_description");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Response response, int errorCode, Exception e) {
//
//            }
//        });

    }

    /**
     * 判断是否有可用的内容提供器
     *
     * @return 不存在返回null
     */
    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if (prodiverlist.contains(LocationManager.NETWORK_PROVIDER)) {
            return LocationManager.NETWORK_PROVIDER;
        } else if (prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;
        } else {
            ShowToastUtils.showToastMsg(mActivity, "没有可用的位置提供器");
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                TimePickerView.Type yearType = TimePickerView.Type.YEAR_MONTH_DAY;
                String yearFormat = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(mActivity, yearType, yearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvDate.setText(date);
                    }
                });
                break;
//            case R.id.rl_punchcard:
//                Intent intent = new Intent();
//                intent.putExtra("adress",city+district);
//                intent.setClass(mActivity, CameraActivity.class);
//                startActivity(intent);
//                break;
        }
    }
}
