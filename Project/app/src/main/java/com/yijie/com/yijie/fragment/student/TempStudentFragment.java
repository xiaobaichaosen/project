package com.yijie.com.yijie.fragment.student;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.student.StudentActivity;
import com.yijie.com.yijie.activity.student.studentgrounp.StundentGrounpActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.base.search.AppSearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 学生Fragement
 */
public class TempStudentFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    @BindView(R.id.iv)
    ImageView iv;
    //    @BindView(R.id.clearEditText)
//    EditText clearEditText;

    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.app_title)
    RelativeLayout appTitle;
    Unbinder unbinder;
    @BindView(R.id.clearEditText)
    EditText clearEditText;
    @BindView(R.id.iv_delect)
    ImageView ivDelect;

    private List<StudentBean> dataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_student;
    }

    @Override
    protected void initView() {

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
                                                              Intent intent = new Intent();
                                                              intent.putExtra("type", type);
                                                              if (type == 1 || type == 3 || type == 4) {

                                                                  intent.setClass(mActivity, StudentActivity.class);
                                                              } else {
                                                                  intent.setClass(mActivity, StundentGrounpActivity.class);
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
        //type=1待审核 //type=2待分配  //type=3 已分配  //type=4 未通过  //type=5 实习生//type=6毕业生
        for (int i = 0; i < 8; i++) {
            if (i == 0 || i == 1) {
                //type=1待审核
                dataList.add(new StudentBean(1, "王颖"));
            } else if (i == 2) {
                //type=2待分配
                dataList.add(new StudentBean(2, "待分配实习生"));

            } else if (i == 3) {
                //type=3 已分配
                dataList.add(new StudentBean(3, "胡瑞彩"));
            } else if (i == 5 || i == 4) {
                //type=4 未通过
                dataList.add(new StudentBean(4, "宋明哲"));
            } else if (i == 6) {
                //type=5 实习生
                dataList.add(new StudentBean(5, "实习生"));
            } else if (i == 7) {
                //type=6毕业生
                dataList.add(new StudentBean(6, "毕业生"));
            }


        }
    }

    @OnClick(R.id.clearEditText)
    public void Click(View view){
        Intent intent = new Intent(mActivity, AppSearchActivity.class);

        //创建一个rect 对象来存储共享元素的位置信息
//        Rect rect = new Rect();
//        //获取元素的位置信息
//        view.getGlobalVisibleRect(rect);
//        //将位置信息附加到intent 上
//        intent.setSourceBounds(rect);
        startActivity(intent);
        //用于屏蔽 activity 默认的转场动画效果
        mActivity.overridePendingTransition(0,0);
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