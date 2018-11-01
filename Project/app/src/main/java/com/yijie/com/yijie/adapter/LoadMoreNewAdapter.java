package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<KindergartenDetail> dataList;
    private Context mContext;

    public LoadMoreNewAdapter(List<KindergartenDetail> dataList, int res) {
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
        KindergartenDetail studentuserKinderneed = dataList.get(position);
        if ( null==studentuserKinderneed.getHeadPic()) {
            recyclerViewHolder.ivHead.setImageResource(R.mipmap.head);
        } else {
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.kinderUrl+studentuserKinderneed.getId() + "/head_pic_/" +studentuserKinderneed.getHeadPic(), recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
        }
        String kindAddress = studentuserKinderneed.getKindAddress();
        if (kindAddress.length()>6){
            recyclerViewHolder.tvKindName.setText("【"+studentuserKinderneed.getKindAddress().substring(6,studentuserKinderneed.getKindAddress().length())+"】"+studentuserKinderneed.getKindName());
        }else {
            recyclerViewHolder.tvKindName.setText("【"+studentuserKinderneed.getKindAddress()+"】"+studentuserKinderneed.getKindName());
        }
        String kinderIntegrity = studentuserKinderneed.getKinderIntegrity();
        if (!TextUtils.isEmpty(kinderIntegrity)){
            String substring = kinderIntegrity.substring(0, kinderIntegrity.length() - 1);
            recyclerViewHolder.tvDetail.setText("完整度"+Math.round(Double.parseDouble(substring))+"%");
        }
        recyclerViewHolder.tvType.setText(studentuserKinderneed.getKindType());
        recyclerViewHolder.tvNumber.setText((studentuserKinderneed.getChildrenNum()+studentuserKinderneed.getTeacherNum())+"人");
        recyclerViewHolder.tvMeals.setText(studentuserKinderneed.getEat());
        if (null!=studentuserKinderneed.getUpdateTime()){
            recyclerViewHolder.tvData.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(studentuserKinderneed.getUpdateTime())));
        }
//        recyclerViewHolder.mrbStar.setRating(studentuserKinderneed);



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
        return 1 ;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_kindName)
        TextView tvKindName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_meals)
        TextView tvMeals;

        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_data)
        TextView tvData;


        RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);



        }
    }

}