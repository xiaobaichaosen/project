package com.yijie.com.yijie.fragment.student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadStudentSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<StudentBean> dataList;
    private Context mContext;

    public LoadStudentSearchAdapter(List<StudentBean> dataList, int res) {
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
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//        recyclerViewHolder.tvItem.setText(dataList.get(position).getName());
        int type = dataList.get(position).getType();
        if (type == 1) {
            recyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.stuent_unabsorbed);
        } else if (type == 3) {
            recyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_allowed);
        } else if (type == 4) {
            recyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_noallowed);
        } else   if (type == 2) {
            recyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_group_allocation);
        } else if (type == 5) {
            recyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_exercitation);
        } else if (type == 6) {
            recyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_graduate);
        }
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
        return dataList.get(position).getType();
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_stutus)
        ImageView ivStutus;
        RecyclerViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}