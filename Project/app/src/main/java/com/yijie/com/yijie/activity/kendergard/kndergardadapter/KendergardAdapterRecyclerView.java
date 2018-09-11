package com.yijie.com.yijie.activity.kendergard.kndergardadapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;


/**
 *
 */
public class KendergardAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  Context mContext;
    private List<Item> mList;

    public KendergardAdapterRecyclerView(Context mContext,List<Item> mList) {
        this.mList = mList;
        this.mContext=mContext;

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

        if (viewType == 1) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_detail_item,parent,false);
            ThreeHolder threeHolder = new ThreeHolder(view);
            return threeHolder;
        }else if (viewType == 2){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_contact,parent,false);
            FourHolder twoHolder = new FourHolder(v);
            return twoHolder;
        }
        else if (viewType == 3) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_information, parent, false);
            KInformationHolder twoHolder = new KInformationHolder(v);
            return twoHolder;


        }
        else if (viewType == 4){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_need,parent,false);
            KindergardNeddHolder twoHolder = new KindergardNeddHolder(v);
            return twoHolder;
        }
        else if (viewType == 5) {


            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_duty, parent, false);
            KDutyStudentHolder twoHolder = new KDutyStudentHolder(v);
            return twoHolder;
        }


        else if (viewType == 6) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_management,parent,false);
            KManagementHolder myViewHolder = new KManagementHolder(view);
            return myViewHolder;

        }
        else if (viewType == 7) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_evaluate, parent, false);
            KEvaluateHolder twoHolder = new KEvaluateHolder(v);
            return twoHolder;

        }

        else if (viewType == 8) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_share, parent, false);
            KShareHolder twoHolder = new KShareHolder(v);
            return twoHolder;
        }
        else {


            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kendergard_feedback, parent, false);
            FiveHolder twoHolder = new FiveHolder(v);
            return twoHolder;
        }
//        return null;



    }
//设置数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThreeHolder) {
            ThreeHolder threeHolder = (ThreeHolder) holder;
            if (mList != null) {
                Item item = mList.get(position); // Object StudentBean
                threeHolder.setName(item.getName()); // Name

            }
        }else if (holder instanceof KindergardNeddHolder){
            KindergardNeddHolder twoHolder = (KindergardNeddHolder) holder;
            if (mList!=null){
                Item item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }else if (holder instanceof  KDutyStudentHolder){
            if (mList!=null){
                Item item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }
        else if (holder instanceof  KInformationHolder){
            if (mList!=null){
                Item item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }
        else if (holder instanceof  KDutyStudentHolder){
            if (mList!=null){
                Item item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }
        else if (holder instanceof  KDutyStudentHolder){
            if (mList!=null){
                Item item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }
        else if (holder instanceof  KShareHolder){
            if (mList!=null){
                Item item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
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


    /**
     * 第二种holder园所需求
     */
    public class KindergardNeddHolder extends RecyclerView.ViewHolder {
        private ImageView imgChatHead;
        public KindergardNeddHolder(View itemView) {
            super(itemView);
//


        }
        public void setImage(int idImage){
            Picasso.with(imgChatHead.getContext()).
                    load(idImage).
                    centerCrop().
                    resize(60,60).
                    transform(new CircleTransform()).
                    into(imgChatHead);
        }
    }
    /**
     * 第三种holder 园所简介
     */
    public class ThreeHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public ThreeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            final LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        checkBox.setText("收起");
                        linearLayout.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.GONE);
                        checkBox.setText("更多");
                    }
                }
            });
        }
        public void setName(String name){
            tvName.setText(name);
        }

    }


    /**
     * 第四种holder 联系方式
     */
    public class FourHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public FourHolder(View itemView) {
            super(itemView);
//            tvName = (TextView) itemView.findViewById(R.id.tvName);



        }
        public void setName(String name){
            tvName.setText(name);
        }

    }

    /**
     * 第四种holder
     */
    public class FiveHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public FiveHolder(View itemView) {
            super(itemView);
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            final LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);


        }


    }

    /**
     * 考勤管理holder
     */
    public class KManagementHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public KManagementHolder(View itemView) {
            super(itemView);

        }


    }
    /**
     * 第六种holder园所硬件信息
     */
    public class KInformationHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public KInformationHolder(View itemView) {
            super(itemView);

//

        }


    }

    /**
     * holder园所在岗学生
     */
    public class KDutyStudentHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public KDutyStudentHolder(View itemView) {
            super(itemView);
            TextView tv_more = (TextView) itemView.findViewById(R.id.checkBox);
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.setClass(mContext, InternshipStatusActivity.class);
                    mContext.startActivity(intent);
                }
            });
//

        }


    }
    /**
     * holder评价
     */
    public class KEvaluateHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public KEvaluateHolder(View itemView) {
            super(itemView);

//

        }


    }

    /**
     * holder分享
     */
    public class KShareHolder extends RecyclerView.ViewHolder {
        public KShareHolder(final View itemView) {
            super(itemView);
           TextView tv_share = (TextView) itemView.findViewById(R.id.tv_share);
            tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.setClass(mContext, MoreShareActivity.class);
                    mContext.startActivity(intent);
                }
            });
//

        }


    }

}
