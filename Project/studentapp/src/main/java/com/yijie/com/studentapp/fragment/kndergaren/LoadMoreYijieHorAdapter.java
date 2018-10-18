package com.yijie.com.studentapp.fragment.kndergaren;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreYijieHorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<StudentBean> dataList;
    private Context mContext;

    public LoadMoreYijieHorAdapter(List<StudentBean> dataList, int res) {
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
        StudentBean commonBean = dataList.get(position);
        recyclerViewHolder.content.setText(commonBean.getName());
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