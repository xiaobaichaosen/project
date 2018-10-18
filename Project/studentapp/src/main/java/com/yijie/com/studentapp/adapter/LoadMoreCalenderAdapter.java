package com.yijie.com.studentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.PhotoActivityForHor;
import com.yijie.com.studentapp.bean.StudentSignIn;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreCalenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private  String userIdString;
    private List<StudentSignIn> dataList;
    private Context mContext;

    public LoadMoreCalenderAdapter(List<StudentSignIn> dataList, int res) {
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
        userIdString = (String) SharedPreferencesUtils.getParam(mContext, "id", "");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

        recyclerViewHolder.tvSignTime.setText("签到时间:"+dataList.get(position).getSigninTime());
        ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.infoUrl + userIdString + "/studentSignIn/" + dataList.get(position).getSigninPic(), recyclerViewHolder.ivPicture, ImageLoaderUtil.getPhotoImageOption());
        LogUtil.e("=="+Constant.infoUrl + userIdString + "/studentSignIn/" + dataList.get(position).getSigninPic());
        if (position==dataList.size()-1){
            recyclerViewHolder.viewLine.setVisibility(View.GONE);
        }else {
            recyclerViewHolder.viewLine.setVisibility(View.VISIBLE);
        }
        recyclerViewHolder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhotoActivityForHor.class);
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                intent.putExtra("imgUrl",Constant.infoUrl + userIdString + "/studentSignIn/" + dataList.get(position).getSigninPic());
                intent.putExtra("startBounds", rect);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(0, 0);
            }
        });
        //将position保存在itemView的Tag中，以便点击时进行获取
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


        @BindView(R.id.view_line)
        View viewLine;

//        @BindView(R.id.imagePoint)
//        ImageView imagePoint;
        @BindView(R.id.iv_picture)
        ImageView ivPicture;
        @BindView(R.id.tv_signTime)
        TextView tvSignTime;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}