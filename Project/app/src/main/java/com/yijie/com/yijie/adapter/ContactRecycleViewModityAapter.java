package com.yijie.com.yijie.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ContactModityActivity;
import com.yijie.com.yijie.bean.school.SchoolContact;
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
    private List<SchoolContact> mList;

    public ContactRecycleViewModityAapter(Context mContext, List<SchoolContact> mList, int res) {
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

    class TextSwitcher implements TextWatcher {

        private final EditText editText;
        private SchoolContactHolder myViewHolder;

        public TextSwitcher(SchoolContactHolder myViewHolder,EditText editText) {
            this.myViewHolder = myViewHolder;
            this.editText=editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable!=null){
                SchoolContact schoolContact = mList.get(Integer.parseInt(editText.getTag().toString()));
                if (editText.getId()==R.id.tv_name){
                    schoolContact.setUserName(editable.toString());
                }else if(editText.getId()==R.id.tv_phone){
                    schoolContact.setCellphone(editable.toString());
                }else if(editText.getId()==R.id.tv_zjNumber){
                    schoolContact.setTelephone(editable.toString());
                }else if(editText.getId()==R.id.tv_wxNumber){
                    schoolContact.setWechat(editable.toString());
                }else if(editText.getId()==R.id.tv_qqNumber){
                    schoolContact.setQq(editable.toString());
                }
            }
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final SchoolContactHolder schoolContactHolder = (SchoolContactHolder) holder;
        if (mList != null) {
            SchoolContact item = mList.get(position); // Object StudentBean
            schoolContactHolder.tvName.setText(item.getUserName());
            schoolContactHolder.tvPhone.setText(item.getCellphone());
            schoolContactHolder.tvZjNumber.setText(item.getTelephone());
            schoolContactHolder.tvWxNumber.setText(item.getWechat());
            schoolContactHolder.tvQqNumber.setText(item.getQq());
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
            //姓名
            schoolContactHolder.tvName.addTextChangedListener(new TextSwitcher(schoolContactHolder,schoolContactHolder.tvName) {
            });
            schoolContactHolder.tvName.setTag(position);
            //电话
            schoolContactHolder.tvPhone.addTextChangedListener(new TextSwitcher(schoolContactHolder,schoolContactHolder.tvPhone) {
            });
            schoolContactHolder.tvPhone.setTag(position);
            //座机
            schoolContactHolder.tvZjNumber.addTextChangedListener(new TextSwitcher(schoolContactHolder,schoolContactHolder.tvZjNumber) {
            });
            schoolContactHolder.tvZjNumber.setTag(position);
            //微信
            schoolContactHolder.tvWxNumber.addTextChangedListener(new TextSwitcher(schoolContactHolder,schoolContactHolder.tvWxNumber) {
            });
            schoolContactHolder.tvWxNumber.setTag(position);
            //qq
            schoolContactHolder.tvQqNumber.addTextChangedListener(new TextSwitcher(schoolContactHolder,schoolContactHolder.tvQqNumber) {
            });
            schoolContactHolder.tvQqNumber.setTag(position);

        }


    }

    /**
     * 获取修改之后的list
     * @return
     */
    public List<SchoolContact> getModiftyList(){
        return mList;
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
        EditText tvName;

        @BindView(R.id.tv_phone)
        EditText tvPhone;

        @BindView(R.id.tv_zjNumber)
        EditText tvZjNumber;
        @BindView(R.id.tv_wxNumber)
        EditText tvWxNumber;
        @BindView(R.id.tv_qqNumber)
        EditText tvQqNumber;
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


