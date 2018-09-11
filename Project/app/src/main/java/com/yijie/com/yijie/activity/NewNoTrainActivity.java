package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.SchoolActivity;
import com.yijie.com.yijie.adapter.LoadMoreNoTrainAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.view.CircleImageView;
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
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/4/23.
 * 新增待培训项目页面
 */

public class NewNoTrainActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.action_item)
    ImageView actionItem;



    private List<SchoolPractice> dataList = new ArrayList<>();
    LoadMoreNoTrainAdapter loadMoreWrapperAdapter;
    private int currentPage=1;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newnotrain);
    }

    @Override
    protected void onResume() {
        dataList.clear();
        currentPage=1;
        getProjectList();
        super.onResume();
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getProjectList();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        currentPage=1;
                        dataList.clear();
                        getProjectList();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        title.setText("新增待培训项目");
        actionItem.setBackgroundResource(R.mipmap.setting);


        loadMoreWrapperAdapter = new LoadMoreNoTrainAdapter(dataList, R.layout.adapter_newnotrain_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);

        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreNoTrainAdapter.OnItemClickListener() {


                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              //TODO 将项目id传递给后台，如果接送项目，按钮置灰
                                                              Intent intent = new Intent();
                                                              intent.putExtra("schoolId", dataList.get(position).getSchoolId()+"");
                                                              intent.putExtra("practiceId", dataList.get(position).getId()+"");
                                                              intent.putExtra("schoolName", dataList.get(position).getName());
                                                              intent.putExtra("projectName", dataList.get(position).getProjectName());
                                                              intent.putExtra("isShowLayout", true);
                                                              intent.putExtra("zjNumber",dataList.get(position).getTelephone());
                                                              intent.putExtra("schoolContact",dataList.get(position).getUser_name());
                                                              intent.putExtra("schoolContactPhone",dataList.get(position).getCellphone());
                                                              intent.putExtra("logo",dataList.get(position).getLitimg());
                                                              intent.setClass(NewNoTrainActivity.this, ProjectDetailActivity.class);
                                                              startActivity(intent);
                                                              readPracticeUpdate(dataList.get(position).getId()+"");
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
                getProjectList();
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


    }

    /**
     * 获取新增待培训列表
     * 分页待定
     */
    private void getProjectList() {
        HttpUtils getinstance = HttpUtils.getinstance(NewNoTrainActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("state", "1");
        getinstance.post(Constant.SELECTTRAINLIST, stringStringHashMap, new BaseCallback<String>() {
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
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        SchoolPractice schoolPractice = gson.fromJson(jsonObject1.toString(), SchoolPractice.class);
                        dataList.add(schoolPractice);
                    }
                    loadMoreWrapperAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dataList.size() == 0) {
                    statusLayoutManager.showEmptyLayout();
                } else {
                    statusLayoutManager.showSuccessLayout();
                }
                commonDialog.dismiss();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }
        });

    }

    /**
     * 设置新增待培训已读
     */
    private void readPracticeUpdate(String practiceId) {
        HttpUtils getinstance = HttpUtils.getinstance(NewNoTrainActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("practiceId",practiceId);
        getinstance.post(Constant.READPRACTICEUPDATE, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);



            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.action_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.action_item:
                break;
        }
    }
}
