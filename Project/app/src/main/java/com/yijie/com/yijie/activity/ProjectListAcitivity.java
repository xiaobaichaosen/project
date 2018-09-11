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
import com.yijie.com.yijie.activity.search.ProjectListSearchActivity;
import com.yijie.com.yijie.activity.search.SchoolListSearchActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.fragment.school.LoadMoreSchoolAdapter;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/5/21.
 * 项目列表
 */

public class ProjectListAcitivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    Unbinder unbinder;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    private int currentPage = 1;
    private int totalPage;

    private List<SchoolSimple> dataList;
    private String schoolId;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_project_list);
    }

    @Override
    protected void onResume() {
        dataList.clear();
        currentPage=1;
        getProjectList();
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
                        getProjectList();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getProjectList();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        dataList = new ArrayList<>();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("项目列表");
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setBackgroundResource(R.mipmap.search);
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadMoreSchoolAdapter loadMoreWrapperAdapter = new LoadMoreSchoolAdapter(true,dataList, R.layout.activity_projectlist_item);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
//        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_GONE);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreSchoolAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.putExtra("schoolId", dataList.get(position).schoolId);
                                                              intent.putExtra("practiceId", dataList.get(position).id);
                                                              intent.putExtra("projectName", dataList.get(position).projectName);
                                                              intent.putExtra("status", dataList.get(position).status);
                                                              intent.putExtra("zjNumber",dataList.get(position).telephone);
                                                              intent.putExtra("schoolName", dataList.get(position).name);
                                                              intent.putExtra("schoolContact",dataList.get(position).user_name);
                                                              intent.putExtra("schoolContactPhone",dataList.get(position).cellphone);
                                                              intent.putExtra("logo",dataList.get(position).litimg);
                                                              intent.setClass(ProjectListAcitivity.this, ProjectDetailActivity.class);
                                                              startActivity(intent);
                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(ProjectListAcitivity.this, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                currentPage = 1;
                getProjectList();
                //去掉，否则刷新的时候会看到五加载更多
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

                if (dataList.size() < totalPage) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getProjectList();
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

    private void getProjectList() {
        String json = (String) SharedPreferencesUtils.getParam(ProjectListAcitivity.this, "user", "");
        int userId = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils getinstance = HttpUtils.getinstance(ProjectListAcitivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("userId", userId + "");
        stringStringHashMap.put("pageSize", 10 + "");
        stringStringHashMap.put("schoolId", "0");
        getinstance.post(Constant.PROJECTLIST, stringStringHashMap, new BaseCallback<String>() {
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
                currentPage++;
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
                if (dataList.size()==0){
                    statusLayoutManager.showEmptyLayout();
                }else {
                    statusLayoutManager.showSuccessLayout();
                }
                commonDialog.dismiss();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
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

    @OnClick({R.id.back, R.id.action_item})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.action_item:
                intent.setClass(ProjectListAcitivity.this, ProjectListSearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
