package com.yijie.com.yijie.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.KendergardStatusActivity;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.adapter.LoadMoreNewAdapter;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;
import com.yijie.com.yijie.bean.bean.StayBean;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;

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
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 新园所
 */
public class NewKinderFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;
    @BindView(R.id.rl_root)
    NestedScrollView rlRoot;

    private int currentPage = 1;
    private int totalPage;
    private int loadingTotal;

    private String schoolId;
    private StatusLayoutManager statusLayoutManager;
    private List<KindergartenDetail> loadingList = new ArrayList<>();
    private LoadMoreWrapper loadMoreLoadingWrapper;
    private String status;

    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_new;

    }
    //不能再onDestroy方法解绑，否则会内存溢出
    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
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
        statusLayoutManager = new StatusLayoutManager.Builder(rlRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        currentPage = 1;
                        loadingList.clear();
                        getNewKinder(status);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage = 1;
                        loadingList.clear();
                        getNewKinder(status);
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
        LoadMoreNewAdapter LoadMoreCompayAdapter = new LoadMoreNewAdapter(loadingList, R.layout.fragment_new_item);
        loadMoreLoadingWrapper = new LoadMoreWrapper(LoadMoreCompayAdapter);
        recyclerView.setAdapter(loadMoreLoadingWrapper);
        loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_ARROW);
        LoadMoreCompayAdapter.setOnItemClickListener(new LoadMoreNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                Bundle mBundle = new Bundle();
                if (null != loadingList.get(position)) {
                    intent.putExtra("isHidden",true);
                    if (status.equals("200")){
                        //待审核有审核button
                        intent.putExtra("isShow",true);
                    }

                    mBundle.putSerializable("kenderDetail", loadingList.get(position));
                    intent.putExtras(mBundle);
                }
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
                getNewKinder(status);

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
                loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING);

                if (loadingList.size() < loadingTotal) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getNewKinder(status);
                                    loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_END);
                }
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void string(String  string) {
        loadingList.clear();
        currentPage = 1;

        if (string.equals("全部")){
            status="100";
        }else if (string.equals("待审核")){
            status="200";
        }else if (string.equals("未通过")){
            status="201";
        }else if (string.equals("已通过")){
            status="202";
        }else if (string.equals("注册中")){
            status="203";
        }
        getNewKinder(status);


    }
    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        loadingList.clear();
        currentPage = 1;
        KendergardStatusActivity kendergardStatusActivity= (KendergardStatusActivity) mActivity;
       String texString= kendergardStatusActivity.textString;
        if (texString.equals("新园所")){
            status="100";
        }else if (texString.equals("待审核")){
            status="200";
        }else if (texString.equals("未通过")){
            status="201";
        }else if (texString.equals("已通过")){
            status="202";
        }else if (texString.equals("注册中")){
            status="203";
        }
        getNewKinder(status);

    }

    /**
     * 获取新园所
     */

    private void getNewKinder(String status) {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("pageSize", "10");
        stringStringHashMap.put("status", status);
        getinstance.post(Constant.SELECTCOOPERGARDENANDNEWGARDEN, stringStringHashMap, new BaseCallback<String>() {
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
                LogUtil.e("正在合作园所===" + o);
                currentPage++;
                Gson gson = new Gson();
                JSONArray list = null;
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    loadingTotal = data.getInt("total");
                    list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        KindergartenDetail studentuserKinderneed = gson.fromJson(list.getJSONObject(i).toString(), KindergartenDetail.class);
                        loadingList.add(studentuserKinderneed);
                    }
                    loadMoreLoadingWrapper.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (null != list) {
                    if (loadingTotal == list.length()) {
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

