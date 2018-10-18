package com.yijie.com.studentapp.fragment.yijie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.bean.KindergartenNeed;
import com.yijie.com.studentapp.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreYjAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<KindergartenNeed> dataList;
    private Context mContext;

    public LoadMoreYjAdapter(List<KindergartenNeed> dataList, int res) {
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
        KindergartenNeed kindergartenNeed = dataList.get(position);
        recyclerViewHolder.tvKindName.setText(kindergartenNeed.getKindName());
        recyclerViewHolder.tvRequstNum.setText("需求:"+kindergartenNeed.getStudentNum());
        recyclerViewHolder.tvPost.setText("投递:"+kindergartenNeed.getCountRecruit());
        recyclerViewHolder.tvSelect.setText("已选:"+kindergartenNeed.getCountReceive());
        recyclerViewHolder.tvRemain.setText("剩余:"+(kindergartenNeed.getCountSurplus()));
        String updateTime = kindergartenNeed.getUpdateTime();
        if (null!=updateTime){
            String s = updateTime.replaceAll("/", "-");
            recyclerViewHolder.tvData.setText(TimeUtils.DateformatTime(TimeUtils.strToDateLong(s)));
        }
        if (kindergartenNeed.getStatus()==0){
            //证明已经投过了
            recyclerViewHolder.tvMoney.setText("已投递");
        }else{
            recyclerViewHolder.tvMoney.setText(kindergartenNeed.getKinderSalarySet());
        }

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
        return dataList!=null ? dataList.size():0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CheckBox cbCheck;
        @BindView(R.id.tv_kindName)
        TextView tvKindName;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_requstNum)
        TextView tvRequstNum;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tv_select)
        TextView tvSelect;
        @BindView(R.id.tv_remain)
        TextView tvRemain;



        public  RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}