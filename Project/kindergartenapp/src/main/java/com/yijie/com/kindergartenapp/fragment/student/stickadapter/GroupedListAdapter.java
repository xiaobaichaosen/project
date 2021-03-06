package com.yijie.com.kindergartenapp.fragment.student.stickadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;


import com.yijie.com.kindergartenapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 */
public class GroupedListAdapter extends GroupedRecyclerViewAdapter {

    private ArrayList<com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity> firstList=new ArrayList<>();
    private ArrayList<com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity> moreList=new ArrayList<>();
    private List<com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity> mGroups=new ArrayList<>();

    public GroupedListAdapter(Context context, ArrayList<com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity> groups) {
        super(context);
        firstList = groups;
        mGroups.addAll(firstList);
    }



    public void addList(List<com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity> groupsList ){
        mGroups.addAll(groupsList);
  }
    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<com.yijie.com.kindergartenapp.fragment.adapter.ChildEntity> children = mGroups.get(groupPosition).getChildren();
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
        com.yijie.com.kindergartenapp.fragment.adapter.GroupEntity entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_header, entity.getHeader());
        if (entity.getHeader().equals("在岗学生")){
            holder.setText(R.id.tv_header,"在岗学生");
            holder.setBackgroundColor(R.id.tv_header, Color.parseColor("#ea7e7c"));
            holder.setBackgroundRes(R.id.tv_sudent_title,R.mipmap.student_title);
            holder.setTextColor(R.id.iv_stutus, Color.parseColor("#ea7e7c"));

        }else{
            holder.setText(R.id.tv_header,"历史学生");
            holder.setBackgroundColor(R.id.tv_header, Color.parseColor("#999999"));
            holder.setBackgroundRes(R.id.tv_sudent_title,R.mipmap.student_gray_title);
            holder.setTextColor(R.id.iv_stutus, Color.parseColor("#4d4d4d"));
        }
    }



    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        com.yijie.com.kindergartenapp.fragment.adapter.ChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
//        if (groupPosition==1){
//            holder.setVisible(R.id.rl_loading, View.GONE);
//            holder.setVisible(R.id.rl_status, View.VISIBLE);
//            holder.setText(R.id.tv_lastdate, entity.getChild());
//        }else{
//            holder.setVisible(R.id.rl_status , View.GONE);
//            holder.setVisible(R.id. rl_loading, View.VISIBLE);
//            holder.setText(R.id.tv_child, entity.getChild());
//        }

    }
}
