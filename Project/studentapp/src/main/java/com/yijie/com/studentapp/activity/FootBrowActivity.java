package com.yijie.com.studentapp.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.kndergard.KndergardLoadingAcitivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.bean.KindergartenNeed;
import com.yijie.com.studentapp.bean.StudentBrowseFootmark;
import com.yijie.com.studentapp.fragment.yijie.LoadMoreSchoolAdapter;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/28.
 * 浏览足迹
 */

public class FootBrowActivity extends BaseActivity {
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
    @BindView(R.id.cb_selectAll)
    CheckBox cbSelectAll;
    @BindView(R.id.loading)
    LinearLayout loading;
    private List<StudentBrowseFootmark> dataList = new ArrayList<>();
    private List<StudentBrowseFootmark> removeList = new ArrayList<>();
    private   LoadMoreSchoolAdapter loadMoreWrapperAdapter;
    private StringBuilder allId=new StringBuilder();
    private    String userId;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_practicedetails);

    }

    @Override
    public void init() {
        title.setText("浏览足迹");

        userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
        // 设置刷新控件颜色
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("清空");
        // 模拟获取数据
        getFootList(userId);





        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                getFootList(userId);
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




    }
    @OnCheckedChanged({R.id.cb_selectAll})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        if (ischanged) {
            loadMoreWrapperAdapter.selectAll();;

        } else {
            loadMoreWrapperAdapter.cancleAll();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @OnClick({R.id.back,  R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_recommend:
                HashMap<Integer, Boolean> map = loadMoreWrapperAdapter.map;
                allId.delete(0,allId.length());
                for (Integer key : map.keySet()) {
                    //如果勾选
                    if (map.get(key)){
                        StudentBrowseFootmark studentBrowseFootmark = dataList.get(key);
                        removeList.add(studentBrowseFootmark);
                        Integer id = studentBrowseFootmark.getKinderNeedId();
                         allId.append(id +",");
                    }
                }
                if (null!=allId){
                    String selectId= allId.subSequence(0, allId.length() - 1).toString();
                    delectFoot(selectId);
                }
                break;
        }

    }

    /**
     *  删除浏览足迹
     */
    public void delectFoot(String allId) {
        final HttpUtils instance = HttpUtils.getinstance(this);
        Map map = new HashMap();
        map.put("studentUserId", userId);
        map.put("ids", allId);
        instance.post(Constant.DELETEBROWSEFOOT, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if ("200".equals(rescode)){
                        ShowToastUtils.showToastMsg(FootBrowActivity.this,jsonObject.getString("resMessage"));
                        dataList.removeAll(removeList);
                        loadMoreWrapperAdapter.map.clear();
                        for (int i = 0; i < dataList.size(); i++) {
                            //Checkbox初始状态置为false
                            loadMoreWrapperAdapter.map.put(i, false);
                        }
                        loadMoreWrapperAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });

    }

    /**
     *  获取收藏园所的列表
     */
    public void getFootList(String userId) {
        final HttpUtils instance = HttpUtils.getinstance(this);
        Map map = new HashMap();
        map.put("studentUserId", userId);
        map.put("pageStart", "1");
        map.put("pageSize", Integer.MAX_VALUE+"");
        instance.post(Constant.SELECTBROWSEFOOTMARKLIST, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (data != null) {
                            Gson gson = new Gson();
                            for (int i = 0; i < data.getJSONArray("list").length(); i++) {
                                StudentBrowseFootmark studentBrowseFootmark = gson.fromJson(data.getJSONArray("list").getJSONObject(i).toString(), StudentBrowseFootmark.class);
                                dataList.add(studentBrowseFootmark);
                            }
                        }


                        loadMoreWrapperAdapter = new LoadMoreSchoolAdapter(dataList, R.layout.postcollection_adapter_item);
                        recyclerView.setAdapter(loadMoreWrapperAdapter);
                        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreSchoolAdapter.OnItemClickListener(

                                                                      ) {
                                                                          @Override
                                                                          public void onItemClick(View view, int position) {
                                                                              Intent intent = new Intent();
                                                                              intent.putExtra("kinderId",dataList.get(position).getKinderId()+"");
                                                                              intent.setClass(FootBrowActivity.this, KndergardLoadingAcitivity.class);
                                                                              startActivity(intent);
                                                                          }
                                                                      }
                        );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });

    }
}
