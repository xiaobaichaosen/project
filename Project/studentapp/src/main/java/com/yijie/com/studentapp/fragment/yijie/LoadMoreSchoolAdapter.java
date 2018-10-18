package com.yijie.com.studentapp.fragment.yijie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.bean.KindergartenNeed;
import com.yijie.com.studentapp.bean.StudentBrowseFootmark;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreSchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private List<StudentBrowseFootmark> dataList;
    private Context mContext;
    /**
     * 这个是checkbox的Hashmap集合
     */
    public final HashMap<Integer, Boolean> map;
    public LoadMoreSchoolAdapter(List<StudentBrowseFootmark> dataList, int res) {
        this.dataList = dataList;
        this.res=res;
        map = new HashMap<>();
        for (int i = 0; i < dataList.size(); i++) {
            //Checkbox初始状态置为false
            map.put(i, false);
        }
    }
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }
    /**
     * 全选
     */
    public void selectAll() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        boolean shouldall = false;
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();
            if (!value) {
                shouldall = true;
                break;
            }
        }

        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldall);
        }
        notifyDataSetChanged();
    }

    /**
     * 取消全选
     */
    public void cancleAll() {
        for (int i = 0; i < dataList.size(); i++) {
            //Checkbox初始状态置为false
            map.put(i, false);
        }
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        StudentBrowseFootmark studentBrowseFootmark = dataList.get(position);
        recyclerViewHolder.tvKindName.setText(studentBrowseFootmark.getKindName());
        recyclerViewHolder.tvRequstNum.setText("需求:"+studentBrowseFootmark.getStudentNum());
        recyclerViewHolder.tvPost.setText("投递:"+studentBrowseFootmark.getCountDelivery());
        recyclerViewHolder.tvSelect.setText("已选:"+studentBrowseFootmark.getCountReceive());
        recyclerViewHolder.tvRemain.setText("剩余:"+(studentBrowseFootmark.getCountSurplus()));
        String updateTime = studentBrowseFootmark.getBrowseTime();
        if (null!=updateTime){
            String s = updateTime.replaceAll("/", "-");
            recyclerViewHolder.tvData.setText(TimeUtils.DateformatTime(TimeUtils.strToDateLong(s)));
        }
        recyclerViewHolder.tvMoney.setText(studentBrowseFootmark.getKinderSalarySet());
        //从map集合获取状态
        recyclerViewHolder.cbCheck.setChecked(map.get(position));
        recyclerViewHolder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put(position, !map.get(position));
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        recyclerViewHolder.itemView.setTag(position);
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
        return dataList!=null ? dataList.size():0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
        @BindView(R.id.tv_kindName)
        TextView tvKindName;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_requstNum)
        TextView tvRequstNum;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_post)
        TextView tvPost;
        @BindView(R.id.tv_select)
        TextView tvSelect;
        @BindView(R.id.tv_remain)
        TextView tvRemain;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}