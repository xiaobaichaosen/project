package com.yijie.com.yijie.activity.kendergard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.SchoolActivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.school.adapter.SchoolContactAdapterRecyclerView;
import com.yijie.com.yijie.activity.school.adapter.SchoolMemeryAdapterRecyclerView;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.bean.KindergartenNeed;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenFragment;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreCompanyKndergrtenAdapter;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreKndergrtenAdapter;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreLoadingKndergrtenAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.LoadingLayout;

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
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/28.
 * 正在招聘，合作，往期合作的园所页面
 */

public class KendergardStatusActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    //    @BindView(R.id.back)
//    CheckBox back;
    Unbinder unbinder;
    @BindView(R.id.loading)
    LinearLayout loading;
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
    @BindView(R.id.back)
    TextView back;
    private List<StudentBean> dataList = new ArrayList<>();
    private List<List<KindergartenNeed>> loadingList = new ArrayList<>();
    private List<StudentBean> companyList = new ArrayList<>();
    private KndergartenFragment.OnButtonClick onButtonClick;//2、定义接口成员变量
    private LoadMoreWrapper loadMoreLoadingWrapper;
    private LoadMoreWrapper loadMoreCompanyWrapper;
    private StatusLayoutManager statusLayoutManager;
   private int loadingTotal;
    //定义接口变量的get方法
    public KndergartenFragment.OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(KndergartenFragment.OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
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


    @Override
    public void setContentView() {
        setContentView(R.layout.fragment_kendergard);
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(loading)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
//                        currentPage = 0;
                        dataList.clear();
                        loadingList.clear();
                        companyList.clear();
                        getData();
                        getLoadingData();
                        getCompanyData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
//                        currentPage = 0;
                        dataList.clear();
                        loadingList.clear();
                        companyList.clear();
                        getData();
                        getLoadingData();
                        getCompanyData();
                    }
                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        // 模拟获取数据
        getData();
        getLoadingData();
        getCompanyData();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager loadingLinearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager companyLinearLayoutManager = new LinearLayoutManager(this);
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
        LoadMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreKndergrtenAdapter(dataList, this, R.layout.kendergard_adapter_company);
        //正在招聘园所
        LoadMoreLoadingKndergrtenAdapter loadMoreLoadingKndergrtenAdapter = new LoadMoreLoadingKndergrtenAdapter(loadingList, this, R.layout.fragment_adapter_loading_item);
        //正在合作的园所
        LoadMoreCompanyKndergrtenAdapter loadMoreCompanyKndergrtenAdapter = new LoadMoreCompanyKndergrtenAdapter(companyList, this, R.layout.kendergard_adapter_company);
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
                                                              intent.setClass(KendergardStatusActivity.this, KndergardAcitivity.class);
                                                              startActivity(intent);
                                                          }
                                                      }
        );
        loadMoreLoadingKndergrtenAdapter.setOnItemClickListener(new LoadMoreLoadingKndergrtenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(KendergardStatusActivity.this, KndergardAcitivity.class);
                startActivity(intent);
            }
        });

        loadMoreCompanyKndergrtenAdapter.setOnItemClickListener(new LoadMoreCompanyKndergrtenAdapter.OnItemClickListener() {
                                                                    @Override
                                                                    public void onItemClick(View view, int position) {
                                                                        Intent intent = new Intent();
                                                                        intent.setClass(KendergardStatusActivity.this, KndergardAcitivity.class);
                                                                        startActivity(intent);
                                                                    }
                                                                }
        );
//
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recyclerLoading.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recyclerCompany.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                loadingList.clear();
                companyList.clear();
                getData();
                getLoadingData();
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
                            runOnUiThread(new Runnable() {
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
                if (loadingList.size() < loadingTotal) {
                    // 模拟获取网络数据，延时1s
                    loadMoreLoadingWrapper.setLoadState(loadMoreLoadingWrapper.LOADING);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
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
                            runOnUiThread(new Runnable() {
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
//            if (i == 0) {
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
//            if (i == 1) {
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
////    }
//
//        /**
//         * 获取正在招聘的园所
//         */
////
//
    private void getLoadingData() {

        HttpUtils getinstance = HttpUtils.getinstance(KendergardStatusActivity.this);
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
                        loadMoreCompanyWrapper.setLoadState(loadMoreCompanyWrapper.LOADING_END);
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

    private void getCompanyData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 2; i++) {
            companyList.add(new StudentBean(1, String.valueOf(letter)));
            letter++;
        }
    }
}
