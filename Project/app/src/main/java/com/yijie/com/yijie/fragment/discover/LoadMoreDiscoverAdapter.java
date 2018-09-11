package com.yijie.com.yijie.fragment.discover;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.school.SelfDiscovery;
import com.yijie.com.yijie.util.BadgeDragable;
import com.yijie.com.yijie.util.DismissDelegate;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.BadgeView;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreDiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<SelfDiscovery> dataList;
    private Context mContext;

    public LoadMoreDiscoverAdapter(List<SelfDiscovery> dataList, int res) {
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
//        recyclerViewHolder.tvItem.setText(dataList.get(position).getName());
        holder.setIsRecyclable(false);
        if (dataList.get(position).getUnreadCount()==0){
            recyclerViewHolder.badgeView.setVisibility(View.GONE);
        }else{
            recyclerViewHolder.badgeView.showBadge(dataList.get(position).getUnreadCount()+"");
        }
            recyclerViewHolder.badgeView.getBadgeFactory().setBadgePaddingDp(5);
            recyclerViewHolder.badgeView.getBadgeFactory().setBadgeTextSizeSp(12);
            recyclerViewHolder. badgeView.getBadgeFactory().setBadgeHorizontalMarginDp(5);
            recyclerViewHolder. badgeView.getBadgeFactory().setBadgeBorderWidthDp(2);
            //关闭拖拽删除
            recyclerViewHolder.badgeView.getBadgeFactory().setDragable(false);
//            recyclerViewHolder. badgeView.setDragDismissDelegage(new DismissDelegate() {
//                @Override
//                public void onDismiss(BadgeDragable badgeable) {
//                    dataList.get(position).setName(0+"");
//                    //状态传回后台
//                }
//            });
        recyclerViewHolder.tv_title.setText(dataList.get(position).getDiscoveryTitle());
        recyclerViewHolder.tv_content.setText(dataList.get(position).getDiscoveryContent());
        recyclerViewHolder.tv_time.setText(dataList.get(position).getCreateTime().toString());

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
        @BindView(R.id.tv_content)
        TextView tv_content;
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