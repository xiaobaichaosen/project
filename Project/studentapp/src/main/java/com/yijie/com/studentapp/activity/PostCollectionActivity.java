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

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.kndergard.KndergardLoadingAcitivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.studentapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.studentapp.bean.KindergartenNeed;
import com.yijie.com.studentapp.bean.StudentBrowseFootmark;
import com.yijie.com.studentapp.fragment.yijie.LoadMoreSchoolAdapter;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/28.
 * 职位收藏
 */

public class PostCollectionActivity extends BaseActivity {
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
    private   LoadMoreSchoolAdapter loadMoreWrapperAdapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_practicedetails);

    }

    @Override
    public void init() {
        title.setText("职位收藏");
        // 设置刷新控件颜色
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#f66168"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("清空");
        // 模拟获取数据
        getData();



        loadMoreWrapperAdapter = new LoadMoreSchoolAdapter(dataList, R.layout.postcollection_adapter_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);

        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreSchoolAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.setClass(PostCollectionActivity.this, KndergardLoadingAcitivity.class);
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
            HashMap<Integer, Boolean> map = loadMoreWrapperAdapter.map;
            LogUtil.e(map.toString());
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 30; i++) {

//            dataList.add(new StudentBean(i, String.valueOf(letter)));

            letter++;
        }
    }

    /**
     *  获取收藏园所的列表
     */
    public void collectKender(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(this);
        Map map = new HashMap();
        map.put("id", kenderId);

        instance.post(Constant.KINDERGARTENDETAILBYID, map, new BaseCallback<String>() {

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
