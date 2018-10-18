package com.yijie.com.studentapp.fragment.kndergaren;

import android.Manifest;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.bean.StudentuserKinderneed;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;
import com.yijie.com.studentapp.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */


public class LoadMoreKndergrtenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private final int res;
    private List<StudentuserKinderneed> dataList;
    private Context mContext;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    public LoadMoreKndergrtenAdapter(List<StudentuserKinderneed> dataList , Context context, int res) {
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
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvKindName.setText(dataList.get(position).getKindName());
        recyclerViewHolder.rbStar.setRating(Float.parseFloat(dataList.get(position).getTotalEvaluate()));
        String updateTime = dataList.get(position).getUpdateTime();
        if (null!=updateTime){
            recyclerViewHolder.tvData.setText(TimeUtils.DateformatTime(TimeUtils.strToDateLong(updateTime)));
        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_kindName)
        TextView tvKindName;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.rb_star)
        MaterialRatingBar rbStar;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }

    }



}