package com.yijie.com.yijie.fragment.student;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreStudentGrounpWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    private List<StudentBean> dataList;

    public LoadMoreStudentGrounpWrapperAdapter(List<StudentBean> dataList) {
        this.dataList = dataList;
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
        return dataList.get(position).getType();
    }

    /**
     * 根据不同的holder加载不同的布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //type=1待审核//type=3 已分配  //type=4 未通过
//        if (viewType == 1 || viewType == 3 || viewType == 4) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_adapter_item, parent, false);
            StudentRecyclerViewHolder studentRecyclerViewHolder = new StudentRecyclerViewHolder(view);
            view.setOnClickListener(this);
            return studentRecyclerViewHolder;
            //type=2待分配//type=3 已分配       //type=5 实习生 //type=6毕业生
//        } else if (viewType == 2 || viewType == 5 || viewType == 6) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_adapter_item_grounp, parent, false);
//            StudentGrounpRecyclerViewHolder studentGrounpRecyclerViewHolder = new StudentGrounpRecyclerViewHolder(view);
//
//            view.setOnClickListener(this);
//            return studentGrounpRecyclerViewHolder;
//        }

//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


//        if (holder instanceof StudentRecyclerViewHolder) {
            StudentRecyclerViewHolder studentRecyclerViewHolder = (StudentRecyclerViewHolder) holder;
            int type = dataList.get(position).getType();
        if (type == 2) {
            studentRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_group_allocation);
            } else if (type == 5) {
            studentRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_exercitation);
            } else if (type == 6) {
            studentRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_graduate);
            }
            studentRecyclerViewHolder.tvName.setText(dataList.get(position).getName());
//            studentRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_unchecked);
            //将position保存在itemView的Tag中，以便点击时进行获取
            studentRecyclerViewHolder.itemView.setTag(position);


//        } else {
//            StudentGrounpRecyclerViewHolder studentGrounpRecyclerViewHolder = (StudentGrounpRecyclerViewHolder) holder;
//            int type = dataList.get(position).getType();
//            if (type == 2) {
//                studentGrounpRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_group_allocation);
//            } else if (type == 5) {
//                studentGrounpRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_exercitation);
//            } else if (type == 6) {
//                studentGrounpRecyclerViewHolder.ivStutus.setBackgroundResource(R.mipmap.student_graduate);
//            }
//            studentGrounpRecyclerViewHolder.tvName.setText(dataList.get(position).getName());
//            //将position保存在itemView的Tag中，以便点击时进行获取
//            studentGrounpRecyclerViewHolder.itemView.setTag(position);
//        }




    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 学生的holder
     */
    public class StudentRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_stutus)
        ImageView ivStutus;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_isAllow)
        TextView tv_isAllow;

        StudentRecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    /**
     * 一组学生holder
     */
    public class StudentGrounpRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.iv_stutus)
        ImageView ivStutus;
        StudentGrounpRecyclerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
