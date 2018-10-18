package com.yijie.com.yijie.activity.student;

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
import com.yijie.com.yijie.activity.student.adapter.LoadMoreCheckPendindAdapter;
import com.yijie.com.yijie.activity.student.adapter.LoadMoreTBStributedAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.StudentResume;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.view.LoadingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/4/27.
 *  待分配(推荐，已投，未投)
 */

public class TBStributedActivity extends BaseActivity {
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
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_speedCheck)
    TextView tvSpeedCheck;
    private List<StudentResume> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;
    int currentPage=1;
    String total;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_samplecheckpending);
    }

    @OnClick(R.id.back)
    public void goBack() {
        finish();
    }

    @Override
    protected void onResume() {
        currentPage=1;
        dataList.clear();
        getData();
        super.onResume();
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(recyclerView)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("待分配");
        rlTitle.setVisibility(View.GONE);
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setBackgroundResource(R.mipmap.search);
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LoadMoreTBStributedAdapter loadMoreWrapperAdapter = new LoadMoreTBStributedAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreTBStributedAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              //根据状态跳转页面
                                                              Intent intent = new Intent();
                                                              intent.putExtra("studentUserId",dataList.get(position).getStudentUserId());
                                                              intent.setClass(TBStributedActivity.this, TBStriDetailActivity.class);
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
                getData();
//                loadMoreWrapper.notifyDataSetChanged();

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

                if (dataList.size() < Integer.parseInt(total)) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currentPage++;
                            getData();
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                        }
                    });


                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    private void getData() {
        HttpUtils getinstance = HttpUtils.getinstance(TBStributedActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("name","");
        getinstance.post(Constant.SELECTRESUMETAKEALLOTLIST, stringStringHashMap, new BaseCallback<String>() {
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
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    total = data.getString("total");
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        StudentResume studentResume = gson.fromJson(list.get(i).toString(), StudentResume.class);
                        dataList.add(studentResume);
                    }

                    if (dataList.size() == 0) {
                        statusLayoutManager.showEmptyLayout();
                    } else {
                        statusLayoutManager.showSuccessLayout();
                        if (list.length() == Integer.parseInt(total)) {
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                        }
                    }
                    commonDialog.dismiss();
//                    loadMoreWrapper.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
}
