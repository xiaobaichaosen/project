package com.yijie.com.studentapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.bean.StudentEvaluate;
import com.yijie.com.studentapp.view.ninegrid.NineGridAdapter;
import com.yijie.com.studentapp.view.ninegrid.NineGridTestModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by 奕杰平台 on 2017/12/11.
 *  我对园所
 */


public class LoadMoreMe2KendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private final int res;

    private List<List<StudentEvaluate>> dataList;
    private Context mContext;
    private NineGridAdapter mAdapter;


    public LoadMoreMe2KendAdapter(List<List<StudentEvaluate>> dataList, Context context, int res) {
        this.dataList = dataList;
        this.res = res;


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
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        List<StudentEvaluate> studentBeans = dataList.get(position);
        if (studentBeans != null) {
            return 1;
        }
        return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, null, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;;
        //recycle 的item是一个rccycle 设置适配器
        final List<StudentEvaluate> studentBeans = dataList.get(position);
        recyclerViewHolder.recyclerLoadingItem.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewHolder.recyclerLoadingItem.setLayoutManager(linearLayoutManager);
        mAdapter = new NineGridAdapter(mContext);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        mAdapter.setList(studentBeans);
        recyclerViewHolder.recyclerLoadingItem.setAdapter(mAdapter);



        //获取集合中第一条数据设置评星
        if (studentBeans.size()>0){
            StudentEvaluate studentEvaluate = studentBeans.get(0);
            recyclerViewHolder.rbScale.setRating(Float.parseFloat(studentEvaluate.getScale()));
            recyclerViewHolder.rbPosition.setRating(Float.parseFloat(studentEvaluate.getPosition()));
            recyclerViewHolder.rbHardware.setRating(Float.parseFloat(studentEvaluate.getHardware()));
            recyclerViewHolder.rbTraffic.setRating(Float.parseFloat(studentEvaluate.getTraffic()));
            recyclerViewHolder.rbCrowdingDegree.setRating(Float.parseFloat(studentEvaluate.getCrowdingDegree()));
            recyclerViewHolder.rbHygieneStatus.setRating(Float.parseFloat(studentEvaluate.getHygieneStatus()));
            recyclerViewHolder.rbStartEndWork.setRating(Float.parseFloat(studentEvaluate.getStartEndWork()));
            recyclerViewHolder.tvKinderName.setText(studentEvaluate.getKindName());

        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_recyclerView)
        RecyclerView recyclerLoadingItem;
        @BindView(R.id.tv_kinderName)
        TextView tvKinderName;
        @BindView(R.id.rb_scale)
        MaterialRatingBar rbScale;
        @BindView(R.id.rb_position)
        MaterialRatingBar rbPosition;
        @BindView(R.id.rb_hardware)
        MaterialRatingBar rbHardware;
        @BindView(R.id.rb_traffic)
        MaterialRatingBar rbTraffic;
        @BindView(R.id.rb_crowdingDegree)
        MaterialRatingBar rbCrowdingDegree;
        @BindView(R.id.rb_hygieneStatus)
        MaterialRatingBar rbHygieneStatus;
        @BindView(R.id.rb_startEndWork)
        MaterialRatingBar rbStartEndWork;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }



}