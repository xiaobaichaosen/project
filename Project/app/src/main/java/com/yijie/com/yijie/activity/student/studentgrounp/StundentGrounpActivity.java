package com.yijie.com.yijie.activity.student.studentgrounp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.Item;
import com.yijie.com.yijie.activity.student.StudentActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.fragment.student.LoadMoreStudentGrounpWrapperAdapter;
import com.yijie.com.yijie.fragment.student.LoadMoreStudentWrapperAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by 奕杰平台 on 2017/12/20.
 */

public class StundentGrounpActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    LoadMoreWrapper loadMoreWrapper;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.clearEditText)
    EditText clearEditText;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.app_title)
    RelativeLayout appTitle;
    @BindView(R.id.iv_delect)
    ImageView ivDelect;
    int type;
    private List<Item> dataList = new ArrayList<>();


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_stundent_grounp);

    }

    @OnClick({R.id.back,R.id.iv_delect})
    public void Click(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.iv_delect:
                clearEditText.setText("");
                break;
        }


    }



    @OnTextChanged(value = R.id.clearEditText, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnTextChanged(value = R.id.clearEditText, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length()>0){
            ivDelect.setVisibility(View.VISIBLE);
        }else{
            ivDelect.setVisibility(View.GONE);
        }
    }


    @OnTextChanged(value = R.id.clearEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {

    }

    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(StundentGrounpActivity.this));

        type = getIntent().getIntExtra("type", 0);
        // 模拟获取数据
        getData();
        LoadMoreStudentGrounpWrapperAdapter loadMoreWrapperAdapter = new LoadMoreStudentGrounpWrapperAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);

        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreStudentGrounpWrapperAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();   int type = dataList.get(position).getType();

                                                              intent.setClass(StundentGrounpActivity.this, StudentActivity.class);
                                                              intent.putExtra("type",type);
                                                              startActivity(intent);
                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(StundentGrounpActivity.this, LinearLayoutManager.VERTICAL));
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

                if (dataList.size() < 26) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            StundentGrounpActivity.this.runOnUiThread(new Runnable() {
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
        char letter = 'A';
//        type 2,5,6
        for (int i = 0; i < 26; i++) {
            if (type==2){

                    dataList.add(new Item(2, "王颖"));

            }else if(type==5){

                    dataList.add(new Item(5, "王颖"));

            }else if (type==6){
                dataList.add(new Item(6, "王颖"));
            }
//            if (i < 8) {
//            } else {
//                dataList.add(new Item(2, String.valueOf(letter)));
//            }

            letter++;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
