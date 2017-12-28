package com.yijie.com.yijie.activity.kendergard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.BaseViewHolder;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.GroupedListAdapter;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.GroupedRecyclerViewAdapter;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.NoFooterAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.utils.AnimationUtil;
import com.yijie.com.yijie.utils.LightStatusBarUtils;
import com.yijie.com.yijie.view.StickyHeaderLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2017/12/26.
 * 园所 在岗实习
 */

public class InternshipStatusActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.clearEditText)
    EditText clearEditText;
    @BindView(R.id.iv_delect)
    ImageView ivDelect;
    @BindView(R.id.app_title)
    RelativeLayout appTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sticky_layout)
    StickyHeaderLayout stickyLayout;
    @BindView(R.id.loading)
    LinearLayout loading;
    GroupedListAdapter mAdapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_internshipstatus);
    }


    public  ArrayList<GroupEntity> getGroups(int groupCount, int childrenCount) {
        ArrayList<GroupEntity> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            ArrayList<ChildEntity> children = new ArrayList<>();
            for (int j = 0; j < childrenCount; j++) {
                children.add(new ChildEntity("第" + (i + 1) + "组第" + (j + 1) + "项"));
            }
            if (i==0){
                groups.add(new GroupEntity("在岗学生"
                        , children));
            }else{
                groups.add(new GroupEntity("结束学习"
                        , children));
            }

        }
        return groups;
    }
    @Override
    public void init() {

        final  ArrayList<GroupEntity> groups = getGroups(2, 5);
        LightStatusBarUtils.setLightStatusBar(this,true);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new GroupedListAdapter(this, groups);

        mAdapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {


                GroupEntity groupEntity = groups.get(groupPosition);
                ArrayList<ChildEntity> children1 = groupEntity.getChildren();
                ArrayList<ChildEntity> children = new ArrayList<>();
                ArrayList<ChildEntity> allChildren = new ArrayList<>();
                allChildren.addAll(children1);
                for (int j = 0; j < 5; j++) {
                    children.add(new ChildEntity("第" + (j + 1) + "项"));
                }
                allChildren.addAll(children);
                groupEntity.setChildren(allChildren);





                adapter.notifyDataSetChanged();
                Toast.makeText(InternshipStatusActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
                Log.e("eee", adapter.toString() + "  " + holder.toString());
            }
        });

        mAdapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {

                Intent intent = new Intent();
                intent.setClass(InternshipStatusActivity.this, InternshipDatailActivity.class);
                startActivity(intent);

                Toast.makeText(InternshipStatusActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
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

}
