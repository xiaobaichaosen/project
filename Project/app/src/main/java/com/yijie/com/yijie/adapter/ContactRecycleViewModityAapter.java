package com.yijie.com.yijie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ContactModityActivity;
import com.yijie.com.yijie.db.ContactBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 奕杰平台 on 2018/2/23.
 */

public class ContactRecycleViewModityAapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private  int res;
    private Context mContext;
    private List<ContactBean> mList;

    public ContactRecycleViewModityAapter(Context mContext, List<ContactBean> mList,int res) {
        this.mList = mList;
        this.mContext = mContext;
        this.res=res;

    }


    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        SchoolContactHolder threeHolder = new SchoolContactHolder(view);
        return threeHolder;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final SchoolContactHolder schoolContactHolder = (SchoolContactHolder) holder;
        if (mList != null) {
            ContactBean item = mList.get(position); // Object StudentBean
            schoolContactHolder.tvName.setText(item.getName());
            schoolContactHolder.tvPhone.setText(item.getPhoneNumber());
            schoolContactHolder.tvZjNumber.setText(item.getZjNubmer());
            schoolContactHolder.tvWxNumber.setText(item.getWxNubmer());
            schoolContactHolder.tvQqNumber.setText(item.getQqNubmer());
            final Integer tag=new Integer(position);//初始化一个Integer实例，其值为position
            schoolContactHolder.checkboxM.setTag(tag);
            if (ContactModityActivity.map.containsKey(tag)){
                schoolContactHolder.checkboxM.setChecked(ContactModityActivity.map.get(tag));
            }else {
                schoolContactHolder.checkboxM.setChecked(false);
            }

            schoolContactHolder.checkboxM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (schoolContactHolder.checkboxM.isChecked()){
                        ContactModityActivity.map.put(tag,true);
                    }else {
                        ContactModityActivity.map.put(tag,false);
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }


    /**
     * 学校联系人holder
     */
    public class SchoolContactHolder extends RecyclerView.ViewHolder {
        View itemView;
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_zjNumber)
        TextView tvZjNumber;
        @BindView(R.id.tv_wxNumber)
        TextView tvWxNumber;
        @BindView(R.id.tv_qqNumber)
        TextView tvQqNumber;
        @BindView(R.id.checkboxM)
        CheckBox checkboxM;

        public SchoolContactHolder(View itemView) {
            super(itemView);

            //重要
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

        }

    }


}


