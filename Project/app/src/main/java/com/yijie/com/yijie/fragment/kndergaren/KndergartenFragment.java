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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.bean.KindergartenNeed;
import com.yijie.com.yijie.utils.LogUtil;

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    //    @BindView(R.id.back)
//    CheckBox back;
    Unbinder unbinder;
    //    @BindView(R.id.loading)
//    LoadingLayout loading;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.clearEditText)
    EditText clearEditText;
    @BindView(R.id.iv_delect)
    ImageView ivDelect;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.app_title)
    RelativeLayout appTitle;
    @BindView(R.id.recycler_loading)
    RecyclerView recyclerLoading;
    @BindView(R.id.recycler_company)
    RecyclerView recyclerCompany;
    private List<StudentBean> dataList = new ArrayList<>();
    private List<List<KindergartenNeed>> loadingList = new ArrayList<>();
    private List<StudentBean> companyList = new ArrayList<>();
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private LoadMoreWrapper loadMoreLoadingWrapper;
    private LoadMoreWrapper loadMoreCompanyWrapper;

    //定义接口变量的get方法
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
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
    public void onResume() {
        super.onResume();
        isPrepared=true;
        LogUtil.e("onResume="+isVisible);
        initData();
    }

    @Override
    protected void initView() {
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        LinearLayoutManager loadingLinearLayoutManager = new LinearLayoutManager(mActivity);
        LinearLayoutManager companyLinearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setHasFixedSize(true);
        recyclerCompany.setHasFixedSize(true);
        recyclerLoading.setHasFixedSize(true);
        setLinearLayout(linearLayoutManager, recyclerView);
        setLinearLayout(companyLinearLayoutManager, recyclerCompany);
        setLinearLayout(loadingLinearLayoutManager, recyclerLoading);
        actionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace()
//4、如果接口成员变量不为空null，则调用接口变量的方法。
                if (onButtonClick != null) {
                    onButtonClick.onClick(view);
                }
            }
        });
        //往期园所
        LoadMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreKndergrtenAdapter(dataList, mActivity, R.layout.kendergard_adapter_company);
        //正在招聘园所
        LoadMoreLoadingKndergrtenAdapter loadMoreLoadingKndergrtenAdapter = new LoadMoreLoadingKndergrtenAdapter(loadingList, mActivity, R.layout.fragment_adapter_loading_item);
        //正在合作的园所
        LoadMoreCompanyKndergrtenAdapter loadMoreCompanyKndergrtenAdapter = new LoadMoreCompanyKndergrtenAdapter(companyList, mActivity, R.layout.kendergard_adapter_company);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        loadMoreLoadingWrapper = new LoadMoreWrapper(loadMoreLoadingKndergrtenAdapter);
        loadMoreCompanyWrapper = new LoadMoreWrapper(loadMoreCompanyKndergrtenAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerLoading.setAdapter(loadMoreLoadingWrapper);
        recyclerCompany.setAdapter(loadMoreCompanyWrapper);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
        loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_ARROW);
        loadMoreCompanyWrapper.setLoadState(loadMoreCompanyWrapper.LOADING_ARROW);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreKndergrtenAdapter.OnItemClickListener() {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(mActivity, KndergardAcitivity.class);
                                                              startActivity(intent);
                                                          }
                                                      }
        );
        loadMoreLoadingKndergrtenAdapter.setOnItemClickListener(new LoadMoreLoadingKndergrtenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(mActivity, KndergardAcitivity.class);
                startActivity(intent);
            }
        });

        loadMoreCompanyKndergrtenAdapter.setOnItemClickListener(new LoadMoreCompanyKndergrtenAdapter.OnItemClickListener() {
                                                                    @Override
                                                                    public void onItemClick(View view, int position) {
                                                                        Intent intent = new Intent();
                                                                        intent.setClass(mActivity, KndergardAcitivity.class);
                                                                        startActivity(intent);
                                                                    }
                                                                }
        );
//
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
//        recyclerLoading.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recyclerCompany.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                loadingList.clear();
                companyList.clear();
                getData();
//                getLoadingData();
                getCompanyData();
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
                loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING_ARROW);
                loadMoreCompanyWrapper.setLoadState(loadMoreCompanyWrapper.LOADING_ARROW);

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

        // 设置往期合作加载更多监听

        loadMoreWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataList.size() < 6) {
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

        // 设置正在招聘

        loadMoreLoadingWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadingList.size() < 6) {
                    // 模拟获取网络数据，延时1s
                    loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    getLoadingData();
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
        // 设置正在合作
        loadMoreCompanyWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyList.size() < 6) {
                    // 模拟获取网络数据，延时1s
                    loadMoreCompanyWrapper.setLoadState(loadMoreCompanyWrapper.LOADING);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getCompanyData();
                                    loadMoreCompanyWrapper.setLoadState(loadMoreCompanyWrapper.LOADING_ARROW);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreCompanyWrapper.setLoadState(loadMoreCompanyWrapper.LOADING_END);
                }
            }
        });

    }

    @Override
    protected void initData() {
        // TODO 正常应该是!isVisble,待解决
        LogUtil.e("initData="+isVisible);
        if(!isPrepared || !isVisible) {
            return;
        }


        // 模拟获取数据
        getData();
//        getLoadingData();
        getCompanyData();


    }

    /**
     * 解决嵌套卡顿问题
     *
     * @param linearLayoutManager
     * @param recyclerView
     */
    private void setLinearLayout(LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }


    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 2; i++) {
            dataList.add(new StudentBean(1, String.valueOf(letter)));
            letter++;
        }
    }

//    private void getLoadingData() {
//        //String.valueOf(letter)
//        char letter = 'A';
//        for (int i = 0; i < 2; i++) {
//            if (i==0){
//                ArrayList<StudentBean> studentBeans = new ArrayList<>();
//                for (int j = 0; j < 3; j++) {
//                    StudentBean studentBean = new StudentBean(1, String.valueOf(letter));
//                    studentBeans.add(studentBean);
//
//                    letter++;
//                }
//                loadingList.add(studentBeans);
//            }
//            char letter2 = 'F';
//            if (i==1){
//                ArrayList<StudentBean> studentBeans = new ArrayList<>();
//                for (int j = 0; j < 1; j++) {
//                    StudentBean studentBean = new StudentBean(1, String.valueOf(letter2));
//                    studentBeans.add(studentBean);
//
//                    letter++;
//                }
//                loadingList.add(studentBeans);
//            }
//        }
//
//
//
//    }

    private void getCompanyData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 2; i++) {
            companyList.add(new StudentBean(1, String.valueOf(letter)));
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
