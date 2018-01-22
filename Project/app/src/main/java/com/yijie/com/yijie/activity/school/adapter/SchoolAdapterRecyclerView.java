package com.yijie.com.yijie.activity.school.adapter;

import android.content.Context;
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
import com.yijie.com.yijie.activity.school.StudentBean;

import java.util.List;


/**
 * Created by Alessandro on 12/01/2016.
 */
public class SchoolAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  Context mContext;
    private List<StudentBean> mList;

    public SchoolAdapterRecyclerView(Context mContext,List<StudentBean> mList) {
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

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_introduction,parent,false);
            SchoolInformation threeHolder = new SchoolInformation(view);
            return threeHolder;
        }else if (viewType == 2){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_contact,parent,false);
            SchoolContactHolder twoHolder = new SchoolContactHolder(v);
            return twoHolder;
        }else if (viewType == 3){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_memory,parent,false);
            SchoolMemeryHolder twoHolder = new SchoolMemeryHolder(v);
            return twoHolder;
        }
        else if (viewType == 4) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_learndetail, parent, false);
            SchoolLearnDetailHolder twoHolder = new SchoolLearnDetailHolder(v);
            return twoHolder;


        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_learnproject,parent,false);
            SchoolLearnProjectHolder myViewHolder = new SchoolLearnProjectHolder(view);
            return myViewHolder;
        }
//        return null;



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SchoolInformation) {
            SchoolInformation threeHolder = (SchoolInformation) holder;
            if (mList != null) {
                StudentBean item = mList.get(position); // Object StudentBean

                threeHolder.setName(item.getName()); // Name
//                holder.setDescription(item.getDescription()); // Description
//                holder.setImage(item.getIdImage()); // Image
            }
        }else if (holder instanceof SchoolContactHolder){
            SchoolContactHolder twoHolder = (SchoolContactHolder) holder;
            if (mList!=null){
                StudentBean item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }else if (holder instanceof  SchoolMemeryHolder){
            if (mList!=null){
                StudentBean item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }
        else if (holder instanceof  SchoolLearnDetailHolder){
            if (mList!=null){
                StudentBean item = mList.get(position); // Object StudentBean
//                twoHolder.setImage(item.getIdImage()); // Image
            }
        }
        else{
            SchoolLearnProjectHolder myViewHolder = (SchoolLearnProjectHolder) holder;
            if (mList!=null){
//                StudentBean item = mList.get(position); // Object StudentBean
//                myViewHolder.setName(item.getName()); // Name
//                myViewHolder.setDescription(item.getDescription()); // Description
//                myViewHolder.setImage(item.getIdImage()); // Image
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
     * 学校联系人holder
     */
    public class SchoolContactHolder extends RecyclerView.ViewHolder {
        private ImageView imgChatHead;
        public SchoolContactHolder(View itemView) {
            super(itemView);
//            imgChatHead = (ImageView) itemView.findViewById(R.id.ivUser);
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            final LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            final LinearLayout ll_addContact = (LinearLayout) itemView.findViewById(R.id.ll_addContact);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        ll_addContact.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        checkBox.setText("收起");
                    }else{
                        checkBox.setText("更多");
                        ll_addContact.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });


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
     * 学校简介holder
     */
    public class SchoolInformation extends RecyclerView.ViewHolder {
        private TextView tvName;
        public SchoolInformation(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            final LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        linearLayout.setVisibility(View.VISIBLE);
                        checkBox.setText("收起");
                    }else{
                        checkBox.setText("更多");
                        linearLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
        public void setName(String name){
            tvName.setText(name);
        }

    }


    /**
     * 备忘录holder
     */
    public class SchoolMemeryHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public SchoolMemeryHolder(View itemView) {
            super(itemView);
//            tvName = (TextView) itemView.findViewById(R.id.tvName);
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
                        checkBox.setText("查看详情");
                    }
                }
            });
        }
        public void setName(String name){
            tvName.setText(name);
        }

    }

    /**
     * 学校实习详细holder
     */
    public class SchoolLearnDetailHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public SchoolLearnDetailHolder(View itemView) {
            super(itemView);
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


    }

    /**
     * 第五种holder
     */
    public class  SchoolLearnProjectHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public SchoolLearnProjectHolder(View itemView) {
            super(itemView);
            final CheckBox overCheckbox = (CheckBox) itemView.findViewById(R.id.overCheckbox);
            final LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
//
            overCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        overCheckbox.setText("收起");
                        linearLayout.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.GONE);
                        overCheckbox.setText("更多");
                    }
                }
            });
        }


    }

}
