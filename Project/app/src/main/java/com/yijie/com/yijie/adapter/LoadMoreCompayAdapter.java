package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;
import com.yijie.com.yijie.bean.bean.StudentuserKinderneed;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreCompayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<KindergartenDetail> dataList;
    private Context mContext;

    public LoadMoreCompayAdapter(List<KindergartenDetail> dataList, int res) {
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
            recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.head);
        } else {
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.kinderUrl+studentuserKinderneed.getId() + "/head_pic_/" +studentuserKinderneed.getHeadPic(), recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
        }
        String kindAddress = studentuserKinderneed.getKindAddress();
        if (kindAddress.length()>6){
            recyclerViewHolder.kenderAdress2name.setText("【"+studentuserKinderneed.getKindAddress().substring(6,studentuserKinderneed.getKindAddress().length())+"】"+studentuserKinderneed.getKindName());
        }else {
            recyclerViewHolder.kenderAdress2name.setText("【"+studentuserKinderneed.getKindAddress()+"】"+studentuserKinderneed.getKindName());
        }
//        recyclerViewHolder.tvNum.setText(studentuserKinderneed.);
        recyclerViewHolder.tvNumber.setText(studentuserKinderneed.getChildrenNum()+studentuserKinderneed.getTeacherNum()+"人");
        recyclerViewHolder.tvType.setText(studentuserKinderneed.getKindType());
        recyclerViewHolder.tvMeals.setText(studentuserKinderneed.getEat());
        recyclerViewHolder.tvNum.setText("在职人数:"+studentuserKinderneed.getIncumbencyNum());
        if (TextUtils.isEmpty(studentuserKinderneed.getWholeEvaluate())){
            recyclerViewHolder.mrbStar.setRating(0);
        }else {
            recyclerViewHolder.mrbStar.setRating(Float.parseFloat(studentuserKinderneed.getWholeEvaluate()));
        }




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
        @BindView(R.id.kender_adress2name)
        TextView kenderAdress2name;
        @BindView(R.id.mrb_star)
        MaterialRatingBar mrbStar;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_meals)
        TextView tvMeals;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);



        }
    }

}