package com.yijie.com.yijie.activity.newschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.CommonBean;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/1/3.
 */

public class CommenAdapter extends RecyclerView.Adapter<CommenAdapter.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<CommonBean> mList;
    private OnItemClickListener mOnItemClickListener = null;

    private List<MyViewHolder> mListHolder;


    public CommenAdapter(Context mContext, List<CommonBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
  public void refreshRecycle(List<CommonBean> list){
        this.mList=list;
        notifyDataSetChanged();
  }
    /**
     * 创建一个监听事件的接口；重要
     */
    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    /**
     * 外界进行调用该方法，为item设置点击事件；重要
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_adapter_item, parent, false);
        //为每个item设置点击事件；
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    /**
     * 这个是继承View.OnClickListener 实现的方法; 重要
     *
     * @param v 当前被点击的v;
     */
    @Override
    public void onClick(View v) {
        //判断当前是否为item设置监听事件
        if (mOnItemClickListener != null) {
            //如果设置了，那么回调该方法，由于view的tag是object类型的，希望能回调到当前所显示到第几项item所以进行类型转换，希望有更好的方法请赐教；
            mOnItemClickListener.onClick(v, Integer.parseInt((String) v.getTag()));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //一定要设置这个。要不在回调方法里面获得不到当前点击的是第几个item;注意tag是object类型的；重要；
        holder.itemView.setTag(position + "");
        holder.tvName.setText(mList.get(position).getName());
        holder.tvPhone.setText(mList.get(position).getMoney());

        holder.ivDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAdapter.getIntance(mContext).deletePersonById(mList.get(position).getId());
                EventBus.getDefault().post(new CommonBean());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        @BindView(R.id.iv_delet)
        ImageView ivDelet;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;


        public MyViewHolder(View itemView) {
            super(itemView);
            //重要
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);

        }

    }

}
