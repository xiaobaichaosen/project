package com.yijie.com.kindergartenapp.fragment.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.baseadapter.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class SchoolFragment extends BaseFragment {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    Unbinder unbinder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.linearLayout_photo)
    LinearLayout linearLayoutPhoto;

    private List<StudentBean> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_school;
    }

    @Override
    protected void initData() {
        title.setText("学校名称");
        back.setVisibility(View.GONE);


        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        loading.setRetryListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loading.showContent();
//            }
//        });
//        loading.showContent();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        // 模拟获取数据
        getData();
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);

        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);

        //设置适配器
        LoadMoreSchoolAdapter loadMoreWrapperAdapter = new LoadMoreSchoolAdapter(dataList, R.layout.school_adapter_item_content);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);


        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);

        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreSchoolAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {

                                                              Intent intent = new Intent();
//                                                              intent.setClass(mActivity, PracticeDetailsActivity.class);
//                                                              startActivity(intent);

//
                                                          }
                                                      }
        );

//加载更多监听
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

    @OnClick({R.id.back})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.back:
//                ShowToastUtils.showToastMsg(this, "收藏职位");
                break;

        }
    }

    @OnCheckedChanged({R.id.checkBox})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.checkBox:
                if (ischanged) {
                    linearLayoutPhoto.setVisibility(View.VISIBLE);
                    checkBox.setText("收起");
                } else {
                    checkBox.setText("更多");
                    linearLayoutPhoto.setVisibility(View.GONE);
                }
                break;

        }
    }

    private void getData() {
        //String.valueOf(letter)
        char letter = 'A';
        for (int i = 0; i < 3; i++) {

            dataList.add(new StudentBean(1, String.valueOf(letter)));

            letter++;
        }
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
}