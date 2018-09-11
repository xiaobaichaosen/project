package com.yijie.com.kindergartenapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.bean.StudentuserKinderneed;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/6/25.
 * 简历列表
 */

public class LoadMoreResumnListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    @BindView(R.id.iv_head)
    ImageView ivHead;

    private List<StudentuserKinderneed> dataList;
    private Context mContext;


    public LoadMoreResumnListAdapter(List<StudentuserKinderneed> dataList) {
        this.dataList = dataList;

    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
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
                .inflate(R.layout.adapter_resumnlist_item, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvName.setText(dataList.get(position).getStuName());
        Integer status = dataList.get(position).getStatus();
        if (status==0){
            recyclerViewHolder.tvStatus.setText("待选");
        }else if (status==2){
            recyclerViewHolder.tvStatus.setText("已选");
        }else if (status==3){
            recyclerViewHolder.tvStatus.setText("放弃");
        }
        String ageHeiWei=dataList.get(position).getStuAge()+"岁"+ " "+dataList.get(position).getHeight()+"cm"+" "+dataList.get(position).getWeight()+"kg";
        recyclerViewHolder.tvYearheightwight.setText(ageHeiWei);
        recyclerViewHolder.tvNb.setText(dataList.get(position).getHobby());
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_yearheightwight)
        TextView tvYearheightwight;
        @BindView(R.id.tv_nb)
        TextView tvNb;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }


}
