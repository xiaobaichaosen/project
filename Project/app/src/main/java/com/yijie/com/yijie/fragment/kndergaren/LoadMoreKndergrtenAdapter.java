package com.yijie.com.yijie.fragment.kndergaren;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.Item;
import com.yijie.com.yijie.base.PermissionsActivity;
import com.yijie.com.yijie.base.PermissionsChecker;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */


public class LoadMoreKndergrtenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<Item> dataList;
    private Context mContext;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    public LoadMoreKndergrtenAdapter(List<Item> dataList ,Context context) {
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
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kndergarten_adapter_item, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.kndergarten_status.setText(dataList.get(position).getName());
        //将position保存在itemView的Tag中，以便点击时进行获取
        recyclerViewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.kndergarten_name)
        TextView kndergartenName;
        @BindView(R.id.tv_Principal)
        TextView tvPrincipal;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tv_phone;
        @BindView(R.id.kndergarten_status)
        TextView kndergarten_status;
        @BindView(R.id.kndergarten_integrity)
        TextView kndergartenIntegrity;
        @BindView(R.id.tv_training_data)
        TextView tvTrainingData;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv_phone.setOnClickListener(new View.OnClickListener() {
                public static final int REQUEST_CODE = 0;

                @Override
                public void onClick(View view) {

                    new CommomDialog(mContext, R.style.dialog, "您确定拨打电话么？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if(confirm){
                                call("15865125759");
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
     * 拨打电话
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
        mContext.startActivity(intent);
    }
}