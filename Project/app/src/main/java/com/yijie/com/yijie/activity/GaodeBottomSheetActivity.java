package com.yijie.com.yijie.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.adapter.LoadMorePopuAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.DensityUtils;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.MyKinderWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

public class GaodeBottomSheetActivity extends BaseActivity {

    @BindView(R.id.tv_gold_search_content)
    TextView tvGoldSearchContent;
    @BindView(R.id.ll_gold_search_bg)
    LinearLayout llGoldSearchBg;
    @BindView(R.id.tv_gold_down_more)
    TextView tvGoldDownMore;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.design_bottom_sheet_bar)
    RelativeLayout designBottomSheetBar;

    @BindView(R.id.rv_gold_mine_list)
    RecyclerView rvGoldMineList;
    @BindView(R.id.fra_bottom_sheet)
    RelativeLayout fraBottomSheet;
    @BindView(R.id.bottom_sheet_coordinatorLayout)
    CoordinatorLayout bottomSheetCoordinatorLayout;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;


    private BottomSheetBehavior behavior;
    private boolean isHasNavigationBar = false;
    private boolean isSetBottomSheetHeight;
    private int fraBottomSheetHeight;
    private boolean isHid = false;
    private int listBehaviorHeight = 0;
    private LoadMorePopuAdapter adapter;

    private AMap aMap;
    List<KindergartenDetail> markBeans = new ArrayList<>();
    private MyKinderWindow myPopuWindow;

    /**
     * 判断NavigationBar（就是虚拟返回键 home键）是存在
     *
     * @param activity
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * 判断NavigationBar（就是虚拟返回键 home键）是否显示
     *
     * @return
     */
    public boolean isNavigationBarShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView.onCreate(savedInstanceState);// 此方法必须重写,否则不显示地图


    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_gaode_bottomsheet);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoldMineList.setLayoutManager(manager);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("我是第" + i);
        }
        adapter = new LoadMorePopuAdapter(list, R.layout.adapter_popu_item);
        rvGoldMineList.setAdapter(adapter);
        behavior = BottomSheetBehavior.from(fraBottomSheet);
        behavior.setHideable(true);
        behavior.setSkipCollapsed(false);
        listBehaviorHeight = DensityUtils.dp2px(this, 345);
        setListener();

        markBeans.clear();
        getData();
        if (aMap == null) {
            aMap = mapView.getMap();

        }
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置缩放按钮是否显示
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //修改SetBottomSheet的高度 留出顶部工具栏的位置
        if (!isSetBottomSheetHeight) {
            CoordinatorLayout.LayoutParams linearParams = (CoordinatorLayout.LayoutParams) fraBottomSheet.getLayoutParams();
            linearParams.height = bottomSheetCoordinatorLayout.getHeight() - DensityUtils.dp2px(this, 90);
            fraBottomSheetHeight = linearParams.height;
            fraBottomSheet.setLayoutParams(linearParams);
            isSetBottomSheetHeight = true;
        }


    }

    /**
     * 设置监听
     */
    private void setListener() {
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        try {
            getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    //这个监听的方法时为了让有NavigationBar处理布局的变化的
                    if (isHasNavigationBar) {
                        boolean is = isNavigationBarShow();
                        if (isHid != is) {
                            CoordinatorLayout.LayoutParams linearParams = (CoordinatorLayout.LayoutParams) fraBottomSheet.getLayoutParams();
                            linearParams.height = bottomSheetCoordinatorLayout.getHeight() - DensityUtils.dp2px(GaodeBottomSheetActivity.this, 90);
                            fraBottomSheetHeight = linearParams.height;
                            fraBottomSheet.setLayoutParams(linearParams);
                            isSetBottomSheetHeight = true;
                        }
                        isHid = is;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //底栏状态改变的监听
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                Log.i("TAG", "TTTT-----" + newState);

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tvGoldDownMore.setVisibility(View.GONE);

                } else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tvGoldDownMore.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e("TAG", "TTT-----------" + bottomSheet.getTop() + "--------" + llGoldSearchBg.getTop());
                Log.i("TAG", "TTT-----------" + (bottomSheet.getTop() - DensityUtils.dp2px(GaodeBottomSheetActivity.this, 90)) + "---------" + slideOffset);
                if (bottomSheet.getTop() < DensityUtils.dp2px(GaodeBottomSheetActivity.this, 135)) {

                    //设置底栏完全展开时，出现的顶部工具栏的动画
                    designBottomSheetBar.setVisibility(View.VISIBLE);
                    designBottomSheetBar.setAlpha(slideOffset);
                    designBottomSheetBar.setTranslationY(-bottomSheet.getTop() + DensityUtils.dp2px(GaodeBottomSheetActivity.this, 135));
                    llGoldSearchBg.setTranslationY(bottomSheet.getTop() - DensityUtils.dp2px(GaodeBottomSheetActivity.this, 135));
                    if (1 == slideOffset) {
                        llGoldSearchBg.setVisibility(View.INVISIBLE);
                    } else {
                        llGoldSearchBg.setVisibility(View.VISIBLE);
                    }
                } else {
                    designBottomSheetBar.setVisibility(View.INVISIBLE);
                    llGoldSearchBg.setVisibility(View.VISIBLE);
                    llGoldSearchBg.setTranslationY(DensityUtils.dp2px(GaodeBottomSheetActivity.this, 0));
                }
            }
        });


    }

//    @OnClick(R.id.tv_gold_down_more)
//    public void onClick() {
//
//    }

    /**
     * 获取园所列表
     */
    private void getData() {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        getinstance.post(Constant.GETSHOWALLKINDATMAP, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
//                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
//                commonDialog.dismiss();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            KindergartenDetail studentSignIn = gson.fromJson(data.getJSONObject(i).toString(), KindergartenDetail.class);
                            markBeans.add(studentSignIn);
                        }
                        //清除所有marker等，保留自身
                        aMap.clear();
                        addMarkers(markBeans);

                    } else {
                        ShowToastUtils.showToastMsg(GaodeBottomSheetActivity.this, "获取园所列表失败");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
//                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });
    }

    /**
     * 通过园所id查询园所详情
     */
    private void getKinderDetail(String kinderId) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("kinderId", kinderId);
        getinstance.post(Constant.GETKINDERINFOBYKINDERID, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
//                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
//                commonDialog.dismiss();
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        KindergartenDetail kindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);

                        //显示底部popu
                        myPopuWindow = new MyKinderWindow(GaodeBottomSheetActivity.this, kindergartenDetail);
                        myPopuWindow.setOnClick(new MyKinderWindow.onConfirm() {
                            @Override
                            public void OnCheck() {
                                ShowToastUtils.showToastMsg(GaodeBottomSheetActivity.this, "查看详情");
                            }
                        });
                        myPopuWindow.show();


                    } else {
                        ShowToastUtils.showToastMsg(GaodeBottomSheetActivity.this, "获取园所详情失败");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
//                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });
    }

    /**
     * 地图上添加标记
     *
     * @param markBeans
     */
    private void addMarkers(final List<KindergartenDetail> markBeans) {
        for (int i = 0; i < markBeans.size(); i++) {
            final KindergartenDetail markBean = markBeans.get(i);
            if (aMap != null) {
                final int flag = i;
                final View view1 = View.inflate(GaodeBottomSheetActivity.this, R.layout.mark_layout, null);
                TextView textName = (TextView) view1.findViewById(R.id.tv_name);
                textName.setText(markBean.getKindName());
                MarkerOptions mk = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(view1)));
                //设置纬度和经度
                LatLng ll = new LatLng(Double.parseDouble(markBean.getLatitude()), Double.parseDouble(markBean.getLongitude()));
                mk.position(ll);
                CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
                aMap.moveCamera(cu);
                aMap.moveCamera(CameraUpdateFactory.zoomBy(9));
                mk.period(flag);
                aMap.addMarker(mk);

            }
        }
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                int period = marker.getPeriod();
                int id = markBeans.get(period).getId();
                getKinderDetail(id + "");
                return false;
            }
        });
//        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//        });

    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
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
        if (null != mapView) {
            mapView.onDestroy();

        }
    }

    @OnClick({R.id.tv_gold_down_more, R.id.btn_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_gold_down_more:
                if (isHasNavigationBar) {
                    CoordinatorLayout.LayoutParams linearParams = (CoordinatorLayout.LayoutParams) fraBottomSheet.getLayoutParams();
                    linearParams.height = bottomSheetCoordinatorLayout.getHeight() - DensityUtils.dp2px(GaodeBottomSheetActivity.this, 90);
                    fraBottomSheetHeight = linearParams.height;
                    fraBottomSheet.setLayoutParams(linearParams);
                    isSetBottomSheetHeight = true;
                }
                behavior.setPeekHeight((fraBottomSheetHeight - DensityUtils.dp2px(GaodeBottomSheetActivity.this, 45) > listBehaviorHeight) ? listBehaviorHeight : (int) (fraBottomSheetHeight / 2));
                fraBottomSheet.setVisibility(View.VISIBLE);
                if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_search:
                //清除所有标记
                aMap.clear();
                //todo 标记刚搜索出来的信息
                tvGoldDownMore.setVisibility(View.VISIBLE);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }
}
