package com.yijie.com.yijie.activity.school.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yijie.com.yijie.activity.newschool.ContactAdapter;
import com.yijie.com.yijie.activity.newschool.NewSchoolActivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.adapter.ContactRecycleViewAapter;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;

import java.util.List;


/**
 * Created by Alessandro on 12/01/2016.
 */
public class SchoolContactAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  Context mContext;
    private List<StudentBean> mList;

    public SchoolContactAdapterRecyclerView(Context mContext, List<StudentBean> mList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_school_contact,parent,false);
        SchoolContact schoolContact = new SchoolContact(view);
            return schoolContact;



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         SchoolContact schoolContact= (SchoolContact) holder;
            if (mList != null) {
                StudentBean item = mList.get(position); // Object StudentBean
                schoolContact.tv_contact_phone.setText(item.getPhoneNumber());
                schoolContact.tv_contact_zj.setText(item.getZiNubmer());
                schoolContact.tv_contactName.setText(item.getConcatName());
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
        TextView tv_contactName;
        TextView tv_contact_phone;
        TextView tv_contact_zj;
        public SchoolContact(View itemView) {
            super(itemView);
            tv_contactName = (TextView) itemView.findViewById(R.id.tv_contactName);
            tv_contact_phone = (TextView) itemView.findViewById(R.id.tv_contact_phone);
            tv_contact_zj = (TextView) itemView.findViewById(R.id.tv_contact_zj);
        }


    }

}
