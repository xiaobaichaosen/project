package com.yijie.com.yijie.activity.kendergard.kndergardadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;


import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.ChildEntity;
import com.yijie.com.yijie.activity.kendergard.GroupEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 */
public class GroupedListAdapter extends GroupedRecyclerViewAdapter {

    private ArrayList<GroupEntity> firstList=new ArrayList<>();
    private ArrayList<GroupEntity> moreList=new ArrayList<>();
    private List<GroupEntity> mGroups=new ArrayList<>();

    public GroupedListAdapter(Context context, ArrayList<GroupEntity> groups) {
        super(context);
        firstList = groups;
        mGroups.addAll(firstList);
    }


  public void addList(List<GroupEntity> groupsList ){
        mGroups.addAll(groupsList);
  }
    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ChildEntity> children = mGroups.get(groupPosition).getChildren();
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.adapter_header;
    }



    @Override
    public int getChildLayout(int viewType) {
        return R.layout.adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        GroupEntity entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_header, entity.getHeader());
        if (entity.getHeader().equals("在岗学生")){
            holder.setText(R.id.tv_header,"在岗学生");
            holder.setBackgroundColor(R.id.tv_header, Color.parseColor("#ea7e7c"));
            holder.setBackgroundRes(R.id.tv_sudent_title,R.mipmap.student_title);
            holder.setTextColor(R.id.iv_stutus, Color.parseColor("#ea7e7c"));

        }else{
            holder.setText(R.id.tv_header,"结束实习");
            holder.setBackgroundColor(R.id.tv_header, Color.parseColor("#4F4F4F"));
            holder.setBackgroundRes(R.id.tv_sudent_title,R.mipmap.student_gray_title);
            holder.setTextColor(R.id.iv_stutus, Color.parseColor("#4d4d4d"));
        }
    }



    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
        if (groupPosition==1){
            holder.setVisible(R.id.rl_loading, View.GONE);
            holder.setVisible(R.id.rl_status, View.VISIBLE);
            holder.setText(R.id.tv_lastdate, entity.getChild());
        }else{
            holder.setVisible(R.id.rl_status , View.GONE);
            holder.setVisible(R.id. rl_loading, View.VISIBLE);
            holder.setText(R.id.tv_child, entity.getChild());
        }

    }
}
