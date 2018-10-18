package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.StudentEducation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/6/26.
 * 教育背景列表
 */

public class LoadMoreEducationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private List<StudentEducation> dataList;
    private Context mContext;


    public LoadMoreEducationAdapter(List<StudentEducation> dataList) {
        this.dataList = dataList;

    }

    private LoadMoreEducationAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(LoadMoreEducationAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }

    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 获得当前view的type类型
     *
     * @param position 必须重写
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_education_iten, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.tvCollege.setText(dataList.get(position).getCollege());
        recyclerViewHolder.tvMajor.setText(dataList.get(position).getMajor());
        recyclerViewHolder.tvEducation.setText(dataList.get(position).getEducation());
        recyclerViewHolder.tvStartTime.setText(dataList.get(position).getStartTime());
        recyclerViewHolder.tvGradTime.setText(dataList.get(position).getGraduateTime());
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_college)
        TextView tvCollege;
        @BindView(R.id.tv_major)
        TextView tvMajor;
        @BindView(R.id.tv_education)
        TextView tvEducation;
        @BindView(R.id.tv_startTime)
        TextView tvStartTime;
        @BindView(R.id.tv_gradTime)
        TextView tvGradTime;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }


}

