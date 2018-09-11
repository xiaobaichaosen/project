package com.yijie.com.yijie.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.fragment.school.LoadMoreSchoolAdapter;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/5/21.
 */

public class SchoolListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<SchoolSimple> dataList;
    private Context mContext;

    public SchoolListAdapter(List<SchoolSimple> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
    }
    private SchoolListAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(SchoolListAdapter.OnItemClickListener listener) {
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
        return new SchoolListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SchoolListAdapter.RecyclerViewHolder recyclerViewHolder = (SchoolListAdapter.RecyclerViewHolder) holder;
        String logoPath = dataList.get(position).litimg;
        int count = dataList.get(position).count;
        int count0 = dataList.get(position).count0;
        int count1 = dataList.get(position).count1;
        if (null==logoPath||logoPath.equals("")||TextUtils.isEmpty(logoPath)){
            recyclerViewHolder.ivLogo.setImageResource(R.mipmap.logo);

        } else {
            //加载网络图片

            String[] split = logoPath.split(";");
            ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.getheadUrl+dataList.get(position).id+"/head_pic_/"+split[0], recyclerViewHolder.ivLogo, ImageLoaderUtil.getPhotoImageOption());

        }
        recyclerViewHolder.schoolName.setText(dataList.get(position).name);
        recyclerViewHolder.tvEdit.setText(dataList.get(position).memo_content);
        recyclerViewHolder.tvName.setText(dataList.get(position).user_name);
        String cellphone = dataList.get(position).cellphone;
        String telephone = dataList.get(position).telephone;

        if (TextUtils.isEmpty(cellphone)){
            recyclerViewHolder.tvPhone.setText(telephone);
        }else{
            recyclerViewHolder.tvPhone.setText(cellphone);
        }
        if (null!=dataList.get(position).updateTime){
            recyclerViewHolder.tvData.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(dataList.get(position).updateTime)));
        }



        if (count==0){
            recyclerViewHolder.llCount.setVisibility(View.GONE);
        }else {
            recyclerViewHolder.llCount.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvProjectSum.setText("项目("+count+")");
            recyclerViewHolder.tvProjectSum.setBackgroundColor(mContext.getResources().getColor(R.color.app_background));
            if(count0==0){
                recyclerViewHolder.tvDevelopSum.setVisibility(View.GONE);
            }else{
                recyclerViewHolder.tvDevelopSum.setVisibility(View.VISIBLE);
                recyclerViewHolder.tvDevelopSum.setText("开发("+count0+")");
                recyclerViewHolder.tvDevelopSum.setBackgroundColor(mContext.getResources().getColor(R.color.app_background));
            }
            if(count1==0){
                recyclerViewHolder.tvNoTrain.setVisibility(View.GONE);
            }else {
                recyclerViewHolder.tvNoTrain.setVisibility(View.VISIBLE);
                recyclerViewHolder.tvNoTrain.setText("待培训("+count1+")");
                recyclerViewHolder.tvNoTrain.setBackgroundColor(mContext.getResources().getColor(R.color.app_background));
            }
        }

//        recyclerViewHolder.tvData.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(dataList.get(position).createTime)));
        //TODO
//        recyclerViewHolder.setIsRecyclable(false);
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
    View itemView;
        @BindView(R.id.school_name)
        TextView schoolName;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_projectSum)
        TextView tvProjectSum;
        @BindView(R.id.tv_developSum)
        TextView tvDevelopSum;
        @BindView(R.id.tv_noTrain)
        TextView tvNoTrain;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.ll_count)
        LinearLayout llCount;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            //重要
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            tvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new CommomDialog(mContext, R.style.dialog, "您确定拨打电话么？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm,String string) {
                            if(confirm){

                                call(tvPhone.getText().toString().trim());
                                dialog.dismiss();
                            }

                        }
                    })
                            .setTitle("提示").show();
                }
            });
        }
    }
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
        mContext.startActivity(intent);
    }
}
