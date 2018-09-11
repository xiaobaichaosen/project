package com.yijie.com.kindergartenapp.fragment.discover;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.KenderDetailAcitivity;
import com.yijie.com.kindergartenapp.activity.RecruitmentActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.bean.KindergartenDiscovery;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.SelfDiscovery;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.view.LoadingLayout;

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

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private List<KindergartenDiscovery> dataList = new ArrayList<>();
    LoadMoreDiscoverAdapter loadMoreWrapperAdapter;
    @BindView(R.id.loading)
    LinearLayout llRoot;
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
        statusLayoutManager = new StatusLayoutManager.Builder(llRoot)
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
                                                              if (discoveryType == 1) {
                                                                  intent.putExtra("kinderId",dataList.get(position).getKinderId());
                                                                  intent.putExtra("discoveryType",dataList.get(position).getDiscoveryType());
                                                                  intent.setClass(mActivity, RecruitmentActivity.class);
                                                                  startActivity(intent);
                                                              }
// else if (discoveryType==2){
//                                                                  //审核简历
//                                                                  intent.setClass(mActivity, SamplecCheckPendingActivity.class);
//                                                                  startActivity(intent);
//                                                              }


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

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取发现列表
     */
    private void getDiscoverList() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        String kinderId = (String) SharedPreferencesUtils.getParam(mActivity, "userId", "");
        stringStringHashMap.put("kinderId", kinderId );

        getinstance.post(Constant.SELECTBYKINDERID, stringStringHashMap, new BaseCallback<String>() {
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
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONArray    list = jsonObject.getJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        KindergartenDiscovery kindergartenDiscovery = gson.fromJson(jsonObject1.toString(), KindergartenDiscovery.class);
                        dataList.add(kindergartenDiscovery);
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
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
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
