package com.yijie.com.kindergartenapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.bean.StudentuserKinderneed;
import com.yijie.com.kindergartenapp.utils.ImageLoaderUtil;
import com.yijie.com.kindergartenapp.view.CircleImageView;

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
        String headPic = dataList.get(position).getHeadPic();
        if (null==headPic){
            recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.head);
        }else {
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.infoUrl+dataList.get(position).getStudentUserId()+"/head_pic_/"+headPic, recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
        }
        recyclerViewHolder.tvName.setText(dataList.get(position).getStuName());
        recyclerViewHolder.tvCreatetime.setText(dataList.get(position).getCreateTime());
        Integer status = dataList.get(position).getStatus();
        if (status==0){
            recyclerViewHolder.tvStatus.setVisibility(View.GONE);
//            recyclerViewHolder.tvStatus.setText("待选");
            recyclerViewHolder.tvYearheightwight.setText("等待园所筛选");
            recyclerViewHolder.tvNb.setText("剩余时间");
        }else if (status==2){
            recyclerViewHolder.tvStatus.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvStatus.setText("已选");
            recyclerViewHolder.tvYearheightwight.setText("已筛选");
            recyclerViewHolder.tvNb.setText("我们会尽快安排学生到园所报到,请耐心等待.");
        }else if (status==3){
            recyclerViewHolder.tvStatus.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvYearheightwight.setText("简历已退回");
            recyclerViewHolder.tvStatus.setText("放弃");
        }else if (status==3){
            recyclerViewHolder.tvStatus.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvYearheightwight.setText("简历已退回");
            recyclerViewHolder.tvStatus.setText("放弃");
        }else if (status==4){
            recyclerViewHolder.tvStatus.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvYearheightwight.setText("已关闭");
            recyclerViewHolder.tvStatus.setText("由于您长期未审核简历,该简历已被退回简历库");
        }


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
        @BindView(R.id.tv_createtime)
        TextView tvCreatetime;
        @BindView(R.id.iv_head)
        CircleImageView ivHead;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }


}
