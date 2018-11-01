package com.yijie.com.kindergartenapp.activity;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.LoadMorePoiAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.kindergartenapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.utils.DataConversionUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/4/19.
 */

public class PoiSearchActivity extends BaseActivity implements TextWatcher, PoiSearch.OnPoiSearchListener, AMapLocationListener, LocationSource {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.keyWord)
    EditText keyWord;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tv_school)
    TextView tvSchool;

    @BindView(R.id.map)
    MapView map;

    @BindView(R.id.iv_center_location)
    ImageView ivCenterLocation;
    private String schoolDatil;
    private String latString;
    private String lonString;

    private List<SchoolAdress> dataList = new ArrayList<>();
    private List<SchoolAdress> moreList = new ArrayList<>();
    LoadMoreWrapper loadMoreWrapper;
    LoadMorePoiAdapter loadMoreWrapperAdapter;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    //用来判断是否重新发的搜索
    private String poiString;
    private AMap aMap;
    private String deepType;
    private AMapLocationClient mLocationClient;
    private OnLocationChangedListener mListener;
    private MapView mapView;
    private ObjectAnimator mTransAnimator;//地图中心标志动态
    private boolean isSearchData;
    private GeocodeSearch.OnGeocodeSearchListener mOnGeocodeSearchListener;
    private PoiItem userSelectPoiItem;
    private String kindName;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_poisearch);
    }

    @Override
    public void init() {
        // 设置刷新控件颜色
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("园所地址");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("确定");
        mapView = (MapView) findViewById(R.id.map);
        kindName = getIntent().getStringExtra("kindName");
        isSearchData=false;
        keyWord.setText(kindName);

        if (aMap == null) {
            aMap = mapView.getMap();
            //设置定位监听
            aMap.setLocationSource(this);
            setUpMap();
        }
        keyWord.addTextChangedListener(this);
        loadMoreWrapperAdapter = new LoadMorePoiAdapter(moreList, R.layout.adapter_item_layout);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMorePoiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO 当点击item的时候添加条目更改颜色

//               view.setBackgroundColor(getResources().getColor(R.color.appBarColor));
//
                tvSchool.setText("所选园所:" + moreList.get(position).getDetailAdress());
                schoolDatil = moreList.get(position).getDetailAdress();
                latString = moreList.get(position).getLat();
                lonString = moreList.get(position).getLon();
//                loadMoreWrapperAdapter.notifyDataSetChanged();
                //设置点击的标记
                setMarkerOptions(moreList.get(position).getName(), Double.parseDouble(moreList.get(position).getLat()), Double.parseDouble(moreList.get(position).getLon()));
//                ShowToastUtils.showToastMsg(PoiSearchActivity.this, moreList.get(position).getName() + "--" + moreList.get(position).getLat() + "--" + moreList.get(position).getLon());
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新


        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);


                if (query != null && poiSearch != null && poiResult != null) {
                    if (poiResult.getPageCount() - 1 > currentPage) {
                        currentPage++;
                        query.setPageNum(currentPage);// 设置查后一页
                        poiSearch.searchPOIAsyn();
                    } else {
                        ShowToastUtils.showToastMsg(PoiSearchActivity.this,
                                "没有搜到结果");
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
        mTransAnimator = ObjectAnimator.ofFloat(ivCenterLocation, "translationY", 0f, -80f, 0f);
        mTransAnimator.setDuration(800);
        //监测地图画面的移动
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (null != mLocationClient && null != cameraPosition&& isSearchData) {
                    getAddressInfoByLatLong(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    startTransAnimator();
//                    doSearchQuery(true, "", location.getCity(), new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude));
                }
//                if (!isSearchData) {
//                    isSearchData = true;
//                }
            }

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }
        });

        //设置触摸地图监听器
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent motionEvent) {
                isSearchData = true;
            }
        });


        //逆地址搜索监听器
        mOnGeocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if (i == 1000&&isSearchData) {
                    if (regeocodeResult != null) {
                        userSelectPoiItem = DataConversionUtils.changeToPoiItem(regeocodeResult);
                        if (null != moreList) {
                            moreList.clear();
                        }
                        List<PoiItem> poiItems = regeocodeResult.getRegeocodeAddress().getPois();
                        for (int j = 0; j < poiItems.size(); j++) {
                            SchoolAdress schoolAdress = new SchoolAdress();
                            LatLonPoint latLonPoint = poiItems.get(j).getLatLonPoint();
                            String snippet = poiItems.get(j).getSnippet();
                            double latitude = latLonPoint.getLatitude();
                            double longitude = latLonPoint.getLongitude();
                            //TODO 等待验证
                            if (latitude != 0.0 && longitude != 0.0 && !snippet.equals("") && !snippet.equals(null)) {
                                schoolAdress.setName(poiItems.get(j).getTitle());
                                schoolAdress.setLat(latitude + "");
                                schoolAdress.setLon(longitude + "");
                                schoolAdress.setDetailAdress(snippet);
                                schoolAdress.setType(1);
                                moreList.add(schoolAdress);
                            }
                        }

                        loadMoreWrapper.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(0);
                    }
                }

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        };
    }

    /**
     * 移动动画
     */
    private void startTransAnimator() {
        if (null != mTransAnimator && !mTransAnimator.isRunning()) {
            mTransAnimator.start();
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
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
        aMap.moveCamera(CameraUpdateFactory.zoomBy(12));
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置缩放按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        deepType = "";//设置搜索类型为学校
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写,否则不显示地图
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String newText = charSequence.toString().trim();
        poiString = newText;
        if ("".equals(newText)) {
            ShowToastUtils.showToastMsg(this, "请输入搜索关键字");
            return;
        } else {
            moreList.clear();
            doSearchQuery(newText);
        }


    }

    /**
     * poi搜索
     */
    private void doSearchQuery(String newText) {
//        showProgressDialog();// 显示进度框
        currentPage = 0;
        query = new PoiSearch.Query(newText, deepType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）

        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
//        query.setCityLimit(true);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        //设置搜索范围
//        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 3000));
        poiSearch.searchPOIAsyn();
    }


    /**
     * poi查询回调
     *
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                dataList.clear();
                //如果编辑框没有变化加载更多
                if (poiSearch.equals(query.getQueryString())) {

                }
                //如果编辑框数据变化重新发起搜索

//                if (query.getQueryString().equals(poiString)) {// 是否是同一条
                poiResult = result;
                // 取得搜索到的poiitems有多少页
                List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                List<SuggestionCity> suggestionCities = poiResult
                        .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                if (poiItems != null && poiItems.size() > 0) {
                    for (int i = 0; i < poiItems.size(); i++) {
                        SchoolAdress schoolAdress = new SchoolAdress();
                        LatLonPoint latLonPoint = poiItems.get(i).getLatLonPoint();
                        String snippet = poiItems.get(i).getSnippet();
                        double latitude = latLonPoint.getLatitude();
                        double longitude = latLonPoint.getLongitude();
                        if (i==0){
                            setMarkerOptions("",latitude,longitude);
                        }
                        //TODO 等待验证
                        if (latitude != 0.0 && longitude != 0.0 && !snippet.equals("") && !snippet.equals(null)) {
                            schoolAdress.setName(poiItems.get(i).getTitle());
                            schoolAdress.setLat(latitude + "");
                            schoolAdress.setLon(longitude + "");
                            schoolAdress.setDetailAdress(snippet);
                            schoolAdress.setType(1);
                            dataList.add(schoolAdress);
                        }
                    }
                    moreList.addAll(dataList);
                    loadMoreWrapper.notifyDataSetChanged();

                } else {
                    ShowToastUtils.showToastMsg(PoiSearchActivity.this,
                            "未搜到结果");
                }
//                }
            } else {
                ShowToastUtils.showToastMsg(PoiSearchActivity.this,
                        "未搜到结果");
            }
        } else {
            ShowToastUtils.showToastMsg(PoiSearchActivity.this,
                    rCode + "");
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord.getText().toString());
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                finish();
                break;
            case R.id.tv_recommend:
                SchoolAdress schoolAdress = new SchoolAdress();
                schoolAdress.setType(2);
                schoolAdress.setDetailAdress(schoolDatil);
                schoolAdress.setLon(lonString);
                schoolAdress.setLat(latString);
                EventBus.getDefault().post(schoolAdress);
                finish();
                break;
        }
    }
    /**
     * 通过经纬度获取当前地址详细信息，逆地址编码
     *
     * @param latitude
     * @param longitude
     */
    private void getAddressInfoByLatLong(double latitude, double longitude) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        /*
        point - 要进行逆地理编码的地理坐标点。
        radius - 查找范围。默认值为1000，取值范围1-3000，单位米。
        latLonType - 输入参数坐标类型。包含GPS坐标和高德坐标。 可以参考RegeocodeQuery.setLatLonType(String)
        */
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latitude, longitude), 3000, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
        geocodeSearch.setOnGeocodeSearchListener(mOnGeocodeSearchListener);
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
                LogUtil.e(detail);
                doSearchQuery(kindName);

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            }
        }
    }

    /**
     * 设置标记
     *
     * @param name      标记的名称
     * @param latitude  纬度
     * @param longitude 经度
     */
    private void setMarkerOptions(String name, double latitude, double longitude) {
        LatLng ll = new LatLng(latitude, longitude);
        aMap.clear();
        CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
        aMap.animateCamera(cu);
        aMap.moveCamera(CameraUpdateFactory.zoomBy(12));
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mLocationClient.startLocation();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }

        mapView.onDestroy();
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
