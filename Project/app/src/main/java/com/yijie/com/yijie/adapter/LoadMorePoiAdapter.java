package com.yijie.com.yijie.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.SchoolAdress;
import com.yijie.com.yijie.fragment.school.LoadMoreSchoolAdapter;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.List;

/**
 * Created by 奕杰平台 on 2018/4/19.
 */

public class LoadMorePoiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<SchoolAdress> dataList;
    private Context mContext;

    public LoadMorePoiAdapter(List<SchoolAdress> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
    }
    private LoadMorePoiAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(LoadMorePoiAdapter.OnItemClickListener listener) {
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
        return new LoadMorePoiAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LoadMorePoiAdapter.RecyclerViewHolder recyclerViewHolder = (LoadMorePoiAdapter.RecyclerViewHolder) holder;
        recyclerViewHolder.tvItem.setText(dataList.get(position).getName());
        recyclerViewHolder.tvDetail.setText(dataList.get(position).getDetailAdress());
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

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;
        TextView tvDetail;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_name);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);

        }
    }

}
