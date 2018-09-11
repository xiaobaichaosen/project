package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewSchoolActivity;
import com.yijie.com.yijie.activity.search.SchoolListSearchActivity;
import com.yijie.com.yijie.adapter.SchoolListAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.base.search.AppSearchActivity;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.LoadingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/5/21.
 * 学校列表
 */

public class SchoolListAcitivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_newSchool)
    ImageView ivNewSchool;
    @BindView(R.id.app_title)
    RelativeLayout appTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;



    private ArrayList<SchoolSimple> dataList;
    private LoadMoreWrapper loadMoreWrapper;
    private int currentPage=1;
    private int totalPage;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_schoollist);

    }

    @Override
    protected void onResume() {
        dataList.clear();
        currentPage=1;
        getSchoolList();
        super.onResume();
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getSchoolList();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getSchoolList();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        dataList = new ArrayList<>();
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SchoolListAdapter loadMoreWrapperAdapter = new SchoolListAdapter(dataList, R.layout.school_adapter_item2);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
//        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_GONE);
        loadMoreWrapperAdapter.setOnItemClickListener(new SchoolListAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.putExtra("schoolId", dataList.get(position).id);
                                                              intent.putExtra("schoolName", dataList.get(position).name);
                                                              intent.putExtra("schoolContact",dataList.get(position).user_name);
                                                              intent.putExtra("schoolContactPhone",dataList.get(position).cellphone);
                                                              intent.putExtra("logo",dataList.get(position).litimg);
                                                              intent.putExtra("zjNumber",dataList.get(position).telephone);
                                                              intent.setClass(SchoolListAcitivity.this, TempActivity.class);
                                                              startActivity(intent);
                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                currentPage = 1;
                getSchoolList();


                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < totalPage) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage++;
                                    getSchoolList();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });

    }

    private void getSchoolList() {
        String json = (String) SharedPreferencesUtils.getParam(this, "user", "");
        //先判断账号的角色
        String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");
        int userId = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        if (roleName.contains("开发老师")){
            stringStringHashMap.put("createBy", userId + "");
        }else {
            stringStringHashMap.put("owner", userId + "");
        }
        stringStringHashMap.put("pageSize", 10 + "");
        getinstance.post(Constant.SCHOOLLIST, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
              commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                 commonDialog.dismiss();
                 statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);

                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    totalPage = Integer.parseInt(data.getString("total"));
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        SchoolSimple schoolSimple = gson.fromJson(jsonObject1.toString(), SchoolSimple.class);
                        dataList.add(schoolSimple);
                    }
                    if (dataList.size()==totalPage){
                        // 显示加载到底的提示
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    loadMoreWrapper.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (totalPage==0){
                   statusLayoutManager.showEmptyLayout();
                }else {
                 statusLayoutManager.showSuccessLayout();
                }
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.iv_search, R.id.iv_newSchool})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_search:
                intent.setClass(SchoolListAcitivity.this, SchoolListSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_newSchool:
                intent.setClass(SchoolListAcitivity.this, NewSchoolActivity.class);
                startActivity(intent);
                break;
        }
    }
}
