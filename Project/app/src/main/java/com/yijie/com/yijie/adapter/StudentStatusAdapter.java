package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.StudentResume;
import com.yijie.com.yijie.util.BadgeDragable;
import com.yijie.com.yijie.util.DismissDelegate;
import com.yijie.com.yijie.view.BadgeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class StudentStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    private final int res;
    private List<StudentResume> dataList;
    private Context mContext;

    public StudentStatusAdapter(List<StudentResume> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
    }
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }


    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 手动添加长按事件
     */
    interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
    private OnLongClickListener mOnLongClickListener = null;
    public void setOnLongClickListener(OnLongClickListener listener) {
        mOnLongClickListener = listener;
    }
    @Override public boolean onLongClick(View view) {
        if (null != mOnLongClickListener) {
            mOnLongClickListener.onLongClick(view, (int) view.getTag());
        }

        // 消耗事件，否则长按逻辑执行完成后还会进入点击事件的逻辑处理
        return true;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        StudentResume studentResume = dataList.get(position);
        recyclerViewHolder.iv_head.setBackgroundResource(R.mipmap.head);
        recyclerViewHolder.tv_title.setText(studentResume.getStuHomePageTitle());
        recyclerViewHolder.badgeView.showBadge(studentResume.getStuResumeCountNum()+"");
        recyclerViewHolder.tv_time.setText(studentResume.getUpdateTime());
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }
    /**
     * 获得当前view的type类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return 1;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView iv_head;
        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_number)
        BadgeView badgeView;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }
    }

}