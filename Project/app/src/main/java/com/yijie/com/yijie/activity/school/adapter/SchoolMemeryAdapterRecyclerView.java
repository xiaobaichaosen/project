package com.yijie.com.yijie.activity.school.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.StudentBean;

import java.util.List;


/**
 * Created by Alessandro on 12/01/2016.
 */
public class SchoolMemeryAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  Context mContext;
    private List<StudentBean> mList;

    public SchoolMemeryAdapterRecyclerView(Context mContext, List<StudentBean> mList) {
        this.mList = mList;
        this.mContext = mContext;

    }

    /**
     * 获得当前view的type类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }
    /**
     * 根据不同的holder加载不同的布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_school_memery,parent,false);
        SchoolContact schoolContact = new SchoolContact(view);
            return schoolContact;



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         SchoolContact schoolContact= (SchoolContact) holder;
            if (mList != null) {
                StudentBean item = mList.get(position); // Object StudentBean
                schoolContact.memery_name.setText(item.getName());
                schoolContact.memery_content.setText(item.getConcatName());
                schoolContact.memery_date.setText(item.getCreatDate());
                if (position==mList.size()-1){
                    schoolContact.tv_gone.setVisibility(View.GONE);
                }
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


    public class  SchoolContact extends RecyclerView.ViewHolder {
        TextView memery_name;
        TextView memery_content;
        TextView memery_date;
        TextView tv_gone;
        public SchoolContact(View itemView) {
            super(itemView);
            memery_name = (TextView) itemView.findViewById(R.id.memery_name);
            memery_content = (TextView) itemView.findViewById(R.id.memery_content);
            memery_date = (TextView) itemView.findViewById(R.id.memery_date);
            tv_gone = (TextView) itemView.findViewById(R.id.tv_gone);
        }


    }

}
