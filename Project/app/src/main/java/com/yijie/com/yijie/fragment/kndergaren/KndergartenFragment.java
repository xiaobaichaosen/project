package com.yijie.com.yijie.fragment.kndergaren;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.activity.school.Item;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class KndergartenFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    @BindView(R.id.back)
    CheckBox back;
    Unbinder unbinder;
    @BindView(R.id.loading)
    LoadingLayout loading;
    private List<Item> dataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_school;
    }

    @Override
    protected void initData() {
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        loading.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.showContent();
            }
        });
        loading.showContent();

        // 模拟获取数据
        getData();
        LoadMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreKndergrtenAdapter(dataList,mActivity);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreKndergrtenAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(mActivity, KndergardAcitivity.class);
                                                              startActivity(intent);

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

                if (dataList.size() < 52) {
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

    //根据勾选状态设置不同适配器
    @OnCheckedChanged(R.id.back)
    void checkBoxCheckedChanged() {
        if (back.isChecked()) {
            LoadMoreMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreMoreKndergrtenAdapter(dataList);
            loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
            recyclerView.setAdapter(loadMoreWrapper);
            loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreMoreKndergrtenAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, KndergardAcitivity.class);
                    startActivity(intent);
                }
            });

        } else {
            LoadMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreKndergrtenAdapter(dataList,mActivity);
            loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
            recyclerView.setAdapter(loadMoreWrapper);
            loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreKndergrtenAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, KndergardAcitivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 26; i++) {

            dataList.add(new Item(1, String.valueOf(letter)));

            letter++;
        }
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
