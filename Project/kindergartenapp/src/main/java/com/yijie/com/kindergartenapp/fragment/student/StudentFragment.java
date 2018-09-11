package com.yijie.com.kindergartenapp.fragment.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.SearchAcivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.fragment.adapter.ChildEntity;
import com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity;
import com.yijie.com.kindergartenapp.fragment.student.stickadapter.BaseViewHolder;
import com.yijie.com.kindergartenapp.fragment.student.stickadapter.GroupedListAdapter;
import com.yijie.com.kindergartenapp.fragment.student.stickadapter.GroupedRecyclerViewAdapter;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.UIUtils;
import com.yijie.com.kindergartenapp.view.StickyHeaderLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/1/29.
 * 学生
 */

public class StudentFragment extends BaseFragment {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sticky_layout)
    StickyHeaderLayout stickyLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_floating_recycler_view)
    RelativeLayout activityFloatingRecyclerView;
    @BindView(R.id.loading)
    LinearLayout loading;
    Unbinder unbinder;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    private LinearLayoutManager linearLayoutManager;
    private GroupedListAdapter mAdapter;
     ArrayList<GroupEntity> groups;
    ArrayList<ChildEntity> allChildren;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_student;
    }

    @Override
    protected void initView() {
        title.setText("学生");
        actionItem.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        actionItem.setBackgroundResource(R.mipmap.search);
        linearLayoutManager = new LinearLayoutManager(mActivity);

        groups = getGroups(2, 5);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mAdapter = new GroupedListAdapter(mActivity, groups);
        fab.hide(false);
        //;或布局中：fab:fab_type="mini"
        fab.setType(FloatingActionButton.TYPE_MINI);
        fab.attachToRecyclerView(rvList, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                fab.hide();
            }

            @Override
            public void onScrollUp() {
                fab.show();
            }
        }, new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = rvList.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition > 1) {
                        fab.show();
                    } else {
                        fab.hide();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        // 设置下拉刷新
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 刷新数据
//                groups.clear();
//                groups = getGroups(2, 5);
//
//                // 延时1s关闭下拉刷新
//                swipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }, 1000);
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.MoveToPosition(linearLayoutManager, 0);
//                UIUtils.MoveToPosition(new LinearLayoutManager(mContext), recyclerview, 0);
                fab.hide();
            }
        });
    }

    @Override
    protected void initData() {

        mAdapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                GroupEntity groupEntity = groups.get(groupPosition);
                ArrayList<ChildEntity> children1 = groupEntity.getChildren();
                ArrayList<ChildEntity> children = new ArrayList<>();

                allChildren = new ArrayList<>();
                allChildren.addAll(children1);
                for (int j = 0; j < 5; j++) {
                    children.add(new ChildEntity("第" + (j + 1) + "项"));
                }
                allChildren.addAll(children);
                groupEntity.setChildren(allChildren);
                adapter.notifyDataSetChanged();
                ShowToastUtils.showToastMsg(mActivity,"加载更多成功");

            }
        });

        mAdapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
                Intent intent = new Intent();
//                if (groupPosition == 0) {
//                    intent.setClass(InternshipStatusActivity.this, InternshipDatailActivity.class);
//
//                } else {
//                    intent.setClass(InternshipStatusActivity.this, FinishInternshipActivity.class);
//                }
//                startActivity(intent);

            }
        });
        rvList.setAdapter(mAdapter);

        //设置是否吸顶。
        stickyLayout.setSticky(true);

    }

    @Override
    protected void onInvisible() {

    }


    public ArrayList<GroupEntity> getGroups(int groupCount, int childrenCount) {
        ArrayList<GroupEntity> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            ArrayList<ChildEntity> children = new ArrayList<>();
            for (int j = 0; j < childrenCount; j++) {
                children.add(new ChildEntity("第" + (i + 1) + "组第" + (j + 1) + "项"));
            }
            if (i == 0) {
                groups.add(new GroupEntity("在岗学生"
                        , children));
            } else {
                groups.add(new GroupEntity("历史学习"
                        , children));
            }

        }
        return groups;
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

    @OnClick({R.id.back, R.id.tv_recommend,R.id.action_item})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
           case  R.id.action_item:
            intent.setClass(mActivity, SearchAcivity.class);
            startActivity(intent);
            break;
            case R.id.tv_recommend:
                break;
        }
    }
}
