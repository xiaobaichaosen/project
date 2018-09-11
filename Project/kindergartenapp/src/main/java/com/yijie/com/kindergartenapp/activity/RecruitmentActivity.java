package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.RecruitmentListAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.kindergartenapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.kindergartenapp.bean.KindergartenDiscovery;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.view.LoadingLayout;

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
 * Created by 奕杰平台 on 2018/6/11.
 * 招聘发布页面
 */

public class RecruitmentActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<KindergartenDiscovery> dataList;
    private int currentPage=1;
    private LoadMoreWrapper loadMoreWrapper;
    int kinderId;
    int discoveryType;
    private int totalPage;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_recruitment);

    }

    @Override
    protected void onResume() {
        dataList.clear();
        currentPage = 0;
        getSchoolList();
        super.onResume();
    }

    @Override
    public void init() {
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        dataList.clear();
                        currentPage = 1;
                        getSchoolList();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        dataList.clear();
                        currentPage = 1;
                        getSchoolList();
                    }
                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        title.setText("招聘发布");
        kinderId = getIntent().getIntExtra("kinderId", 0);
        discoveryType = getIntent().getIntExtra("discoveryType", 0);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        dataList = new ArrayList<>();
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecruitmentListAdapter listAdapter = new RecruitmentListAdapter(dataList, R.layout.adapter_recruitment_item);
        loadMoreWrapper = new LoadMoreWrapper(listAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        listAdapter.setOnItemClickListener(new RecruitmentListAdapter.OnItemClickListener(

                                           ) {
                                               @Override
                                               public void onItemClick(View view, int position) {
//                                                              Intent intent = new Intent();
//                                                              intent.putExtra("schoolId", dataList.get(position).id);
//                                                              intent.putExtra("schoolName", dataList.get(position).name);
//                                                              intent.setClass(RecruitmentActivity.this, RequestDetailActivity.class);
//                                                              startActivity(intent);
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
                loadMoreWrapper.notifyDataSetChanged();

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

        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("kinderId", kinderId + "");
        stringStringHashMap.put("type", discoveryType + "");
        stringStringHashMap.put("pageSize", 10 + "");
        getinstance.post(Constant.SELECTPUBLISHLIST, stringStringHashMap, new BaseCallback<String>() {
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

                currentPage++;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    totalPage = Integer.parseInt(data.getString("total"));
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        KindergartenDiscovery kindergartenDiscovery = gson.fromJson(jsonObject1.toString(), KindergartenDiscovery.class);
                        dataList.add(kindergartenDiscovery);
                    }
                    if (dataList.size() < 10) {
                        // 显示加载到底的提示
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    loadMoreWrapper.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dataList.size() == 0) {
                    statusLayoutManager.showEmptyLayout();
                } else {
                    statusLayoutManager.showSuccessLayout();

                }
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                break;
        }
    }
}
