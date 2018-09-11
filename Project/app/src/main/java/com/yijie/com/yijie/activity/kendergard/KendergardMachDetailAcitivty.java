package com.yijie.com.yijie.activity.kendergard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.student.StudentActivity;
import com.yijie.com.yijie.adapter.LoadMoreMatchWrapperAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.bean.StudentuserKinderneed;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;

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
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/4/27.
 */

public class KendergardMachDetailAcitivty extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.recycler_studentSample)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_root)
    LinearLayout rlRoot;
    @BindView(R.id.tv_projectName)
    TextView tvProjectName;
    @BindView(R.id.tv_updateTime)
    TextView tvUpdateTime;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.tv_studentNumber)
    TextView tvStudentNumber;
    @BindView(R.id.tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.view_gone)
    View viewGone;
    private List<StudentuserKinderneed> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;
    private StatusLayoutManager statusLayoutManager;
    private int currentPage = 0;
    private int kinderNeedId;
    private int loadingTotal;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kendergard_matchdetail);
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(rlRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        currentPage = 0;
                        dataList.clear();
                        getData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage = 0;
                        dataList.clear();
                        getData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        String kinderName = getIntent().getStringExtra("kinderName");
        kinderNeedId = getIntent().getIntExtra("kinderNeedId", 0);
        title.setText(kinderName);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        title.setText("国际幼儿园");
        actionItem.setVisibility(View.GONE);
        actionItem.setBackgroundResource(R.mipmap.search);
        // 模拟获取数据
        getData();
        LoadMoreMatchWrapperAdapter loadMoreWrapperAdapter = new LoadMoreMatchWrapperAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreMatchWrapperAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(KendergardMachDetailAcitivty.this, StudentActivity.class);
                                                              intent.putExtra("isShowMove", true);
                                                              startActivity(intent);
                                                          }
                                                      }
        );
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

                if (dataList.size() < loadingTotal) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            KendergardMachDetailAcitivty.this.runOnUiThread(new Runnable() {
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
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("pageSize", 10 + "");
        stringStringHashMap.put("kinderNeedId", kinderNeedId + "");

        getinstance.post(Constant.SELECTKINDERDEMANDDETAILS, stringStringHashMap, new BaseCallback<String>() {
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
                currentPage++;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    loadingTotal = data.getInt("total");
                    int deliveryKinderNum = data.getInt("deliveryKinderNum");
                    int kinderChoiceNum = data.getInt("kinderChoiceNum");
                    int kinderGiveUpNum = data.getInt("kinderGiveUpNum");

                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        StudentuserKinderneed studentuserKinderneed = gson.fromJson(list.getJSONObject(i).toString(), StudentuserKinderneed.class);
                        dataList.add(studentuserKinderneed);
                        if (i == 0) {
                            tvProjectName.setText(studentuserKinderneed.getProjectName());
                            tvSchoolName.setText(studentuserKinderneed.getSchoolName());
                            tvStudentNumber.setText("需求:"+studentuserKinderneed.getStudentNum());
//                            tvUpdateTime.setText(studentuserKinderneed.getu);
                        }
                    }

                    if (dataList.size() == 0) {
                        statusLayoutManager.showEmptyLayout();
                    } else {
                        viewGone.setVisibility(View.VISIBLE);
                        //有人报名，没有处理,
                        if (deliveryKinderNum>0&&kinderChoiceNum==0&&kinderGiveUpNum==0){
                           tvPeople.setText("投递:"+deliveryKinderNum);
                            //有人报名，都是已选,
                        }else  if(deliveryKinderNum>0&&kinderChoiceNum>0&&kinderGiveUpNum==0){
                           tvPeople.setText("投递:"+deliveryKinderNum+";已选:"+kinderChoiceNum);
                            //有人报名，都是放弃
                        }else  if(deliveryKinderNum>0&&kinderChoiceNum==0&&kinderGiveUpNum>0){
                            tvPeople.setText("投递:"+deliveryKinderNum+";放弃:"+kinderGiveUpNum);
                            //有人报名，有已选，有放弃
                        }else  if(deliveryKinderNum>0&&kinderChoiceNum>0&&kinderGiveUpNum>0){
                          tvPeople.setText("投递:"+deliveryKinderNum+";已选:"+kinderChoiceNum+";放弃:"+kinderGiveUpNum);
                        }else {
                            //提完需求没人报名
                          tvPeople.setVisibility(View.GONE);
                        }
                        if (list.length()==loadingTotal){
                            // 显示加载到底的提示
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                        }
                        statusLayoutManager.showSuccessLayout();
                    }
                    loadMoreWrapper.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
