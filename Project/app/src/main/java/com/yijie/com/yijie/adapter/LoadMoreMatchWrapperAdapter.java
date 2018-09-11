package com.yijie.com.yijie.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.bean.StudentuserKinderneed;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/21.
 */

public class LoadMoreMatchWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private List<StudentuserKinderneed> dataList;
    private Context mContext;


    public LoadMoreMatchWrapperAdapter(List<StudentuserKinderneed> dataList) {
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
                .inflate(R.layout.adapter_math_student, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//        recyclerViewHolder.kndergarten_status.setText(dataList.get(position).getName());
        //将position保存在itemView的Tag中，以便点击时进行获取
        StudentuserKinderneed studentuserKinderneed = dataList.get(position);
        String headPic = studentuserKinderneed.getHeadPic();
        if (null!=headPic){
            //加载网络图片
            ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, headPic, recyclerViewHolder.ivHead, 0, 0);
        }else{
            recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.shool_pictor);
        }
        recyclerViewHolder.tvName.setText(studentuserKinderneed.getStuName());
        recyclerViewHolder.tvInform.setText(studentuserKinderneed.getStuAge()+"岁 "+studentuserKinderneed.getHeight()+"cm "+studentuserKinderneed.getWeight()+"kg");
        recyclerViewHolder.tvNb.setText(studentuserKinderneed.getHobby());
        recyclerViewHolder.itemView.setTag(position);
        int status = studentuserKinderneed.getStatus();
        /**
         * 简历状态(0：学生自己投的状态为 待选 1 2：园所同意接收简历 3：园所放弃接收)
         */
        if (status==0){
            recyclerViewHolder.tvStatus.setText("待选");
        }else  if (status==0){
            recyclerViewHolder.tvStatus.setText("已选");
        } else{
            recyclerViewHolder.tvStatus.setText("放弃");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_inform)
        TextView tvInform;
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