package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.util.BadgeDragable;
import com.yijie.com.yijie.util.DismissDelegate;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.view.BadgeView;
import com.yijie.com.yijie.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreNoTrainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<SchoolPractice> dataList;
    private Context mContext;

    public LoadMoreNoTrainAdapter(List<SchoolPractice> dataList, int res) {
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
        String logoPath = dataList.get(position).getLogo();
        Integer isRead= dataList.get(position).getIsRead();
        if (null==isRead){
            recyclerViewHolder.llRoot.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            recyclerViewHolder.llRoot.setBackgroundColor(mContext.getResources().getColor(R.color.app_background));
        }
        if ( null==logoPath) {
            recyclerViewHolder.ivLogo.setBackgroundResource(R.mipmap.logo);
        } else {
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(logoPath, recyclerViewHolder.ivLogo, ImageLoaderUtil.getPhotoImageOption());
        }
        recyclerViewHolder.tvProjectName.setText(dataList.get(position).getProjectName());
        recyclerViewHolder.tvSchoolName.setText(dataList.get(position).getName());
        recyclerViewHolder.tvTrainDate.setText(dataList.get(position).getTrainTime());
        recyclerViewHolder.tvEdit.setText(dataList.get(position).getMemoContent());
//        recyclerViewHolder.tvItem.setText(dataList.get(position).getName());
        //强制关闭item复用
//        holder.setIsRecyclable(false);


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
        return 1 ;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        CircleImageView ivLogo;
        @BindView(R.id.tv_projectName)
        TextView tvProjectName;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime;
        @BindView(R.id.ll_center)
        RelativeLayout llCenter;
        @BindView(R.id.tv_schoolName)
        TextView tvSchoolName;
        @BindView(R.id.tv_check)
        TextView tvCheck;
        @BindView(R.id.tv_trainDate)
        TextView tvTrainDate;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);



        }
    }

}