package com.yijie.com.kindergartenapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.RequestDetailActivity;
import com.yijie.com.kindergartenapp.activity.StudentResumnActivity;
import com.yijie.com.kindergartenapp.adapter.LoadMoreResumnListAdapter;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.kindergartenapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.kindergartenapp.bean.StudentuserKinderneed;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ViewUtils;
import com.yijie.com.kindergartenapp.view.CommomDialog;
import com.yijie.com.kindergartenapp.view.CustomDialog;

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
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/25.
 * 收到的简历碎片
 */

public class ResumnListFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private List<StudentuserKinderneed> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;
    private int currentPage=1;
    private int totalPage;
    private int kenderNeedId;
    private int    kinderId;
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected void initView() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(llRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
            @Override
            public void onEmptyChildClick(View view) {
                dataList.clear();
                currentPage = 1;
                getData();
            }

            @Override
            public void onErrorChildClick(View view) {
                dataList.clear();
                currentPage = 1;
                getData();
            }
                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();

        //获取父activity中得详情id
        RequestDetailActivity requestDetailActivity = (RequestDetailActivity) mActivity;
        kenderNeedId = requestDetailActivity.kenderNeedId;
        kinderId = requestDetailActivity.kinderId;
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LoadMoreResumnListAdapter loadMoreWrapperAdapter = new LoadMoreResumnListAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreResumnListAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              if (dataList.get(position).getStatus() == 0) {
                                                                  StudentuserKinderneed studentuserKinderneed = dataList.get(position);
                                                                  Integer studentUserId = studentuserKinderneed.getStudentUserId();
                                                                  Integer kinderNeedId = studentuserKinderneed.getKinderNeedId();
                                                                  Intent intent = new Intent();

                                                                  intent.putExtra("kinderId", kinderId);
                                                                  intent.putExtra("studentUserId", studentUserId);
                                                                  intent.putExtra("kinderNeedId", kinderNeedId);
                                                                  intent.setClass(mActivity, StudentResumnActivity.class);
                                                                  startActivity(intent);
                                                              }

                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                currentPage = 0;
                getData();
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
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
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

    @Override
    public void onResume() {
        isPrepared = true;
        currentPage=1;
        dataList.clear();

        initData();
        super.onResume();
    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }

        getData();

    }

    @Override
    protected void onInvisible() {

    }

    private void getData() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("pageSize", 10 + "");
        stringStringHashMap.put("kinderNeedId", kenderNeedId + "");
        getinstance.post(Constant.SELECTBYKINDERNEEDID, stringStringHashMap, new BaseCallback<String>() {
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
                        StudentuserKinderneed schoolSimple = gson.fromJson(jsonObject1.toString(), StudentuserKinderneed.class);
                        dataList.add(schoolSimple);
                    }
                    if (dataList.size() ==totalPage) {
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
    protected int getLayoutId() {
        return R.layout.fragment_resumnlist;
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
}
