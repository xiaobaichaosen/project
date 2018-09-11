package com.yijie.com.yijie.activity.student.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.StudentResume;
import com.yijie.com.yijie.utils.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/4/27.
 * 待审核列表
 */

public class LoadMoreHasPassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private List<StudentResume> dataList;
    private Context mContext;


    public LoadMoreHasPassAdapter(List<StudentResume> dataList) {
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
                .inflate(R.layout.adapter_haspass_item, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        String headPic = dataList.get(position).getHeadPic();
        if (null==headPic){
            recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.head);
        }else {
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(headPic, recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
        }
        recyclerViewHolder.tvName.setText(dataList.get(position).getStudentName());
        recyclerViewHolder.tvKindName.setText(dataList.get(position).getKindName());
        recyclerViewHolder.tvSchoolName.setText(dataList.get(position).getName());
        recyclerViewHolder.ivCellphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(dataList.get(position).getCellphone());
            }
        });
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    @SuppressLint("MissingPermission")
    private void call(String phone) {
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
        mContext.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.iv_cellphone)
        ImageView ivCellphone;
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_schoolName)
        TextView tvSchoolName;
        @BindView(R.id.tv_kindName)
        TextView tvKindName;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }
}