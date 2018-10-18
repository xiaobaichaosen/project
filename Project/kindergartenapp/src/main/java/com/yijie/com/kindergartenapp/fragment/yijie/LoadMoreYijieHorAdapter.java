package com.yijie.com.kindergartenapp.fragment.yijie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.RequestDetailActivity;
import com.yijie.com.kindergartenapp.activity.RequstActivity;
import com.yijie.com.kindergartenapp.activity.SignActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreYijieHorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<CommonBean> dataList;
    private Context mContext;

    public LoadMoreYijieHorAdapter(List<CommonBean> dataList, int res) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        CommonBean commonBean = dataList.get(position);
        recyclerViewHolder.content.setText(commonBean.getContent());
        recyclerViewHolder.ivHead.setBackgroundResource(commonBean.getType());


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
        @BindView(R.id.content)
        TextView content;

        @BindView(R.id.iv_head)
        ImageView ivHead;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            tvItem = (TextView) itemView.findViewById(R.id.tv_edit);
//            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);

        }
    }

}