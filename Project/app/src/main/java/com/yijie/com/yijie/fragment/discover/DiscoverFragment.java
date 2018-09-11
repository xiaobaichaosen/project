package com.yijie.com.yijie.fragment.discover;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.NewNoTrainActivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.student.SamplecCheckPendingActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.bean.school.SelfDiscovery;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.view.LoadingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/4/23.
 * 发现fragment
 */

public class DiscoverFragment extends BaseFragment {
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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private List<SelfDiscovery> dataList = new ArrayList<>();
    LoadMoreDiscoverAdapter loadMoreWrapperAdapter;
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        super.onResume();
    }

    @Override
    protected void initView() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        dataList.clear();
                        getDiscoverList();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        dataList.clear();
                        getDiscoverList();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        title.setText("发现");
        back.setVisibility(View.GONE);

        loadMoreWrapperAdapter = new LoadMoreDiscoverAdapter(dataList, R.layout.adapter_discover_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreDiscoverAdapter.OnItemClickListener() {


                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              //TODO 点击根据状态跳转到不同页面
                                                              Intent intent = new Intent();
                                                              int discoveryType = dataList.get(position).getDiscoveryType();
                                                              if (discoveryType==1){
                                                                  intent.setClass(mActivity, NewNoTrainActivity.class);
                                                                  startActivity(intent);
                                                              }else if (discoveryType==2){
                                                                  //审核简历
                                                                  intent.setClass(mActivity, SamplecCheckPendingActivity.class);
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
                getDiscoverList();
                loadMoreWrapperAdapter.notifyDataSetChanged();

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


    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        dataList.clear();
        getDiscoverList();

    }

    /**
     * 获取发现列表
     */
    private void getDiscoverList() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        String roleName = (String) SharedPreferencesUtils.getParam(mActivity, "roleName", "");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("roleName", roleName);
        getinstance.post(Constant.SELFDISCOVERY, stringStringHashMap, new BaseCallback<String>() {
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
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        SelfDiscovery selfDiscovery = gson.fromJson(jsonObject1.toString(), SelfDiscovery.class);
                        dataList.add(selfDiscovery);
                    }
                    loadMoreWrapperAdapter.notifyDataSetChanged();
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
                statusLayoutManager.showErrorLayout();
            }
        });
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
