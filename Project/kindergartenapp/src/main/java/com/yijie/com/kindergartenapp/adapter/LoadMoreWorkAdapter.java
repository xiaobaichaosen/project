package com.yijie.com.kindergartenapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.bean.StudentWorkDue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/6/26.
 * 教育背景列表
 */

public class LoadMoreWorkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private List<StudentWorkDue> dataList;
    private Context mContext;


    public LoadMoreWorkAdapter(List<StudentWorkDue> dataList) {
        this.dataList = dataList;

    }

    private LoadMoreResumnListAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(LoadMoreResumnListAdapter.OnItemClickListener listener) {
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
                .inflate(R.layout.adapter_work_item, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.tvCompanyName.setText(dataList.get(position).getCompanyName());
        recyclerViewHolder.tvWorkDue.setText(dataList.get(position).getWorkDue());
        recyclerViewHolder.tvPost.setText(dataList.get(position).getPost());
        recyclerViewHolder.tvDescription.setText(dataList.get(position).getDescription());
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_companyName)
        TextView tvCompanyName;
        @BindView(R.id.tv_workDue)
        TextView tvWorkDue;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }


}

