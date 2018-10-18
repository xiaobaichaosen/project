package com.yijie.com.studentapp.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.kndergard.KndergardAcitivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.studentapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.studentapp.fragment.yijie.LoadMoreSchoolAdapter;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/2/28.
 * 往届实习项目详情
 */

public class PracticeDetailsActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<StudentBean> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_practicedetails);

    }

    @Override
    public void init() {
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 模拟获取数据
        getData();


//        LoadMoreSchoolAdapter loadMoreWrapperAdapter = new LoadMoreSchoolAdapter(dataList, R.layout.kendergard_adapter_practice_item);
//        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
//        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreSchoolAdapter.OnItemClickListener(
//
//                                                      ) {
//                                                          @Override
//                                                          public void onItemClick(View view, int position) {
//                                                              Intent intent = new Intent();
//                                                              intent.setClass(PracticeDetailsActivity.this, KndergardAcitivity.class);
//                                                              startActivity(intent);
//                                                          }
//                                                      }
//        );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
                            runOnUiThread(new Runnable() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 26; i++) {

            dataList.add(new StudentBean(1, String.valueOf(letter)));

            letter++;
        }
    }
}
