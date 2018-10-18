package com.yijie.com.kindergartenapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.AllPeopleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;

public class AllPeopleAdapter extends RecyclerView.Adapter<AllPeopleAdapter.MyViewHolder> {
    /**
     * 这个是checkbox的Hashmap集合
     */
    public final HashMap<Integer, Boolean> map;

    /**
     * 这个是数据集合
     */
    public final List<String> list;

    public AllPeopleAdapter() {
        map = new HashMap<>();
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("参与人员" + i);
            //Checkbox初始状态置为false
            map.put(i, false);
        }
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
     * 全不选
     *
     */
    public void noAll() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        boolean shouldall = false;
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();
            if (!value) {
                shouldall = false;
                break;
            }
        }

        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldall);
        }
        notifyDataSetChanged();
    }

    /**
     * 反选
     */
    public void neverAll() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(!entry.getValue());
        }
        notifyDataSetChanged();
    }

    /**
     * 单选
     * @param postion
     */
    public void singleSelect(int postion) {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        map.put(postion, true);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_check_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.checkBox.setText(list.get(position));
        //从map集合获取状态
        holder.checkBox.setChecked(map.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put(position, !map.get(position));
                EventBus.getDefault().post("update");
                //刷新适配器
                notifyDataSetChanged();
                //单选
//                singleSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null ? list.size():0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            checkBox = (CheckBox) itemView.findViewById(R.id.cbox);
        }
    }

}

