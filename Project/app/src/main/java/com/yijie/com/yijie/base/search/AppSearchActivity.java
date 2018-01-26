package com.yijie.com.yijie.base.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ConversionActivity;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.student.StudentActivity;

import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;

import com.yijie.com.yijie.fragment.student.LoadStudentSearchAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by 奕杰平台 on 2018/1/19.
 */

public class AppSearchActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.clearEditText)
    EditText clearEditText;
    @BindView(R.id.iv_delect)
    ImageView ivDelect;
    @BindView(R.id.action_item)
    TextView actionItem;
    @BindView(R.id.app_title)
    RelativeLayout appTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    ArrayList<StudentBean> hintData = new ArrayList<StudentBean>();
    LoadStudentSearchAdapter loadMoreWrapperAdapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        loadMoreWrapperAdapter = new LoadStudentSearchAdapter(hintData, R.layout.student_adapter_item);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);

        loadMoreWrapperAdapter.setOnItemClickListener(new LoadStudentSearchAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              int type = hintData.get(position).getType();
                                                              Intent intent = new Intent();
                                                              intent.putExtra("type", type);
                                                              if (type == 5) {
                                                                  intent.setClass(AppSearchActivity.this, ConversionActivity.class);

                                                              } else {
                                                                  intent.setClass(AppSearchActivity.this, StudentActivity.class);
                                                              }
                                                              startActivity(intent);

                                                          }
                                                      }
        );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//         设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (hintData.size() < 20) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                           runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  getSearchData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 2000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    @OnClick({R.id.action_item  ,R.id.iv_delect})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action_item:
                    finish();
                break;

            case R.id.iv_delect:
                clearEditText.setText("");
                break;

        }
    }
    //搜索
    @OnTextChanged(value = R.id.clearEditText, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        //真实数据取消
        hintData.clear();
        if (s.length() > 0) {


            ivDelect.setVisibility(View.VISIBLE);

        } else {
            ivDelect.setVisibility(View.GONE);

        }
        getSearchData();
        loadMoreWrapperAdapter.notifyDataSetChanged();

    }

    private void getSearchData() {

        for (int i = 1; i <= 10; i++) {
            //type=1待审核 //type=2待分配  //type=3 已分配  //type=4 未通过  //type=5 实习生//type=6毕业生
            if (i==1){
                hintData.add(new StudentBean(1,"王颖"));
            }else if (i==2){
                hintData.add(new StudentBean(2,"王颖"));
            }else if (i==3){
                hintData.add(new StudentBean(3,"王颖"));
            }else if (i==4){
                hintData.add(new StudentBean(4,"王颖"));
            }else{
                hintData.add(new StudentBean(5,"王颖"));
            }

        }


    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
