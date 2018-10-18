package com.yijie.com.studentapp.view.slipdelect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.bean.StudentWorkDue;

import java.util.List;

public class LoadMoreWorkDueAdapter extends RecyclerView.Adapter<LoadMoreWorkDueAdapter.RecyclerViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private Context mContext;

    private IonSlidingViewClickListener mIDeleteBtnClickListener;


    private SlidingButtonView mMenu = null;

    private final int res;
    private List<StudentWorkDue> dataList;

    public LoadMoreWorkDueAdapter(List<StudentWorkDue> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
    }




    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                this.mContext=parent.getContext();

        mIDeleteBtnClickListener = (IonSlidingViewClickListener) mContext;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.tvTime.setText(dataList.get(position).getWorkDue());
        holder.tvCollage.setText(dataList.get(position).getCompanyName());
        holder.tvEducation.setText(dataList.get(position).getPost());
        //设置内容布局的宽为屏幕宽度
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        holder.layout_content.getLayoutParams().width =outMetrics.widthPixels;

        holder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }

            }
        });
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });
    }

    /**
     * 获得当前view的type类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return 1;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }




    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);

    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);

        void onDeleteBtnCilck(View view, int position);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public TextView tvCollage;
        public TextView tvTime;
        public TextView tvEducation;
        public ViewGroup layout_content;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvCollage = (TextView) itemView.findViewById(R.id.tv_collage);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvEducation = (TextView) itemView.findViewById(R.id.tv_education);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);

            ((SlidingButtonView) itemView).setSlidingButtonListener(LoadMoreWorkDueAdapter.this);
        }
    }

}
