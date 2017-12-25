package com.yijie.com.yijie.activity.recommend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.activity.recommend.recommendadapter.LoadMoreRecommendWrapperAdapter;
import com.yijie.com.yijie.activity.school.Item;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.fragment.KndergartenFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2017/12/21.
 * 推荐activity
 */

public class RecommendActivity extends BaseActivity {
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
    private List<Item> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_recommend);

    }
@OnClick(R.id.back)
public void goBack(){
        finish();
}
    @Override
    public void init() {
        setColor(RecommendActivity.this, Color.WHITE); // 改变状态栏的颜色
        setTranslucent(RecommendActivity.this); // 改变状态栏变成透明
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(RecommendActivity.this));
        title.setText("推荐");
        actionItem.setBackgroundResource(R.mipmap.search);
        // 模拟获取数据
        getData();
       LoadMoreRecommendWrapperAdapter loadMoreWrapperAdapter = new LoadMoreRecommendWrapperAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreRecommendWrapperAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(RecommendActivity.this, KndergardAcitivity.class);

                                                                intent.putExtra("isShowMove",true);


                                                              startActivity(intent);
                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(RecommendActivity.this, LinearLayoutManager.VERTICAL));
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

                if (dataList.size() < 26) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            RecommendActivity.this.runOnUiThread(new Runnable() {
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

    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 26; i++) {

                dataList.add(new Item(1, String.valueOf(letter)));


            letter++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
