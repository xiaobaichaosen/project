package com.yijie.com.yijie.fragment.kndergaren;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 * 正在招聘的园所
 */


public class LoadMoreCompanyKndergrtenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private final int res;
    private List<StudentBean> dataList;
    private Context mContext;


    public LoadMoreCompanyKndergrtenAdapter(List<StudentBean> dataList , Context context, int res) {
        this.dataList = dataList;
        this.res=res;


    }

    private LoadMoreCompanyKndergrtenAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(LoadMoreCompanyKndergrtenAdapter.OnItemClickListener listener) {
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
        return dataList.get(position).getType();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        return new LoadMoreCompanyKndergrtenAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LoadMoreCompanyKndergrtenAdapter.RecyclerViewHolder recyclerViewHolder = (LoadMoreCompanyKndergrtenAdapter.RecyclerViewHolder) holder;
//        recyclerViewHolder.kndergarten_status.setText(dataList.get(position).getName());
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


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
        Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
        mContext.startActivity(intent);
    }
}