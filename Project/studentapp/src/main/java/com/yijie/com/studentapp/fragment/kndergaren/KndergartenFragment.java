package com.yijie.com.studentapp.fragment.kndergaren;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.kndergard.KndergardAcitivity;
import com.yijie.com.studentapp.activity.kndergard.KndergardLoadingAcitivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.studentapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.studentapp.fragment.school.StudentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class KndergartenFragment extends BaseFragment {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_loading)
    RecyclerView recyclerLoading;
    Unbinder unbinder;
    //往前合作
    private List<StudentBean> dataList = new ArrayList<>();
    //正在招聘
    private List<StudentBean> dataListLoading = new ArrayList<>();
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private LoadMoreWrapper loadMoreWrapper;
    private LoadMoreWrapper loadMoreLoadingWrapper;

    //定义接口变量的get方法
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }


    @Override
    protected void lazyLoad() {

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

    //1、定义接口
    public interface OnButtonClick {
        public void onClick(View view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kendergard;
    }

    @Override
    protected void initData() {
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        //往前合作的园所
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        //正在招聘的园所
        recyclerLoading.setLayoutManager(new LinearLayoutManager(mActivity));
        title.setText("合作园所");
        back.setVisibility(View.GONE);
//        loading.setRetryListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loading.showContent();
//            }
//        });
//        loading.showContent();

        // 模拟获取数据
        getData();
        getLoadingData();


        LoadMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreKndergrtenAdapter(dataList, mActivity, R.layout.kendergard_adapter_item);
        final LoadMoreKndergrtenAdapter loadMoreLoadingWrapperAdapter = new LoadMoreKndergrtenAdapter(dataListLoading, mActivity, R.layout.kendergard_adapter_loading_item);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        loadMoreLoadingWrapper = new LoadMoreWrapper(loadMoreLoadingWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerLoading.setAdapter(loadMoreLoadingWrapper);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
        loadMoreLoadingWrapper .setLoadState(loadMoreWrapper.LOADING_ARROW);
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

        loadMoreLoadingWrapperAdapter.setOnItemClickListener(new LoadMoreKndergrtenAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(mActivity, KndergardLoadingAcitivity.class);
                                                              startActivity(intent);

                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recyclerLoading.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                dataListLoading.clear();
                getData();
                getLoadingData();
                loadMoreWrapper.notifyDataSetChanged();
                loadMoreLoadingWrapper.notifyDataSetChanged();

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

        //正在招聘园所加载更多监听
        loadMoreLoadingWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   if (dataList.size() < 15) {
                                                       // 模拟获取网络数据，延时1s
                                                       loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING);
                                                       new Timer().schedule(new TimerTask() {
                                                           @Override
                                                           public void run() {
                                                               mActivity.runOnUiThread(new Runnable() {
                                                                   @Override
                                                                   public void run() {

                                                                       getLoadingData();
                                                                       loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_ARROW);
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
//往期合作园所加载更多监听
        loadMoreWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataList.size() < 15) {
                    // 模拟获取网络数据，延时1s
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
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
        for (int i = 0; i < 5; i++) {

            dataList.add(new StudentBean(1, String.valueOf(letter)));

            letter++;
        }
    }
    private void getLoadingData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 2; i++) {

            dataListLoading.add(new StudentBean(1, String.valueOf(letter)));

            letter++;
        }
    }

}
