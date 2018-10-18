package com.yijie.com.yijie.activity.student;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.student.studentgrounp.InternshipActivity;
import com.yijie.com.yijie.adapter.StudentStatusAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.bean.StudentResume;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/26.
 * 不同状态学生简历
 */

public class StudentStatusActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<StudentResume> dataList = new ArrayList<>();
    StudentStatusAdapter studentStatusAdapter;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_studentstauts);
    }

    @Override
    protected void onResume() {
        dataList.clear();
        selectList();
        super.onResume();
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        selectList();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        selectList();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));  // 设置刷新控件颜色
        setTranslucent(this); // 改变状态栏变成透明
        actionItem.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        actionItem.setBackgroundResource(R.mipmap.search);
        studentStatusAdapter = new StudentStatusAdapter(dataList, R.layout.adapter_studentstatus_item);
        recyclerView.setAdapter(studentStatusAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(StudentStatusActivity.this, LinearLayoutManager.VERTICAL));
        studentStatusAdapter.setOnItemClickListener(new StudentStatusAdapter.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(View view, int position) {
                                                            //TODO 点击根据状态跳转到不同页面
                                                            Intent intent = new Intent();
                                                            int status = dataList.get(position).getStatus();
                                                            if (status == 1) {
                                                                //待审核
                                                                intent.setClass(StudentStatusActivity.this, SamplecCheckPendingActivity.class);
                                                                startActivity(intent);
                                                                //待分配
                                                            } else if (status == 2) {
                                                                intent.setClass(StudentStatusActivity.this, TBStributedActivity.class);
                                                                startActivity(intent);
                                                                //未通过
                                                            } else if (status == 4) {
                                                                intent.setClass(StudentStatusActivity.this, NotPassActivity.class);
                                                                startActivity(intent);
                                                            } else if (status == 3) {
                                                                intent.putExtra("page",3);
                                                                intent.setClass(StudentStatusActivity.this, HasPassedActivity.class);
                                                                startActivity(intent);
                                                            }
//


                                                        }
                                                    }
        );
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                selectList();

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
     * 查询不同状态下学生列表个数
     */
    private void selectList() {
        final HttpUtils instance = HttpUtils.getinstance(StudentStatusActivity.this);
        Map map = new HashMap();
        instance.post(Constant.SELECTSTUHOMEPAGELIST, map, new BaseCallback<String>() {

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
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                try {
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(s);
                    String resode = jsonObject.getString("rescode");
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (resode.equals("200")) {
                        for (int i = 0; i < data.length(); i++) {
                            StudentResume studentResume = gson.fromJson(data.getJSONObject(i).toString(), StudentResume.class);
                            dataList.add(studentResume);
                        }

                        studentStatusAdapter.notifyDataSetChanged();
                        if (data.length() == 0) {
                            statusLayoutManager.showEmptyLayout();
                        } else {
                            statusLayoutManager.showSuccessLayout();
                        }
                        commonDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
