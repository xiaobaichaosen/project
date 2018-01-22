package com.yijie.com.yijie.activity.kendergard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.BaseViewHolder;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.FinisInternshipAdapter;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.GroupedRecyclerViewAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.utils.UIUtils;
import com.yijie.com.yijie.view.StickyHeaderLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/1/22.
 * 结束实习
 */

public class FinishInternshipActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
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
    private LinearLayoutManager linearLayoutManager;
    private FinisInternshipAdapter mAdapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_finishinternship);
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
                groups.add(new GroupEntity("结束学习"
                        , children));
            }

        }
        return groups;
    }

    @Override
    public void init() {

        linearLayoutManager = new LinearLayoutManager(this);
        final ArrayList<GroupEntity> groups = getGroups(2, 25);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new FinisInternshipAdapter(this, groups);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.MoveToPosition(linearLayoutManager, 0);
//                UIUtils.MoveToPosition(new LinearLayoutManager(mContext), recyclerview, 0);

                fab.hide();

            }
        });


//        mAdapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
//            @Override
//            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
//                                      int groupPosition) {
//
//
//                GroupEntity groupEntity = groups.get(groupPosition);
//                ArrayList<ChildEntity> children1 = groupEntity.getChildren();
//                ArrayList<ChildEntity> children = new ArrayList<>();
//                ArrayList<ChildEntity> allChildren = new ArrayList<>();
//                allChildren.addAll(children1);
//                for (int j = 0; j < 5; j++) {
//                    children.add(new ChildEntity("第" + (j + 1) + "项"));
//                }
//                allChildren.addAll(children);
//                groupEntity.setChildren(allChildren);
//
//
//                adapter.notifyDataSetChanged();
//
//            }
//        });

        mAdapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {

                Intent intent = new Intent();
                intent.setClass(FinishInternshipActivity.this, InternshipDatailActivity.class);
                startActivity(intent);

            }
        });
        rvList.setAdapter(mAdapter);

        //设置是否吸顶。
        stickyLayout.setSticky(true);
    }


    @OnClick({R.id.back})
    public void click(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.back:
                finish();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
