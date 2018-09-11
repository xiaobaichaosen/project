package com.yijie.com.yijie.fragment.student;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.student.HasPassedActivity;
import com.yijie.com.yijie.activity.student.NotPassActivity;
import com.yijie.com.yijie.activity.student.SamplecCheckPendingActivity;
import com.yijie.com.yijie.activity.student.studentgrounp.InternshipActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/4/23.
 * 发现fragment
 */

public class StudentFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private List<StudentBean> dataList = new ArrayList<>();
    LoadMoreWrapper loadMoreWrapper;
    LoadMoreStudentAdapter2 loadMoreWrapperAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_student;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared=true;
        LogUtil.e("onResume="+isVisible);
        initData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // TODO 正常应该是!isVisble,待解决
        LogUtil.e("initData="+isVisible);
        if(!isPrepared || !isVisible) {
            return;
        }
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        // 模拟获取数据
        getData();

        loadMoreWrapperAdapter = new LoadMoreStudentAdapter2(dataList, R.layout.adapter_studentstatus_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreStudentAdapter2.OnItemClickListener( ) {


                     @Override
                   public void onItemClick(View view, int position) {
                         //TODO 点击根据状态跳转到不同页面
                         Intent intent = new Intent();
                         if (position==0){
                             //待审核
                             intent.setClass(mActivity, SamplecCheckPendingActivity.class);
                             intent.putExtra("page",0);
                             startActivity(intent);
                             //未通过
                         }else if (position==2){
                             intent.setClass(mActivity, NotPassActivity.class);
                             intent.putExtra("page",2);
                             startActivity(intent);
                         }else if (position==3){
                             intent.setClass(mActivity, HasPassedActivity.class);
                             intent.putExtra("page",3);
                             startActivity(intent);
                         }else if (position==4){
                             intent.setClass(mActivity, InternshipActivity.class);
                             intent.putExtra("page",4);
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
                getData();
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

//        // 设置加载更多监听
//        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
//
//                if (dataList.size() < 52) {
//                    // 模拟获取网络数据，延时1s
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            mActivity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    getData();
//                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
//                                }
//                            });
//                        }
//                    }, 1000);
//                } else {
//                    // 显示加载到底的提示
//                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
//                }
//            }
//        });
    }


    private void getData() {
        //String.valueOf(letter)
        for (int i = 0; i < 6; i++) {
            StudentBean studentBean=new StudentBean();
            studentBean.setName("简历待审核"+1+i);
            studentBean.setType(1);
            studentBean.setPhoneNumber(i+"5");
            dataList.add(studentBean);

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
