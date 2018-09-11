package com.yijie.com.yijie.fragment.kndergaren;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.KendergardMachDetailAcitivty;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.bean.KindergartenNeed;
import com.yijie.com.yijie.utils.ShowToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 * 正在招聘的园所
 */


public class LoadMoreLoadingKndergrtenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private final int res;

    private List<List<KindergartenNeed>> dataList;
    private Context mContext;


    public LoadMoreLoadingKndergrtenAdapter(List<List<KindergartenNeed>> dataList, Context context, int res) {
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
        List<KindergartenNeed> studentBeans = dataList.get(position);
        if (studentBeans != null) {
            return 1;
        }
        return 0;

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
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;;
        //recycle 的item是一个rccycle 设置适配器
        final List<KindergartenNeed> studentBeans = dataList.get(position);
        LoadMoreLoadingItemKndergrtenAdapter loadMoreLoadingItemKndergrtenAdapter = new LoadMoreLoadingItemKndergrtenAdapter(studentBeans, R.layout.fragment_adapter_loading);
        loadMoreLoadingItemKndergrtenAdapter.setOnItemClickListener(new LoadMoreLoadingItemKndergrtenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("kinderName",studentBeans.get(position).getKindName());
                intent.putExtra("kinderNeedId",studentBeans.get(position).getId());
                intent.setClass(mContext, KendergardMachDetailAcitivty.class);
                mContext.startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        recyclerViewHolder.recyclerLoadingItem.setLayoutManager(linearLayoutManager);
        recyclerViewHolder.recyclerLoadingItem.setAdapter(loadMoreLoadingItemKndergrtenAdapter);

        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_loading_item)
        RecyclerView recyclerLoadingItem;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }


    /**
     * 拨打电话
     *
     * @param phone 电话号码
     */
    @SuppressLint("MissingPermission")
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        mContext.startActivity(intent);
    }
}