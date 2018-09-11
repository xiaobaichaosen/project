package com.yijie.com.kindergartenapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.KindergartenDiscovery;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/5/21.
 */

public class RecruitmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<KindergartenDiscovery> dataList;
    private Context mContext;

    public RecruitmentListAdapter(List<KindergartenDiscovery> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
    }
    private RecruitmentListAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(RecruitmentListAdapter.OnItemClickListener listener) {
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
        return new RecruitmentListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecruitmentListAdapter.RecyclerViewHolder recyclerViewHolder = (RecruitmentListAdapter.RecyclerViewHolder) holder;
        KindergartenDiscovery kindergartenDiscovery = dataList.get(position);
        recyclerViewHolder.tvProjectName.setText(kindergartenDiscovery.getProjectName());
        recyclerViewHolder.tvAddress.setText(kindergartenDiscovery.getLocation());
        recyclerViewHolder.tvNumber.setText(kindergartenDiscovery.getStudentNum()+"");
        String all="" ;
        if (null!=kindergartenDiscovery.getDance()){
            all=kindergartenDiscovery.getDance();
        }if (null!=kindergartenDiscovery.getArts()){
            all+="、"+kindergartenDiscovery.getArts();
        } if (null!=kindergartenDiscovery.getEnglish()){
            all+="、"+kindergartenDiscovery.getEnglish();
        }if (null!=kindergartenDiscovery.getSkidding()){
            all+="、"+kindergartenDiscovery.getSkidding();
        }if (null!=kindergartenDiscovery.getPiano()){
            all+="、"+kindergartenDiscovery.getPiano();
        }if (null!=kindergartenDiscovery.getElectronicOrgan()){
            all+="、"+kindergartenDiscovery.getElectronicOrgan();
        }if (null!=kindergartenDiscovery.getOther()){
            all+="、"+kindergartenDiscovery.getOther();
        }



        recyclerViewHolder.tvNb.setText(all);
        recyclerViewHolder.tvTime.setText(kindergartenDiscovery.getCreateTime());
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

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
    View itemView;
        @BindView(R.id.tv_projectName)
        TextView tvProjectName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_nb)
        TextView tvNb;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            //重要
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
