package com.yijie.com.yijie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.SchoolProjectDetailActivity;
import com.yijie.com.yijie.activity.TempActivity;
import com.yijie.com.yijie.activity.kendergard.KendergardStatusActivity;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.bean.bean.KindergartenNeed;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreLoadingKndergrtenAdapter;
import com.yijie.com.yijie.fragment.school.LoadMoreSchoolAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;

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
 * 园所需求
 */
public class KinderNeedFragment  extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    Unbinder unbinder;
    @BindView(R.id.rl_root)
    NestedScrollView rlRoot;
    private int currentPage = 1;
    private int totalPage;
    private int loadingTotal;
    private String schoolId;
    private StatusLayoutManager statusLayoutManager;
    private List<List<KindergartenNeed>> loadingList = new ArrayList<>();
    private LoadMoreWrapper loadMoreLoadingWrapper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    public void onResume() {
        isPrepared = true;

        initData();
        super.onResume();
    }

    @Override
    protected void initView() {
        recyclerView.setNestedScrollingEnabled(false);
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(rlRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        currentPage = 1;
                        loadingList.clear();
                        getLoadingData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage = 1;
                        loadingList.clear();
                        getLoadingData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();


        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        //正在招聘园所
        LoadMoreLoadingKndergrtenAdapter loadMoreLoadingKndergrtenAdapter = new LoadMoreLoadingKndergrtenAdapter(loadingList, mActivity, R.layout.fragment_adapter_loading_item);
        loadMoreLoadingWrapper = new LoadMoreWrapper(loadMoreLoadingKndergrtenAdapter);
        recyclerView.setAdapter(loadMoreLoadingWrapper);
        loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_ARROW);
        loadMoreLoadingKndergrtenAdapter.setOnItemClickListener(new LoadMoreLoadingKndergrtenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(mActivity, KndergardAcitivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                loadingList.clear();
                currentPage = 1;
                getLoadingData();

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

                if (loadingList.size() < loadingTotal) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getLoadingData();
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
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        loadingList.clear();
        currentPage = 1;
        getLoadingData();

    }

       /**
         * 获取正在招聘的园所
        */

    private void getLoadingData() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", "0");
        stringStringHashMap.put("pageSize", "10");
        getinstance.post(Constant.SELECTGROUNPLIST, stringStringHashMap, new BaseCallback<String>() {
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
                Gson gson=new Gson();
                JSONArray list=null;
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    loadingTotal = data.getInt("total");
                    list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ArrayList<KindergartenNeed> kindergartenNeeds = new ArrayList<>();
                        JSONArray jsonArray = (JSONArray) list.get(i);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            KindergartenNeed kindergartenNeed = gson.fromJson(jsonArray.getJSONObject(j).toString(), KindergartenNeed.class);
                            kindergartenNeeds.add(kindergartenNeed);
                        }
                        loadingList.add(kindergartenNeeds);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (null!=list){
                    if (loadingTotal==list.length()){
                        // 显示加载到底的提示
                        loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_END);
                    }
                    loadMoreLoadingWrapper.notifyDataSetChanged();
                    if (list.length() == 0) {
                        statusLayoutManager.showEmptyLayout();
                    } else {
                        statusLayoutManager.showSuccessLayout();
                    }
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
