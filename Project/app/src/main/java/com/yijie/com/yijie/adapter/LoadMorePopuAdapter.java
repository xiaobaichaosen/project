package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yijie.com.yijie.R;

import java.util.List;

public class LoadMorePopuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<String> dataList;
    private Context mContext;

    public LoadMorePopuAdapter(List<String> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
    }
    private LoadMorePopuAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(LoadMorePopuAdapter.OnItemClickListener listener) {
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
        return new LoadMorePopuAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LoadMorePopuAdapter.RecyclerViewHolder recyclerViewHolder = (LoadMorePopuAdapter.RecyclerViewHolder) holder;
        recyclerViewHolder.tvItem.setText(dataList.get(position));
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

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.text1);

        }
    }

}

