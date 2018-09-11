package com.yijie.com.yijie.fragment.kndergaren;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.KndergardAcitivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.bean.KindergartenNeed;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 * 正在招聘的园所
 */


public class LoadMoreLoadingItemKndergrtenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private final int res;
    private List<KindergartenNeed> dataList;
    private Context mContext;


    public LoadMoreLoadingItemKndergrtenAdapter(List<KindergartenNeed> dataList, int res) {
        this.dataList = dataList;
        this.res=res;


    }

    private LoadMoreLoadingItemKndergrtenAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(LoadMoreLoadingItemKndergrtenAdapter.OnItemClickListener listener) {
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
        return new LoadMoreLoadingItemKndergrtenAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LoadMoreLoadingItemKndergrtenAdapter.RecyclerViewHolder recyclerViewHolder = (LoadMoreLoadingItemKndergrtenAdapter.RecyclerViewHolder) holder;
        //给内容的recycle设置数据
        if (dataList.size()>1){
            if (0==position){
                recyclerViewHolder.ivKendegard.setBackgroundResource(R.mipmap.shool_pictor);
            }else{
                recyclerViewHolder.ivKendegard.setBackgroundResource(0);
            }

        }else{
            recyclerViewHolder.ivKendegard.setBackgroundResource(R.mipmap.shool_pictor);
        }
         recyclerViewHolder.kendegardName.setText(dataList.get(position).getKindName());
         recyclerViewHolder.schoolTitle.setText(dataList.get(position).getProjectName());
         recyclerViewHolder.tvData.setText(dataList.get(position).getUpStringTime());
         recyclerViewHolder.schoolName.setText(dataList.get(position).getSchoolName());

         //需求人数
        int studentNum = dataList.get(position).getStudentNum();
        //已选人数
        int receiveNum = dataList.get(position).getCount2();
        //投递人数
        int receiveResume = dataList.get(position).getCount();
        //放弃人数，这里不对，后台待添加
        int noPass = dataList.get(position).getCount3();
        if (receiveNum==studentNum){
           recyclerViewHolder.tvPeople.setTextColor(Color.parseColor("#FF5F00"));
           recyclerViewHolder.tvPeople.setText("匹配成功");
       }else {
           //有人报名，没有处理,
           if (receiveResume>0&&receiveNum==0&&noPass==0){
               recyclerViewHolder.tvPeople.setText("投递:"+receiveResume);
               //有人报名，都是已选,
           }else  if(receiveNum>0&&receiveNum>0&&noPass==0){
               recyclerViewHolder.tvPeople.setText("投递:"+receiveResume+";已选:"+receiveNum);
               //有人报名，都是放弃
           }else  if(receiveNum>0&&receiveNum==0&&noPass>0){
               recyclerViewHolder.tvPeople.setText("投递:"+receiveResume+";放弃:"+noPass);
               //有人报名，有已选，有放弃
           }else  if(receiveNum>0&&receiveNum>0&&noPass>0){
               recyclerViewHolder.tvPeople.setText("投递:"+receiveResume+";已选:"+receiveNum+";放弃:"+noPass);
           }else {
               //提完需求没人报名
               recyclerViewHolder.tvPeople.setVisibility(View.GONE);
           }
       }
       recyclerViewHolder.studentNumber.setText("需求:"+studentNum);
        recyclerViewHolder.kendegardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("kinderName",dataList.get(position).getKindName());
                intent.setClass(mContext, KndergardAcitivity.class);
                mContext.startActivity(intent);
            }
        });
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_kendegard)
        ImageView ivKendegard;
        @BindView(R.id.kendegard_name)
        TextView kendegardName;
        @BindView(R.id.school_title)
        TextView schoolTitle;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.school_name)
        TextView schoolName;
        @BindView(R.id.student_number)
        TextView studentNumber;
        @BindView(R.id.tv_people)
        TextView tvPeople;

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