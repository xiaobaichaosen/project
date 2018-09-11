package com.yijie.com.yijie.activity.newschool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.InternshipStatusActivity;
import com.yijie.com.yijie.activity.kendergard.Item;
import com.yijie.com.yijie.activity.kendergard.MoreShareActivity;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.CircleTransform;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.yijie.bean.school.SchoolMemoInfo;
import com.yijie.com.yijie.fragment.student.LoadMoreStudentAdapter2;
import com.yijie.com.yijie.utils.TimeUtil;

import java.util.List;

/**
 * Created by 奕杰平台 on 2018/5/2.
 * 备忘录适配器
 */

public class MomorandumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnLongClickListener {

    private Context mContext;
    private List<SchoolMemoInfo> mList;

    public MomorandumAdapter(Context mContext,List<SchoolMemoInfo> mList) {
        this.mList = mList;
        this.mContext=mContext;

    }
public void refrshRecyclView(List<SchoolMemoInfo> list){
        this.mList=list;
        notifyDataSetChanged();
}

    /**
     * 手动添加长按事件
     */
    public  interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
    public MomorandumAdapter.OnLongClickListener mOnLongClickListener = null;
    public void setOnLongClickListener(MomorandumAdapter.OnLongClickListener listener) {
        mOnLongClickListener = listener;
    }
    @Override public boolean onLongClick(View view) {
        if (null != mOnLongClickListener) {
            mOnLongClickListener.onLongClick(view, (int) view.getTag());
        }

        // 消耗事件，否则长按逻辑执行完成后还会进入点击事件的逻辑处理
        return true;
    }
    /**
     * 获得当前view的type类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        return mList.get(position).getSendType();
    }
    /**
     * 根据不同的holder加载不同的布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_from_usermessage, parent, false);
            MeViewHolder threeHolder = new MeViewHolder(view);
            view.setOnLongClickListener(this);
            return threeHolder;
        } else  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_to_usermessage, parent, false);
            OtherViewHodler twoHolder = new OtherViewHodler(view);
            view.setOnLongClickListener(this);
            return twoHolder;
        }

    }
    //设置数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MeViewHolder) {
            MeViewHolder meViewHolder = (MeViewHolder) holder;
            if (mList != null) {
                SchoolMemoInfo schoolMemoInfo = mList.get(position);
                if (0==schoolMemoInfo.getIsDel()){
                    meViewHolder.mycontent.getPaint().setAntiAlias(true);// 抗锯齿 ;
                }else {
                    meViewHolder.mycontent.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                }
                if (null!=schoolMemoInfo.getProjectName()){
                    meViewHolder.tvProjectName.setVisibility(View.VISIBLE);
                    meViewHolder.tvProjectName.setText(schoolMemoInfo.getProjectName());
                }else {
                    meViewHolder.tvProjectName.setVisibility(View.GONE);
                }
                meViewHolder.tvName.setText(schoolMemoInfo.getCreateBy()+"");
                meViewHolder.mycontent.setText(schoolMemoInfo.getMemoContent());
                String createTime = schoolMemoInfo.getCreateTime();
                if (null!=createTime){
                    meViewHolder.tvDate.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(schoolMemoInfo.getCreateTime())));
                }
            }
            //将position保存在itemView的Tag中，以便点击时进行获取
            meViewHolder.setIsRecyclable(false);
            meViewHolder.itemView.setTag(position);
        } else if (holder instanceof OtherViewHodler) {
            OtherViewHodler otherViewHodler = (OtherViewHodler) holder;
            if (mList != null) {
                SchoolMemoInfo schoolMemoInfo = mList.get(position);
                if (0==schoolMemoInfo.getIsDel()){
                    //未删除
                    otherViewHodler.mycontent .getPaint().setAntiAlias(true);// 抗锯齿
                }else {
                    //删除
                    otherViewHodler.mycontent.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                }
                if (null!=schoolMemoInfo.getProjectName()){
                    otherViewHodler.tvProjectName.setVisibility(View.VISIBLE);
                    otherViewHodler.tvProjectName.setText(schoolMemoInfo.getProjectName());
                }else {
                    otherViewHodler.tvProjectName.setVisibility(View.GONE);
                }
                otherViewHodler.tvName.setText(schoolMemoInfo.getCreateBy()+"");
                otherViewHodler.mycontent.setText(schoolMemoInfo.getMemoContent());
                otherViewHodler.tvDate.setText(TimeUtil.DateformatTime(TimeUtil.strToDateLong(schoolMemoInfo.getCreateTime())));
            }
            //将position保存在itemView的Tag中，以便点击时进行获取
            otherViewHodler.setIsRecyclable(false);
            otherViewHodler.itemView.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }else {
            return mList.size();
        }
    }


    /**
     * 我发的消息
     */
    public class MeViewHolder extends RecyclerView.ViewHolder {
        private  TextView tvDate;
        private  TextView mycontent;
        private TextView tvName;
        private TextView tvProjectName;
        public MeViewHolder(View itemView) {
            super(itemView);
            tvProjectName = (TextView) itemView.findViewById(R.id.tv_projectName);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            mycontent = (TextView) itemView.findViewById(R.id.mycontent);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }

    }
    /**
     * 其他人发的消息
     */
    public class OtherViewHodler extends RecyclerView.ViewHolder {
        private  TextView tvDate;
        private  TextView mycontent;
        private TextView tvProjectName;
        private TextView tvName;

        public OtherViewHodler(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            mycontent = (TextView) itemView.findViewById(R.id.mycontent);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvProjectName = (TextView) itemView.findViewById(R.id.tv_projectName);


        }
        public void setName(String name){
            tvName.setText(name);
        }

    }




}

