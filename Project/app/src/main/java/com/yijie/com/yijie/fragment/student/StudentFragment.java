package com.yijie.com.yijie.fragment.student;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.Item;
import com.yijie.com.yijie.activity.student.StudentActivity;
import com.yijie.com.yijie.activity.student.studentgrounp.StundentGrounpActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 学生Fragement
 */
public class StudentFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    private List<Item> dataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_student;
    }

    @Override
    protected void initData() {
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        // 模拟获取数据
        getData();
        LoadMoreStudentWrapperAdapter loadMoreWrapperAdapter = new LoadMoreStudentWrapperAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreStudentWrapperAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              int type = dataList.get(position).getType();
                                                              Intent intent=new Intent();
                                                              intent.putExtra("type",type);
                                                              if (type==1||type==3||type==4){

                                                                  intent.setClass(mActivity,StudentActivity.class);
                                                              }else{
                                                                  intent.setClass(mActivity,StundentGrounpActivity.class);
                                                              }


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

                if (dataList.size() < 8) {
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

    private void getData() {
        //String.valueOf(letter)
        for (int i = 0; i < 8; i++) {
            if (i==0||i==1){
                //type=1待审核
                dataList.add(new Item(1,"王颖"));
            }else if (i==2){
                //type=2待分配
                dataList.add(new Item(2,"待分配实习生"));

            }else if (i==3){
                //type=3 已分配
                dataList.add(new Item(3,"胡瑞彩"));
            }
            else if (i==5||i==4){
                //type=4 未通过
                dataList.add(new Item(4,"宋明哲"));
            }
            else if (i==6){
                //type=5 实习生
                dataList.add(new Item(5,"实习生"));
            }
            else if (i==7){
                //type=6毕业生
                dataList.add(new Item(6,"毕业生"));
            }


        }
    }


}