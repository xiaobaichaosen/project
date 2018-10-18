package com.yijie.com.studentapp.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.studentapp.bean.KindergartenNeed;
import com.yijie.com.studentapp.fragment.yijie.LoadMoreYjAdapter;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 简历未被选择
 */
public class UnSelectFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loading)
    LinearLayout loading;
    Unbinder unbinder;
    private List<KindergartenNeed> dataList = new ArrayList<>();
    private String userId;
    private LoadMoreWrapper loadMoreWrapper;
    private int currentPage;
    private int totalPage;

    @Override
    protected void initView() {
        userId = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        LoadMoreYjAdapter loadMoreWrapperAdapter = new LoadMoreYjAdapter(dataList, R.layout.school_adapter_item_content);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                currentPage = 1;
                getData();


                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
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

//加载更多监听
        loadMoreWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        initData();
        super.onResume();
    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        dataList.clear();
        currentPage = 1;
        getData();
    }
    /**
     * 获取正在招聘的园所列表
     */
    public void getData() {
        String schoolId = (String) SharedPreferencesUtils.getParam(mActivity, "schoolId", "");
        final HttpUtils instance = HttpUtils.getinstance(mActivity);
        Map map = new HashMap();
        map.put("schoolId", schoolId);
        map.put("studentUserId", userId);
        map.put("pageStart", currentPage + "");


        instance.post(Constant.SELECTBEINGRECRUITLIST, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                currentPage++;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (data != null) {
                        Gson gson = new Gson();
                        totalPage = data.getInt("total");
                        for (int i = 0; i < data.getJSONArray("list").length(); i++) {
                            KindergartenNeed kindergartenNeed = gson.fromJson(data.getJSONArray("list").getJSONObject(i).toString(), KindergartenNeed.class);
                            dataList.add(kindergartenNeed);
                        }
                    }

                    if (dataList.size() == totalPage) {
                        // 显示加载到底的提示
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    loadMoreWrapper.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
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
    protected int getLayoutId() {
        return R.layout.fragment_uncheck;
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

