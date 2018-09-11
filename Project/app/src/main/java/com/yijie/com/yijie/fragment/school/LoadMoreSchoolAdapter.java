package com.yijie.com.yijie.fragment.school;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.bean.SchoolSimple;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.view.CircleImageView;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreSchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private final boolean flag;
    private List<SchoolSimple>  dataList;
    private Context mContext;

    public LoadMoreSchoolAdapter(boolean flag,List<SchoolSimple> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
        this.flag=flag;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvProjectName.setText(dataList.get(position).projectName);
        String roleName = (String) SharedPreferencesUtils.getParam(mContext, "roleName", "");
        String updateTime = dataList.get(position).updateTime;
        String trainTime = dataList.get(position).trainTime;
        String status = dataList.get(position).status;
        String schoolName = dataList.get(position).name;
        String memoContent = dataList.get(position).memoContent;
        String logoPath = dataList.get(position).litimg;
        if (flag){
            if (null==logoPath||logoPath.equals("")|| TextUtils.isEmpty(logoPath)){
                recyclerViewHolder.ivLogo.setImageResource(R.mipmap.logo);
            } else {
                //加载网络图片
                String[] split = logoPath.split(";");
                ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.getheadUrl+dataList.get(position).schoolId+"/head_pic_/"+split[0], recyclerViewHolder.ivLogo, ImageLoaderUtil.getPhotoImageOption());

            }
        }else {
            recyclerViewHolder.ivLogo.setVisibility(View.GONE);
        }

        //更新时间
        recyclerViewHolder.tvTrainingTime.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(updateTime)));
        if (roleName.contains("开发老师")){
            //培训时间
            if (null!=trainTime){
                recyclerViewHolder.rlTrainTime.setVisibility(View.VISIBLE);
                recyclerViewHolder.tvTrainingData.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(trainTime)));
            }else {
                recyclerViewHolder.rlTrainTime.setVisibility(View.GONE);

            }

        }else{



            //培训时间
            recyclerViewHolder.rlTrainTime.setVisibility(View.GONE);
        }

        if (null==memoContent){
            recyclerViewHolder.tvEdit.setVisibility(View.GONE);
        }else{
            recyclerViewHolder.tvEdit.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvEdit.setText(memoContent);
        }
        if (null==schoolName){
            recyclerViewHolder.rlSchool.setVisibility(View.GONE);
        }else{
            recyclerViewHolder.rlSchool.setVisibility(View.VISIBLE);
            recyclerViewHolder.tvSchoolName.setText(schoolName);
        }

        //根据status设置状态的文字和颜色
        if (status.equals("0")){
            recyclerViewHolder.tvStatus.setText("开发");
            recyclerViewHolder.llManage.setVisibility(View.GONE);
        }else  if (status.equals("1")){
            recyclerViewHolder.tvStatus.setText("待培训");
            recyclerViewHolder.llManage.setVisibility(View.GONE);
        }else  if (status.equals("2")){
            recyclerViewHolder.tvStatus.setText("培训");
            if (roleName.contains("开发老师")){
                recyclerViewHolder.llManage.setVisibility(View.VISIBLE);
                if (null==dataList.get(position).headPic){
                    recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.head);
                }else {
                    //加载网络图片
                    ImageLoaderUtil.getImageLoader(mContext).displayImage(dataList.get(position).headPic, recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
                }

                recyclerViewHolder.tvManageName.setText(dataList.get(position).realName);
            }else{
                recyclerViewHolder.llManage.setVisibility(View.GONE);
            }

        }else  if (status.equals("3")){
            recyclerViewHolder.tvStatus.setText("分配");
            if (roleName.contains("开发老师")){
                recyclerViewHolder.llManage.setVisibility(View.VISIBLE);
                if (null==dataList.get(position).headPic){
                    recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.head);
                }else {
                    //加载网络图片
                    ImageLoaderUtil.getImageLoader(mContext).displayImage(dataList.get(position).headPic, recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
                }
                recyclerViewHolder.tvManageName.setText(dataList.get(position).realName);
            }else{
                recyclerViewHolder.llManage.setVisibility(View.GONE);
            }
        }else  if (status.equals("4")){
            recyclerViewHolder.tvStatus.setText("合作");
            if (roleName.contains("开发老师")){
                recyclerViewHolder.llManage.setVisibility(View.VISIBLE);
                if (null==dataList.get(position).headPic){
                    recyclerViewHolder.ivHead.setBackgroundResource(R.mipmap.head);
                }else {
                    //加载网络图片
                    ImageLoaderUtil.getImageLoader(mContext).displayImage(dataList.get(position).headPic, recyclerViewHolder.ivHead, ImageLoaderUtil.getPhotoImageOption());
                }
                recyclerViewHolder.tvManageName.setText(dataList.get(position).realName);
            }else{
                recyclerViewHolder.llManage.setVisibility(View.GONE);
            }

        }
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
        @BindView(R.id.tv_projectName)
        TextView tvProjectName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_)
        TextView tv_;
        @BindView(R.id.tv_training_time)
        TextView tvTrainingTime;
        @BindView(R.id.tv_training_data)
        TextView tvTrainingData;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_manageName)
        TextView tvManageName;
        @BindView(R.id.tv_schoolName)
        TextView tvSchoolName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.rl_school)
        RelativeLayout rlSchool;
        @BindView(R.id.rl_trainTime)
        RelativeLayout rlTrainTime;
        @BindView(R.id.ll_manage)
        LinearLayout llManage;
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.iv_logo)
        CircleImageView ivLogo;
      public RecyclerViewHolder(View itemView) {
                super(itemView);
                //重要
                this.itemView = itemView;
                ButterKnife.bind(this, itemView);

        }
    }
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
        mContext.startActivity(intent);
    }
}